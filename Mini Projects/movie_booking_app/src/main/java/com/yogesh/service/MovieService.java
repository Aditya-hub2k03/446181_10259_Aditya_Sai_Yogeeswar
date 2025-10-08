package com.yogesh.service;

import com.yogesh.model.Movie;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    public List<Movie> getTopRatedMovies() {
        return fetchMovies("SELECT * FROM movies ORDER BY rating DESC LIMIT 6");
    }

    public List<Movie> getNewReleases() {
        return fetchMovies("SELECT * FROM movies ORDER BY release_date DESC LIMIT 6");
    }

    public List<Movie> getMoviesByLanguage(String language) {
        return fetchMovies("SELECT * FROM movies WHERE language = '" + language + "'");
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return fetchMovies("SELECT * FROM movies WHERE genre = '" + genre + "'");
    }

    public List<Movie> searchMovies(String keyword) {
        return fetchMovies("SELECT * FROM movies WHERE title LIKE '%" + keyword + "%'");
    }

    public Movie getMovieById(int movieId) {
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM movies WHERE movie_id = ?");
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapMovie(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Movie> fetchMovies(String query) {
        List<Movie> list = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(mapMovie(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Movie mapMovie(ResultSet rs) throws SQLException {
        return Movie.builder()
                .movieId(rs.getInt("movie_id"))
                .title(rs.getString("title"))
                .language(rs.getString("language"))
                .genre(rs.getString("genre"))
                .releaseDate(rs.getDate("release_date"))
                .duration(rs.getInt("duration"))
                .posterUrl(rs.getString("poster_url"))
                .rating(rs.getFloat("rating"))
                .synopsis(rs.getString("synopsis"))
                .createdAt(rs.getTimestamp("created_at"))
                .build();
    }
}
