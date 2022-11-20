package ru.job4j.cinema.store;

import ru.job4j.cinema.model.Movie;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MovieStore {
    private static final int INDEX_DIFFERENCE = 1;
    private static final MovieStore INST = new MovieStore();
    private final List<Movie> movies = new ArrayList<>();


    private MovieStore() {
        movies.add(new Movie(1, "Чужие", addPoster("/images/aliens.jpg")));
        movies.add(new Movie(2, "Терминатор", addPoster("/images/Terminator.jpg")));
        movies.add(new Movie(3, "Титаник", addPoster("/images/титаник.jpg")));
        movies.add(new Movie(4, "Аватар", addPoster("/images/аватар.jpg")));
    }

    public static MovieStore instOf() {
        return INST;
    }

    private byte[] addPoster(String file) {
        try (InputStream resource = MovieStore.class.getResourceAsStream(file)) {
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
