package ru.job4j.cinema.repository;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository
public class SessionDbStore implements SessionRepository {

    private static final Logger LOG = LogManager.getLogger(SessionDbStore.class);
    private static final String FIND_ALL = """
            SELECT *
            FROM sessions
            """;

    private static final String FIND_BY_ID = """
            SELECT *
            FROM sessions
            WHERE id = ?
            """;

    private static final String INSERT = """
            INSERT INTO sessions(name, photo) VALUES (?, ?)
            """;

    private final BasicDataSource pool;

    public SessionDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Optional<Integer> addSession(Movie movie) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, movie.getName());
            ps.setBytes(2, movie.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    movie.setId(id.getInt(1));
                    return Optional.of(movie.getId());
                }
            }
        } catch (Exception exception) {
            LOG.error(exception.getMessage(), exception);
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                movies.add(createMovie(resultSet)
                );
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return movies;
    }

    @Override
    public Movie findById(Integer movieId) {
        try (Connection connection = pool.getConnection();
        PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, movieId);
            try (ResultSet resultSet = ps.executeQuery()) {
                        if (resultSet.next()) {
                            return createMovie(resultSet);
                        }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    private static Movie createMovie(ResultSet rs) throws SQLException {
        Movie movie = new Movie(
                rs.getInt(1),
                rs.getString(2)
        );
        movie.setPhoto(rs.getBytes(3));
        return movie;
    }
}
