package ru.job4j.cinema.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.*;

import javax.servlet.http.HttpSession;


import java.util.Optional;

import static ru.job4j.cinema.util.SessionUser.*;

@Controller
public class TicketController {

    private static final int HALL_ID_THEN_USE_HALL_STORE = 1;

    private final ITicketService iTicketService;
    private final ISessionService iSessionService;
    private final IHallService ihallService;

    public TicketController(ITicketService iTicketService,
                            ISessionService iSessionService,
                            IHallService ihallService) {
        this.iTicketService = iTicketService;
        this.iSessionService = iSessionService;
        this.ihallService = ihallService;
    }

    @GetMapping("/formRowTicket/{movieId}")
    public String formTicketRow(Model model, @PathVariable("movieId") String id,
                                HttpSession session) {
        model.addAttribute("ticket", new Ticket(0, 0, 0, Integer.parseInt(id)));
        model.addAttribute("posRows",
                ihallService.findById(HALL_ID_THEN_USE_HALL_STORE).getPosRows());
        model.addAttribute("movie", id);
        model.addAttribute("user", getSessionUser(session));
        return "select_row";
    }

    @PostMapping("/addRow")
    public String addTicketRow(@ModelAttribute Ticket ticket,
                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("posRow", ticket.getPosRow());
        redirectAttributes.addAttribute("movieId", ticket.getMovieId());
        return "redirect:/formCellTicket/{posRow}/{movieId}";
    }

    @GetMapping("/formCellTicket/{posRow}/{movieId}")
    public String formTicketCell(Model model, HttpSession session,
                                @PathVariable("posRow") String posRow,
                                @PathVariable("movieId") String movieId) {
        model.addAttribute("cells",
                ihallService.findById(HALL_ID_THEN_USE_HALL_STORE).getCells());
        model.addAttribute("user", getSessionUser(session));
        model.addAttribute("posRow", posRow);
        model.addAttribute("movieId", movieId);
        return "select_cell";
    }

    @PostMapping("/addCell")
    public String addTicketCell(@ModelAttribute Ticket ticket,
                               RedirectAttributes redirectAttributes) {
        System.out.println(ticket);
        redirectAttributes.addAttribute("posRow", ticket.getPosRow());
        redirectAttributes.addAttribute("cell", ticket.getCell());
        redirectAttributes.addAttribute("movieId", ticket.getMovieId());
        return "redirect:/formTicketReservation/{posRow}/{cell}/{movieId}";
    }

    @GetMapping("/formTicketReservation/{posRow}/{cell}/{movieId}")
    public String formReservation(Model model,
                                  @PathVariable("posRow") String posRow,
                                  @PathVariable("cell") String cell,
                                  @PathVariable("movieId") String movieId, HttpSession session) {
        model.addAttribute("posRow", Integer.parseInt(posRow));
        model.addAttribute("cell", Integer.parseInt(cell));
        model.addAttribute("user", getSessionUser(session));
        model.addAttribute("movie", iSessionService.findById(Integer.parseInt(movieId)));
        return "ticket_reservation";
    }

    @PostMapping("/createTicket")
    public String createTicket(@ModelAttribute Ticket ticket, HttpSession session) {
                Optional<Ticket> dbTicket = iTicketService.addTicket(ticket, getSessionUser(session));
                if (dbTicket.isEmpty()) {
                    return "redirect:/failTicket";
                }
        return "redirect:/ticket";
    }

    @GetMapping("/ticket")
    public String ticket(Model model, HttpSession session) {
        model.addAttribute("user", getSessionUser(session));
        return "/ticket";
    }

    @GetMapping("/schemaPhoto")
    public ResponseEntity<Resource> download() {
        System.out.println("МЫ ТУТ!");
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(ihallService.findById(HALL_ID_THEN_USE_HALL_STORE).getSchema().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(
                        ihallService.findById(HALL_ID_THEN_USE_HALL_STORE).getSchema()));
    }

    @GetMapping("/failTicket")
    public String fail() {
        return "/sailFail";
    }
}
