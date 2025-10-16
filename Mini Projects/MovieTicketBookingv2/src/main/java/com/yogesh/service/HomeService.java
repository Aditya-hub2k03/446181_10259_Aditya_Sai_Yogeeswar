package com.yogesh.service;

import com.yogesh.dao.HomeDAO;
import com.yogesh.dto.MovieDTO;
import com.yogesh.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {

    @Autowired
    private HomeDAO homeDAO;

    public List<MovieDTO> fetchLanguageBasedMovies(String language) {
        List<Movie> movies = homeDAO.fetchLanguageBasedMovies(language);
        return movies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MovieDTO> fetchTopRatedMovies() {
        List<Movie> movies = homeDAO.fetchTopRatedMovies();
        return movies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MovieDTO> fetchGenreBasedMovies(String genre) {
        List<Movie> movies = homeDAO.fetchGenreBasedMovies(genre);
        return movies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MovieDTO> fetchNewReleases() {
        List<Movie> movies = homeDAO.fetchNewReleases();
        return movies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> fetchAllAvailableCities() {
        return homeDAO.fetchAllAvailableCities();
    }

    public String fetchUserPreferredGenre(int userId) {
        return homeDAO.fetchUserPreferredGenre(userId);
    }

    public List<MovieDTO> fetchRecommendedMovies(int userId) {
        List<Movie> movies = homeDAO.fetchRecommendedMovies(userId);
        return movies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setMovieId(movie.getMovieId());
        dto.setTitle(movie.getTitle());
        dto.setSynopsis(movie.getSynopsis());
        dto.setCast(movie.getCast());
        dto.setCrew(movie.getCrew());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setPosterUrl(movie.getPosterUrl());
        dto.setRating(movie.getRating());
        dto.setGenre(movie.getGenre());
        dto.setLanguage(movie.getLanguage());
        dto.setFormats(movie.getFormats());
        return dto;
    }
}
