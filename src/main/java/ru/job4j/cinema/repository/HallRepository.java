package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Hall;

import java.util.List;

public interface HallRepository {

    void addHall(Hall hall);

    List<Hall> findAll();

    Hall findById(int id);
}
