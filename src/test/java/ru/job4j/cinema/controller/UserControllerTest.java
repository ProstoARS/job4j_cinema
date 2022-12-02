package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    public void whenUserRegistration() {
        User user = new User();
        UserService userService = mock(UserService.class);
        Model model = mock(Model.class);
        when(userService.add(user)).thenReturn(Optional.of(user));
        UserController userController = new UserController(userService);
        assertThat(userController.registration(model, user)).isEqualTo("redirect:/success");
    }

    @Test
    public void whenRegistrationFail() {
        User user = new User();
        UserService userService = mock(UserService.class);
        Model model = mock(Model.class);
        when(userService.add(user)).thenReturn(Optional.empty());
        UserController userController = new UserController(userService);
        assertThat(userController.registration(model, user)).isEqualTo(
                "redirect:/formRegistration?fail=true");
    }

    @Test
    public void whenUserLogin() {
        UserService userService = mock(UserService.class);
        User user = new User("ars", "ars@mail.ru", "12345");
        UserController userController = new UserController(userService);
        when(userService.findUserByEmailAndPhone(user.getEmail(), user.getPhone()))
                .thenReturn(Optional.of(user));
        assertThat(userController.login(user, new MockHttpServletRequest()))
                .isEqualTo("redirect:/index");
    }

    @Test
    public void whenUserLoginFail() {
        UserService userService = mock(UserService.class);
        User user = new User();
        UserController userController = new UserController(userService);
        when(userService.findUserByEmailAndPhone("", ""))
                .thenReturn(Optional.empty());
        assertThat(userController.login(user, new MockHttpServletRequest()))
                .isEqualTo("redirect:/loginPage?fail=true");
    }

}