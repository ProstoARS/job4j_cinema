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
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;
import javax.servlet.http.HttpSession;


import java.util.Optional;

import static ru.job4j.cinema.util.SessionUser.*;

@Controller
public class TicketController {

    private final TicketService ticketService;
    private final SessionService sessionService;
    private final HallService hallService;

    public TicketController(TicketService ticketService,
                            SessionService sessionService,
                            HallService hallService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
        this.hallService = hallService;
    }

    @GetMapping("/formRowTicket/{movieId}")
    public String formTicketRow(Model model, @PathVariable("movieId") String id,
                                HttpSession session) {
        model.addAttribute("ticket", new Ticket(0, 0, 0, Integer.parseInt(id)));
        model.addAttribute("posRows", hallService.getPosRows());
        model.addAttribute("movie", sessionService.findById(Integer.parseInt(id)));
        model.addAttribute("user", getSessionUser(session));
        return "select_row";
    }

    @PostMapping("/addRow")
    public String addTicketRow(HttpSession session, @ModelAttribute Ticket ticket,
                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("posRow", ticket.getPosRow());
        redirectAttributes.addAttribute("sessionId", ticket.getSessionId());
        return "redirect:/formCellTicket/{posRow}/{sessionId}";
    }

    @GetMapping("/formCellTicket/{posRow}/{sessionId}")
    public String addTicketCell(Model model, HttpSession session,
                                @PathVariable("posRow") String posRow,
                                @PathVariable("sessionId") String sessionId) {
        model.addAttribute("cells", hallService.getCells());
        model.addAttribute("user", getSessionUser(session));
        model.addAttribute("posRow", Integer.parseInt(posRow));
        model.addAttribute("sessionId", Integer.parseInt(sessionId));
        return "select_cell";
    }

    @PostMapping("/createTicket")
    public String createTicket(@ModelAttribute Ticket ticket, HttpSession session) {
                Optional<Ticket> dbTicket = ticketService.addTicket(ticket, getSessionUser(session));
                if (dbTicket.isEmpty()) {
                    return "redirect:/failTicket";
                }
        return "redirect:/ticket";
    }

    @GetMapping("/ticket")
    public String ticket(Model model) {
        return "/ticket";
    }

    @GetMapping("/schemaPhoto")
    public ResponseEntity<Resource> download() {
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(hallService.getSchema().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(hallService.getSchema()));
    }
}
