package ru.job4j.cinema.service;

import org.springframework.stereotype.Controller;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.TicketDbStore;


@Controller
public class TicketService {

    private final TicketDbStore ticketDbStore;

    public TicketService(TicketDbStore ticketDbStore) {
        this.ticketDbStore = ticketDbStore;
    }

    public void addTicket(Ticket ticket, int userId) {
        ticketDbStore.addTicket(ticket, userId);
    }
}