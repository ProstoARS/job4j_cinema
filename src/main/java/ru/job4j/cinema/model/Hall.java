package ru.job4j.cinema.model;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Hall {

    private int rows;
    private int cells;

    private byte[] schema;


    public Hall(int rows, int cells, byte[] schema) {
        this.rows = rows;
        this.cells = cells;
        this.schema = schema;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCell() {
        return cells;
    }

    public void setCell(int cell) {
        this.cells = cell;
    }

    public byte[] getSchema() {
        return schema;
    }

    public void setSchema(byte[] schema) {
        this.schema = schema;
    }
}
