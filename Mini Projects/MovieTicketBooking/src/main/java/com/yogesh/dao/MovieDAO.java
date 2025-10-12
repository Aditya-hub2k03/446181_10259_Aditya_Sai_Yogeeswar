package com.yogesh.dao;

import com.yogesh.model.Movie;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MovieDAO {

    // Fetch Complete Movie Details
    public Movie fetchCompleteMovieDetails(int movieId) {
        String sql = "SELECT movie_id, title, synopsis, cast, crew, release_date, poster_url, rating, genre, language, formats FROM movies WHERE movie_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setTitle(rs.getString("title"));
                movie.setSynopsis(rs.getString("synopsis"));
                movie.setCast(rs.getString("cast"));
                movie.setCrew(rs.getString("crew"));
                movie.setReleaseDate(rs.getDate("release_date"));
                movie.setPosterUrl(rs.getString("poster_url"));
                movie.setRating(rs.getDouble("rating"));
                movie.setGenre(rs.getString("genre"));
                movie.setLanguage(rs.getString("language"));
                movie.setFormats(rs.getString("formats"));
                return movie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Search Movies by Keyword (Title or Cast)
    public List<Movie> searchMoviesByKeyword(String keyword) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT movie_id, title, poster_url FROM movies WHERE title LIKE CONCAT('%', ?, '%') OR cast LIKE CONCAT('%', ?, '%')";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, keyword);
            stmt.setString(2, keyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setTitle(rs.getString("title"));
                movie.setPosterUrl(rs.getString("poster_url"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
