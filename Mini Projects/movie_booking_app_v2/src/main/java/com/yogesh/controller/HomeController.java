package com.yogesh.controller;

import com.yogesh.model.Movie;
import com.yogesh.model.User;
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

    @GetMapping("/home")
    public String homePage(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "home";
    }

    @GetMapping("/home_loggedin")
    public String homeLoggedIn(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("userName", user.getUserName());
        return "home_loggedin";
    }
}
