package ru.job4j.cinema.model;

import java.util.ArrayList;
import java.util.List;

public class Hall {

    private final int row;
    private final int cell;
    private final byte[] schema;
    private Integer id;

    private List<Integer> posRows;

    private List<Integer> cells;

    public Hall(int row, int cell, byte[] schema) {
        this.row = row;
        this.cell = cell;
        this.schema = schema;
        initRows();
        initSells();
    }

    private void initRows() {
        this.posRows = new ArrayList<>();
        for (int i = 1; i <= row; i++) {
            posRows.add(i);
        }
    }

    private void initSells() {
        this.cells = new ArrayList<>();
        for (int i = 1; i <= cell; i++) {
            cells.add(i);
        }
    }

    public List<Integer> getPosRows() {
        return posRows;
    }

    public List<Integer> getCells() {
        return cells;
    }

    public byte[] getSchema() {
        return schema;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
