package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> add(User user) {
        return userRepository.addUser(user);
    }

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        return userRepository.findUserByEmailAndPhone(email, phone);
    }
}
