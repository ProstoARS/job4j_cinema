package ru.job4j.cinema.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Movie;
import ru.job4j.cinema.store.MovieStore;


@Controller
public class CinemaControl {

    private final MovieStore moviesStore = MovieStore.instOf();


    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("movies", moviesStore.findAll());
        return "index";
    }

    @GetMapping("/photoMovie/{movieId}")
    public ResponseEntity<Resource> download(@PathVariable("movieId") Integer movieId) {
        Movie movie = moviesStore.findById(movieId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(movie.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(movie.getPhoto()));
    }
}
