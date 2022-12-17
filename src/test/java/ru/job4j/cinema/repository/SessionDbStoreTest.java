package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.TestDataSourceConfig;
import ru.job4j.cinema.model.Movie;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SessionDbStoreTest {

    private static BasicDataSource pool;

    @BeforeAll
    public static void loadPool() {
        pool = new TestDataSourceConfig().loadPool();
    }

    @AfterEach
    public void clean() throws SQLException {
        try (PreparedStatement st = pool.getConnection().prepareStatement(
                "DELETE FROM ticket")) {
            st.execute();
        }
        try (PreparedStatement st = pool.getConnection().prepareStatement(
                "DELETE FROM users")) {
            st.execute();
        }
        try (PreparedStatement st = pool.getConnection().prepareStatement(
                "DELETE FROM sessions")) {
            st.execute();
        }
    }

    @Test
    public void whenAddSession() {
        SessionDbRepository sessionDbStore = new SessionDbRepository(pool);
        Movie movie = new Movie(2, "Titanic");
        int movieId = sessionDbStore.addSession(movie).get();
        movie.setId(movieId);
        Movie movieFromDb = sessionDbStore.findById(movie.getId());
        assertThat(movieFromDb).isEqualTo(movie);
    }

    @Test
    public void whenFindAllSession() {
        SessionDbRepository sessionDbStore = new SessionDbRepository(pool);
        Movie movie1 = new Movie(2, "Titanic");
        Movie movie2 = new Movie(3, "Avatar");
        int movieId1 = sessionDbStore.addSession(movie1).get();
        int movieId2 = sessionDbStore.addSession(movie2).get();
        movie1.setId(movieId1);
        movie2.setId(movieId2);
        List<Movie> movies = new ArrayList<>(List.of(movie1, movie2));
        List<Movie> expected = sessionDbStore.findAll();
        assertThat(expected).isEqualTo(movies);
    }

}