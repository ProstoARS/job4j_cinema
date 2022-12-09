package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Movie;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static ru.job4j.cinema.util.PropertyReader.*;

public class MovieStore {
    private static final int INDEX_DIFFERENCE = 1;
    private static final MovieStore INST = new MovieStore();
    private final List<Movie> movies = new ArrayList<>();


    private MovieStore() {
        movies.add(new Movie(1, "Чужие", addPoster("aliens")));
        movies.add(new Movie(2, "Терминатор", addPoster("terminator")));
        movies.add(new Movie(3, "Титаник", addPoster("titanic")));
        movies.add(new Movie(4, "Аватар", addPoster("avatar")));
    }

    public static MovieStore instOf() {
        return INST;
    }

    private byte[] addPoster(String file) {

        try (InputStream resource = MovieStore.class.getResourceAsStream(
                load("img_location.properties").getProperty(file))) {
            if (resource != null) {
                return resource.readAllBytes();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Movie> findAll() {
        return movies.stream().toList();
    }

    public Movie findById(int movieId) {
        return movies.get(movieId - INDEX_DIFFERENCE);
    }

}
