package com.yogesh.controller;

import com.yogesh.model.Movie;
import com.yogesh.service.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public String homePage(Model model, HttpSession session) {
        List<Movie> topRated = movieService.getTopRatedMovies();
        List<Movie> newReleases = movieService.getNewReleases();
        List<Movie> byLanguage = movieService.getMoviesByLanguage("English");
        List<Movie> byGenre = movieService.getMoviesByGenre("Action");

        model.addAttribute("topRated", topRated);
        model.addAttribute("newReleases", newReleases);
        model.addAttribute("byLanguage", byLanguage);
        model.addAttribute("byGenre", byGenre);

        if (session.getAttribute("loggedUser") != null) {
            return "home_loggedin";
        }
        return "home";
    }
}
