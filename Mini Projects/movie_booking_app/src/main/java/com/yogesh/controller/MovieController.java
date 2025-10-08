package com.yogesh.controller;

import com.yogesh.model.Movie;
import com.yogesh.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/{id}")
    public String showMovieInfo(@PathVariable int id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "movie_info";
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam("query") String query, Model model) {
        model.addAttribute("results", movieService.searchMovies(query));
        return "search";
    }
}
