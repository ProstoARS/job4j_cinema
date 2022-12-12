package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Hall;

import java.util.List;

public interface IHallService {

    void addHall(Hall hall);

    List<Hall> findAll();

    Hall findById(int id);
}
