package com.yogesh.service;

import com.yogesh.dto.MovieDto;
import java.util.List;

public interface MovieService {
    List<MovieDto.MovieResponse> getAllMovies() throws Exception;
    MovieDto.MovieResponse getMovieById(Long movieId) throws Exception;
    void addMovie(MovieDto.MovieRequest request) throws Exception;
    void updateMovie(Long movieId, MovieDto.MovieRequest request) throws Exception;
    void deleteMovie(Long movieId) throws Exception;
}
