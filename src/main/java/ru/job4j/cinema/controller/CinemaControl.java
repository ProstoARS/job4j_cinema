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
import ru.job4j.cinema.repository.IMovieRepository;
import ru.job4j.cinema.service.ISessionService;

import javax.servlet.http.HttpSession;


@Controller
public class CinemaControl {

    private final IMovieRepository iMovieRepository;
    private final ISessionService iSessionService;

    public CinemaControl(ISessionService iSessionService, IMovieRepository iMovieRepository) {
        this.iSessionService = iSessionService;
        this.iMovieRepository = iMovieRepository;
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
        model.addAttribute("movies", iSessionService.findAll());
        return "index";
    }

    @GetMapping("/photoMovie/{movieId}")
    public ResponseEntity<Resource> download(@PathVariable("movieId") Integer movieId) {
        Movie movie = iSessionService.findById(movieId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(movie.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(movie.getPhoto()));
    }

    private void initCinema() {
       if (iSessionService.findAll().isEmpty()) {
           iMovieRepository.findAll().forEach(iSessionService::addSession);
       }
    }

}
