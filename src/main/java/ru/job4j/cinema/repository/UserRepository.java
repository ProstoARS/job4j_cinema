package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> addUser(User user);

    Optional<User> findUserByEmailAndPhone(String email, String phone);
}
