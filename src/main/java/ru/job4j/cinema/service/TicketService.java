package ru.job4j.cinema.service;

import org.springframework.stereotype.Controller;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.store.TicketDbStore;

import java.util.Optional;


@Controller
public class TicketService {

    private final TicketDbStore ticketDbStore;

    public TicketService(TicketDbStore ticketDbStore) {
        this.ticketDbStore = ticketDbStore;
    }

    public Optional<Ticket> addTicket(Ticket ticket, User user) {
        return ticketDbStore.addTicket(ticket, user.getUserId());
    }
}
