package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Hall;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.job4j.cinema.util.PropertyReader.load;

@Repository
public class HallMemoryRepository implements HallRepository {

    private final Map<Integer, Hall> halls = new ConcurrentHashMap<>();

    private final AtomicInteger id = new AtomicInteger();

    public HallMemoryRepository() {
        addHall(new Hall(7, 15,
                addSchema(load("img_location.properties").getProperty("hallLocation"))));
    }

    @Override
    public void addHall(Hall hall) {
        hall.setId(id.incrementAndGet());
        halls.put(hall.getId(), hall);
    }

    @Override
    public List<Hall> findAll() {
        return halls.values().stream().toList();
    }

    @Override
    public Hall findById(int id) {
        return halls.get(id);
    }

    private byte[] addSchema(String file) {
        try (InputStream resource = HallMemoryRepository.class.getResourceAsStream(file)) {
            if (resource != null) {
                return resource.readAllBytes();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
