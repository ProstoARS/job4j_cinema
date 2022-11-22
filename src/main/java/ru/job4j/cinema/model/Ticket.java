package ru.job4j.cinema.model;

import java.util.Objects;

public class Ticket {

    private int id;
    private int posRow;
    private int cell;

    private int movieId;

    public Ticket(int id, int posRow, int cell) {
        this.id = id;
        this.posRow = posRow;
        this.cell = cell;
    }

    public int getSessionId() {
        return movieId;
    }

    public void setSessionId(int sessionId) {
        this.movieId = sessionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosRow() {
        return posRow;
    }

    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket ticket)) {
            return false;
        }
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ticket{"
                + "id=" + id
                + ", posRow=" + posRow
                + ", cell=" + cell
                + '}';
    }
}
