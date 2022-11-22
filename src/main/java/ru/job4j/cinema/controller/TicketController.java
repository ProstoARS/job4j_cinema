package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

import java.util.List;

@Controller
public class TicketController {

    private static final List<Integer> POS_ROWS = List.of(1, 2, 3, 4, 5, 6, 7);

    private static final List<Integer> CELLS = List.of(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/formRowticket/{movieId}")
    public String addTicketRow(@ModelAttribute Ticket ticket,
                               Model model, @PathVariable("movieId") int id) {
        model.addAttribute("row", POS_ROWS);
        model.addAttribute("movie", id);
        return "redirect:/formCellticket";
    }

    @PostMapping("/formCellticket")
    public String addTicketCell(@ModelAttribute Ticket ticket, Model model) {
        model.addAttribute("cell", CELLS);
        return "redirect:/ticket";
    }
}
