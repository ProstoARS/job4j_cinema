package ru.job4j.cinema.model;

import java.util.Objects;

public class User {

    private String userName;
    private String email;
    private String phone;
    private int userId;

    public User(String userName, String email, String phone, int userId) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{"
                + "userName='" + userName + '\''
                + ", email='" + email + '\''
                + ", phone='" + phone + '\''
                + ", userId=" + userId
                + '}';
    }
}
