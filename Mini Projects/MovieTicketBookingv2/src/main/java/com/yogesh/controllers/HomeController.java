package com.yogesh.controllers;

import com.yogesh.dto.MovieDTO;
import com.yogesh.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/home/common";
    }

    @GetMapping("/home/common")
    public String commonHome(
            @RequestParam(name = "language", defaultValue = "English") String language,
            Model model) {
        List<MovieDTO> languageBasedMovies = homeService.fetchLanguageBasedMovies(language);
        List<MovieDTO> topRatedMovies = homeService.fetchTopRatedMovies();
        List<MovieDTO> genreBasedMovies = homeService.fetchGenreBasedMovies("Action");
        List<MovieDTO> newReleases = homeService.fetchNewReleases();
        List<String> cities = homeService.fetchAllAvailableCities();

        model.addAttribute("languageBasedMovies", languageBasedMovies);
        model.addAttribute("topRatedMovies", topRatedMovies);
        model.addAttribute("genreBasedMovies", genreBasedMovies);
        model.addAttribute("newReleases", newReleases);
        model.addAttribute("cities", cities);

        return "home-common";
    }

    @GetMapping("/home/loggedin")
    public String loggedInHome(
            @SessionAttribute("userId") int userId,
            @RequestParam(name = "language", defaultValue = "English") String language,
            Model model) {
        List<MovieDTO> languageBasedMovies = homeService.fetchLanguageBasedMovies(language);
        List<MovieDTO> topRatedMovies = homeService.fetchTopRatedMovies();
        List<MovieDTO> genreBasedMovies = homeService.fetchGenreBasedMovies("Action");
        List<MovieDTO> newReleases = homeService.fetchNewReleases();
        List<String> cities = homeService.fetchAllAvailableCities();
        String preferredGenre = homeService.fetchUserPreferredGenre(userId);
        List<MovieDTO> recommendedMovies = homeService.fetchRecommendedMovies(userId);

        model.addAttribute("languageBasedMovies", languageBasedMovies);
        model.addAttribute("topRatedMovies", topRatedMovies);
        model.addAttribute("genreBasedMovies", genreBasedMovies);
        model.addAttribute("newReleases", newReleases);
        model.addAttribute("cities", cities);
        model.addAttribute("preferredGenre", preferredGenre);
        model.addAttribute("recommendedMovies", recommendedMovies);

        return "home-loggedin";
    }
}
