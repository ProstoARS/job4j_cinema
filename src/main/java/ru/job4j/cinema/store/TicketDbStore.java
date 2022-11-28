package ru.job4j.cinema.store;


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
public class TicketDbStore {

    private static final Logger LOG = LogManager.getLogger(TicketDbStore.class);
    private static final String INSERT = """
            INSERT INTO ticket(
            session_id, pos_row, cell, user_id)
            VALUES (?, ?, ?, ?)
            """;
    private final BasicDataSource pool;

    public TicketDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> addTicket(Ticket ticket, int userId) {
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
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
}
