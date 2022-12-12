package ru.job4j.cinema.repository;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class TicketDbStore implements TicketRepository {

    private static final Logger LOG = LogManager.getLogger(TicketDbStore.class);
    private static final String INSERT = """
            INSERT INTO ticket(
            session_id, pos_row, cell, user_id)
            VALUES (?, ?, ?, ?)
            """;

    private static final String FIND_TICKET = """
            SELECT * FROM ticket
            WHERE id = ?;
            """;
    private final BasicDataSource pool;

    public TicketDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Optional<Ticket> addTicket(Ticket ticket, int userId) {
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getMovieId());
            ps.setInt(2, ticket.getPosRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, userId);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(ticket);
    }

    @Override
    public Ticket findById(int id) {
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_TICKET)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ticket(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getInt(4)
                    );
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}
