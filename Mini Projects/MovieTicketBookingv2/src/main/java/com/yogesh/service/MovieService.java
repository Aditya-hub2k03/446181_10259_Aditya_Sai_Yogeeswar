package com.yogesh.service;

import com.yogesh.dao.MovieDAO;
import com.yogesh.dto.MovieDTO;
import com.yogesh.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieDAO movieDAO;

    public MovieDTO fetchCompleteMovieDetails(int movieId) {
        Movie movie = movieDAO.fetchCompleteMovieDetails(movieId);
        return convertToDTO(movie);
    }

    public List<MovieDTO> searchMoviesByKeyword(String keyword) {
        List<Movie> movies = movieDAO.searchMoviesByKeyword(keyword);
        return movies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieDAO.getAllMovies();
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

    private Movie convertToEntity(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setMovieId(movieDTO.getMovieId());
        movie.setTitle(movieDTO.getTitle());
        movie.setSynopsis(movieDTO.getSynopsis());
        movie.setCast(movieDTO.getCast());
        movie.setCrew(movieDTO.getCrew());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setPosterUrl(movieDTO.getPosterUrl());
        movie.setRating(movieDTO.getRating());
        movie.setGenre(movieDTO.getGenre());
        movie.setLanguage(movieDTO.getLanguage());
        movie.setFormats(movieDTO.getFormats());
        return movie;
    }
}
