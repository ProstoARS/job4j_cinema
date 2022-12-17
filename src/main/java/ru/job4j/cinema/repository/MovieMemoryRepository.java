package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Movie;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.job4j.cinema.util.PropertyReader.*;

@Repository
public class MovieMemoryRepository implements MovieRepository {
    private final Map<Integer, Movie> movies = new ConcurrentHashMap<>();

    private final AtomicInteger id = new AtomicInteger();


    private MovieMemoryRepository() {
        addMovie(new Movie("Чужие", addPoster("aliens")));
        addMovie(new Movie("Терминатор", addPoster("terminator")));
        addMovie(new Movie("Титаник", addPoster("titanic")));
        addMovie(new Movie("Аватар", addPoster("avatar")));
    }

    @Override
    public void addMovie(Movie movie) {
        movie.setId(id.incrementAndGet());
        movies.put(movie.getId(), movie);
    }

    @Override
    public List<Movie> findAll() {
        return movies.values().stream().toList();
    }

    @Override
    public Movie findById(int movieId) {
        return movies.get(movieId);
    }

    private byte[] addPoster(String file) {
        try (InputStream resource = MovieMemoryRepository.class.getResourceAsStream(
                load("img_location.properties").getProperty(file))) {
            if (resource != null) {
                return resource.readAllBytes();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
