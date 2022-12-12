package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.repository.SessionRepository;

import java.util.List;

@Service
public class SessionService implements ISessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void addSession(Movie movie) {
        sessionRepository.addSession(movie);
    }

    @Override
    public List<Movie> findAll() {
        return sessionRepository.findAll();
    }

    @Override
    public Movie findById(int id) {
        return sessionRepository.findById(id);
    }
}
