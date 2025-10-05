package com.yogesh.controller;

import com.yogesh.dto.ApiResponse;
import com.yogesh.dto.MovieDto;
import com.yogesh.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieDto.MovieResponse>>> getAllMovies() throws Exception {
        List<MovieDto.MovieResponse> movies = movieService.getAllMovies();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Movies retrieved successfully", movies));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieDto.MovieResponse>> getMovieById(@PathVariable Long id) throws Exception {
        MovieDto.MovieResponse movie = movieService.getMovieById(id);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Movie not found", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Movie retrieved successfully", movie));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addMovie(@RequestBody MovieDto.MovieRequest request) throws Exception {
        movieService.addMovie(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Movie added successfully", "Movie created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateMovie(@PathVariable Long id, @RequestBody MovieDto.MovieRequest request) throws Exception {
        movieService.updateMovie(id, request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Movie updated successfully", "Movie updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMovie(@PathVariable Long id) throws Exception {
        movieService.deleteMovie(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Movie deleted successfully", "Movie deleted"));
    }
}
