package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {

    @Test
    void whenAddTicketRow() {
        Ticket ticket = new Ticket(1, 1, 1);
        TicketService ticketService = mock(TicketService.class);
        SessionService sessionService = mock(SessionService.class);
        HallService hallService = mock(HallService.class);
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        redirectAttributes.addAttribute("posRow", ticket.getPosRow());
        redirectAttributes.addAttribute("movieId", ticket.getMovieId());
        assertThat(ticketController.addTicketRow(ticket, redirectAttributes))
                .isEqualTo("redirect:/formCellTicket/{posRow}/{movieId}");
    }

    @Test
    void whenAddTicketCell() {
        Ticket ticket = new Ticket(1, 1, 1);
        TicketService ticketService = mock(TicketService.class);
        SessionService sessionService = mock(SessionService.class);
        HallService hallService = mock(HallService.class);
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        redirectAttributes.addAttribute("posRow", ticket.getPosRow());
        redirectAttributes.addAttribute("movieId", ticket.getMovieId());
        assertThat(ticketController.addTicketCell(ticket, redirectAttributes))
                .isEqualTo("redirect:/formTicketReservation/{posRow}/{cell}/{movieId}");
    }

    @Test
    void whenCreateTicket() {
        Ticket ticket = new Ticket(1, 1, 1);
        HttpSession session = new MockHttpSession();
        TicketService ticketService = mock(TicketService.class);
        SessionService sessionService = mock(SessionService.class);
        HallService hallService = mock(HallService.class);
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        when(ticketService.addTicket(ticket, new User())).thenReturn(Optional.of(ticket));
        assertThat(ticketController.createTicket(ticket, session))
                .isEqualTo("redirect:/ticket");
    }

    @Test
    void whenFailCreateTicket() {
        Ticket ticket = new Ticket(1, 1, 1);
        HttpSession session = new MockHttpSession();
        TicketService ticketService = mock(TicketService.class);
        SessionService sessionService = mock(SessionService.class);
        HallService hallService = mock(HallService.class);
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        when(ticketService.addTicket(ticket, new User())).thenReturn(Optional.empty());
        assertThat(ticketController.createTicket(ticket, session))
                .isEqualTo("redirect:/failTicket");
    }
}