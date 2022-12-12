package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Movie;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {

    Optional<Integer> addSession(Movie movie);

    List<Movie> findAll();

    Movie findById(Integer movieId);
}
