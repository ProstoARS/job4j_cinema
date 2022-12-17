package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Movie;

import java.util.List;

public interface MovieRepository {

    void addMovie(Movie movie);

    List<Movie> findAll();

    Movie findById(int movieId);
}
