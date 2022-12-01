package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

class UserDbStoreTest {

    private static BasicDataSource pool;

    @BeforeAll
    public static void loadPool() {
        pool = new Main().loadPool();
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
    public void whenAddUser() {
        UserDbStore store = new UserDbStore(pool);
        User user = new User("ars", "ars@mail.ru", "05576");
        store.addUser(user);
        User userFromDB = store.findUserByEmailAndPhone("ars@mail.ru", "05576").get();
        int userId = userFromDB.getUserId();
        user.setUserId(userId);
        assertThat(userFromDB).isEqualTo(user);
    }
}