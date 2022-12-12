package ru.job4j.cinema.service;

import org.springframework.stereotype.Controller;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Optional;


@Controller
public class TicketService implements ITicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<Ticket> addTicket(Ticket ticket, User user) {
        return ticketRepository.addTicket(ticket, user.getUserId());
    }

    @Override
    public Ticket findById(int id) {
        return ticketRepository.findById(id);
    }
}
