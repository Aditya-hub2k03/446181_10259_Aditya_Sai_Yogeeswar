package com.yogesh.controller;

import com.yogesh.model.Movie;
import com.yogesh.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping({"/", "/home"})
    public String homePage(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "home";
    }

    @GetMapping("/movie/{id}")
    public String movieInfo(@PathVariable("id") int id, Model model) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) return "redirect:/home";
        model.addAttribute("movie", movie);
        return "movie_info";
    }
}
