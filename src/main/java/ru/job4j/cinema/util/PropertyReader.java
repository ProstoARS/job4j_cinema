package ru.job4j.cinema.util;

import ru.job4j.cinema.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

public final class PropertyReader {

    private PropertyReader() {
    }

    public static Properties load(String fileName) {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(Main.class.getClassLoader()
                                .getResourceAsStream(fileName))
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return cfg;
    }
}
