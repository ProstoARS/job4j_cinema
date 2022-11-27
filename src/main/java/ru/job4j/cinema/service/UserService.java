package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.store.UserDbStore;

import java.util.Optional;

@Service
public class UserService {

    private final UserDbStore userDbStore;

    public UserService(UserDbStore userDbStore) {
        this.userDbStore = userDbStore;
    }

    public Optional<User> add(User user) {
        return userDbStore.addUser(user);
    }

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        return userDbStore.findUserByEmailAndPhone(email, phone);
    }
}
