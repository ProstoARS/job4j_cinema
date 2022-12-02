package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.TestDataSourceConfig;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

class TicketDbStoreTest {

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
    public void whenAddTicket() {
        TicketDbStore ticketDbStore = new TicketDbStore(pool);
        SessionDbStore sessionDbStore = new SessionDbStore(pool);
        UserDbStore userDbStore = new UserDbStore(pool);
        User user = new User("ars", "ars@mail.ru", "05576");
        int userId = userDbStore.addUser(user).get().getUserId();
        Movie movie = new Movie(1, "Titanic");
        sessionDbStore.addSession(movie);
        Ticket ticket = new Ticket(9, 7, movie.getId());
        int ticketId = ticketDbStore.addTicket(ticket, userId).get().getId();
        ticket.setId(ticketId);
        Ticket ticketInDb = ticketDbStore.findById(ticketId);
        assertThat(ticketInDb).isEqualTo(ticket);
    }

}