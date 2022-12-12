package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Movie;

import java.util.List;

public interface ISessionService {

    void addSession(Movie movie);

    List<Movie> findAll();

    Movie findById(int id);
}
