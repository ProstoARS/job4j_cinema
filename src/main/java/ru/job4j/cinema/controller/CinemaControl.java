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
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.store.MovieStore;

import javax.servlet.http.HttpSession;


@Controller
public class CinemaControl {

    private final MovieStore moviesStore = MovieStore.instOf();
    private final SessionService sessionService;

    public CinemaControl(SessionService sessionService) {
        this.sessionService = sessionService;
        initCinema();
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUserName("Гость");
        }
        model.addAttribute("user", user);
        model.addAttribute("movies", sessionService.findAll());
        return "index";
    }

    @GetMapping("/photoMovie/{movieId}")
    public ResponseEntity<Resource> download(@PathVariable("movieId") Integer movieId) {
        Movie movie = sessionService.findById(movieId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(movie.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(movie.getPhoto()));
    }

    private void initCinema() {
       if (sessionService.findAll().isEmpty()) {
           moviesStore.findAll().forEach(sessionService::addSession);
       }
    }

}
