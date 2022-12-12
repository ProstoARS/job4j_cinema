package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDbStore implements UserRepository {

    private static final Logger LOG = LogManager.getLogger(UserDbStore.class);

    private static final String INSERT = """
            INSERT INTO users(
            userName, email, phone)
            VALUES (?, ?, ?)
            """;

    private static final String FIND_USER = """
            SELECT *
            FROM users
            WHERE email = ? and phone = ?
            """;

    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Optional<User> addUser(User user) {
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_USER)) {
            ps.setString(1, email);
            ps.setString(2, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("id")));
            }
        } catch (SQLException exception) {
            LOG.error(exception.getMessage(), exception);
        }
        return Optional.empty();
    }
}
