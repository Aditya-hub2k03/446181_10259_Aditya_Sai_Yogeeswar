package com.yogesh.controller;

import com.yogesh.dto.movie.GetAverageMovieRequestDto;
import com.yogesh.dto.movie.GetAverageMovieResponseDto;
import com.yogesh.dto.movie.MovieRatingInfo;
import com.yogesh.dto.movie.RateMovieRequestDto;
import com.yogesh.dto.movie.RateMovieResponseDto;
import com.yogesh.exception.MovieRatingException;
import com.yogesh.model.Movie;
import com.yogesh.service.RatingsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ratings")
public class RatingsController {

    @Autowired
    private RatingsService ratingsService;

    @GetMapping("/rate-movie")
    public String showRateMovieForm(Model model, HttpSession session) throws ClassNotFoundException {
        if (!ratingsService.isUserLoggedIn(session)) {
            return "redirect:/login";
        }

        // Get all movies to display in the form
        List<Movie> movies = ratingsService.getAllMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("rateMovieRequest", new RateMovieRequestDto());
        return "rateMovie";
    }

    @PostMapping("/rate-movie")
    public String rateMovie(@ModelAttribute("rateMovieRequest") RateMovieRequestDto request, Model model, HttpSession session) throws ClassNotFoundException {
        try {
            RateMovieResponseDto response = ratingsService.rateMovie(request, session);
            model.addAttribute("message", "Movie rated successfully!");

            // Refresh the movie list
            List<Movie> movies = ratingsService.getAllMovies();
            model.addAttribute("movies", movies);

            model.addAttribute("rating", response.getMovieRating());
            return "rateMovie";
        } catch (MovieRatingException e) {
            // Refresh the movie list
            List<Movie> movies = ratingsService.getAllMovies();
            model.addAttribute("movies", movies);

            model.addAttribute("error", e.getMessage());
            return "rateMovie";
        }
    }

    @GetMapping("/movie-rating")
    public String showMovieRatingForm(Model model, HttpSession session) throws ClassNotFoundException {
        if (!ratingsService.isUserLoggedIn(session)) {
            return "redirect:/login";
        }

        // Get all movies with their ratings
        List<MovieRatingInfo> movieRatings = ratingsService.getAllMoviesWithRatings();
        model.addAttribute("movieRatings", movieRatings);
        return "movieRating";
    }

    @PostMapping("/movie-rating")
    public String getMovieRating(@ModelAttribute("movieRatingRequest") GetAverageMovieRequestDto request, Model model, HttpSession session) throws ClassNotFoundException {
        try {
            GetAverageMovieResponseDto response = ratingsService.getAverageMovieRating(request, session);
            model.addAttribute("averageRating", response.getAverageRating());

            // Refresh the movie ratings list
            List<MovieRatingInfo> movieRatings = ratingsService.getAllMoviesWithRatings();
            model.addAttribute("movieRatings", movieRatings);

            return "movieRating";
        } catch (MovieRatingException e) {
            // Refresh the movie ratings list
            List<MovieRatingInfo> movieRatings = ratingsService.getAllMoviesWithRatings();
            model.addAttribute("movieRatings", movieRatings);

            model.addAttribute("error", e.getMessage());
            return "movieRating";
        }
    }
}
