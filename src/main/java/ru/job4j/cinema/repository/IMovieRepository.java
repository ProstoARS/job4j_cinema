package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Movie;

import java.util.List;

public interface IMovieRepository {

    void addMovie(Movie movie);

    List<Movie> findAll();

    Movie findById(int movieId);
}
