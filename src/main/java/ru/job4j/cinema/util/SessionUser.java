package ru.job4j.cinema.util;

import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

public final class SessionUser {

    private SessionUser() {
    }

    public static User getSessionUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUserName("Гость");
        } else {
            user.setUserName(user.getUserName());
        }
        return user;
    }
}
