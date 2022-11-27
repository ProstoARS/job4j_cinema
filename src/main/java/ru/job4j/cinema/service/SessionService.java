package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.store.SessionDbStore;

import java.util.List;

@Service
public class SessionService {

    private final SessionDbStore sessionDbStore;

    public SessionService(SessionDbStore sessionDbStore) {
        this.sessionDbStore = sessionDbStore;
    }

    public void addSession(Movie movie) {
        sessionDbStore.addSession(movie);
    }

    public List<Movie> findAll() {
        return sessionDbStore.findAll();
    }

    public Movie findById(int id) {
        return sessionDbStore.findById(id);
    }
}
