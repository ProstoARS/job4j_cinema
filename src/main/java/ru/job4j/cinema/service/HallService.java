package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class HallService {

    private final Hall hall;

    private List<Integer> posRows;

    private List<Integer> cells;

    public HallService(List<Integer> posRows, List<Integer> cells) {
        this.hall = new Hall(7, 15, addSchema("/images/schema.jpg"));
        initRows();
        initSells();
    }

    private void initRows() {
        this.posRows = new ArrayList<>();
        for (int i = 1; i <= hall.getRows(); i++) {
            posRows.add(i);
        }
    }

    private void initSells() {
        this.cells = new ArrayList<>();
        for (int i = 1; i <= hall.getCell(); i++) {
            cells.add(i);
        }
    }

    private byte[] addSchema(String file) {
        try (InputStream resource = HallService.class.getResourceAsStream(file)) {
            if (resource != null) {
                return resource.readAllBytes();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Integer> getPosRows() {
        return posRows;
    }

    public List<Integer> getCells() {
        return cells;
    }

    public byte[] getSchema() {
        return hall.getSchema();
    }
}
