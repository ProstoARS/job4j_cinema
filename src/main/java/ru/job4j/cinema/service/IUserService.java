package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;

import java.util.Optional;

public interface IUserService {

    Optional<User> add(User user);

    Optional<User> findUserByEmailAndPhone(String email, String phone);
}
