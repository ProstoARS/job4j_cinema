package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

import java.util.List;

@Service
public class HallService implements IHallService {

    private final HallRepository hallRepository;


    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public void addHall(Hall hall) {
        hallRepository.addHall(hall);
    }

    @Override
    public List<Hall> findAll() {
        return hallRepository.findAll();
    }

    @Override
    public Hall findById(int id) {
        return hallRepository.findById(id);
    }
}
