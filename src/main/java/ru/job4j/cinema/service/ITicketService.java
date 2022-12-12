package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.Optional;

public interface ITicketService {

    Optional<Ticket> addTicket(Ticket ticket, User user);

    Ticket findById(int id);
}
