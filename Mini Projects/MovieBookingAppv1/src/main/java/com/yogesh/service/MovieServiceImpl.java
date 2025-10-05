package com.yogesh.service;

import com.yogesh.dto.MovieDto;
import com.yogesh.util.DbUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Override
    public List<MovieDto.MovieResponse> getAllMovies() throws Exception {
        List<MovieDto.MovieResponse> movies = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM movies");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                movies.add(mapToResponse(rs));
            }
        }
        return movies;
    }

    @Override
    public MovieDto.MovieResponse getMovieById(Long movieId) throws Exception {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM movies WHERE movie_id = ?")) {
            ps.setLong(1, movieId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapToResponse(rs);
            }
        }
        return null;
    }

    @Override
    public void addMovie(MovieDto.MovieRequest request) throws Exception {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO movies (title, poster_url, genre, language, release_date, runtime) VALUES (?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, request.getTitle());
            ps.setString(2, request.getPosterUrl());
            ps.setString(3, request.getGenre());
            ps.setString(4, request.getLanguage());
            ps.setDate(5, java.sql.Date.valueOf(request.getReleaseDate()));
            ps.setInt(6, request.getRuntime());
            ps.executeUpdate();
        }
    }

    @Override
    public void updateMovie(Long movieId, MovieDto.MovieRequest request) throws Exception {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE movies SET title=?, poster_url=?, genre=?, language=?, release_date=?, runtime=? WHERE movie_id=?")) {
            ps.setString(1, request.getTitle());
            ps.setString(2, request.getPosterUrl());
            ps.setString(3, request.getGenre());
            ps.setString(4, request.getLanguage());
            ps.setDate(5, java.sql.Date.valueOf(request.getReleaseDate()));
            ps.setInt(6, request.getRuntime());
            ps.setLong(7, movieId);
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteMovie(Long movieId) throws Exception {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM movies WHERE movie_id=?")) {
            ps.setLong(1, movieId);
            ps.executeUpdate();
        }
    }

    private MovieDto.MovieResponse mapToResponse(ResultSet rs) throws Exception {
        MovieDto.MovieResponse res = new MovieDto.MovieResponse();
        res.setMovieId(rs.getLong("movie_id"));
        res.setTitle(rs.getString("title"));
        res.setPosterUrl(rs.getString("poster_url"));
        res.setGenre(rs.getString("genre"));
        res.setLanguage(rs.getString("language"));
        res.setReleaseDate(rs.getDate("release_date").toString());
        res.setRuntime(rs.getInt("runtime"));
        return res;
    }
}
