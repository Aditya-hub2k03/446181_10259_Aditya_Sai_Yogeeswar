package com.yogesh.controllers;

import com.yogesh.dto.MovieDTO;
import com.yogesh.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/details")
    public String movieDetails(@RequestParam(name = "movieId") int movieId, Model model) {
        MovieDTO movie = movieService.fetchCompleteMovieDetails(movieId);
        model.addAttribute("movie", movie);
        return "movie-details";
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam(name = "keyword") String keyword, Model model) {
        List<MovieDTO> movies = movieService.searchMoviesByKeyword(keyword);
        model.addAttribute("movies", movies);
        model.addAttribute("keyword", keyword);
        return "movie-search";
    }
}
