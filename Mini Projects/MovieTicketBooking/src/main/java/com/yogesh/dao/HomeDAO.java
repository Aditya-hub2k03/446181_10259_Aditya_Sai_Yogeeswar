package com.yogesh.dao;

import com.yogesh.model.Movie;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class HomeDAO {

    // Fetch Language Based Movie List
    public List<Movie> fetchLanguageBasedMovies(String language) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE language = ? ORDER BY release_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, language);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
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
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // Fetch Top Rated Movies
    public List<Movie> fetchTopRatedMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies ORDER BY rating DESC LIMIT 10";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
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
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // Fetch Genre Based Movies
    public List<Movie> fetchGenreBasedMovies(String genre) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE genre = ? ORDER BY release_date DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
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
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // Fetch New Releases (within last 30 days)
    public List<Movie> fetchNewReleases() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE release_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
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
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // Fetch All Available Cities (with theatres)
    public List<String> fetchAllAvailableCities() {
        List<String> cities = new ArrayList<>();
        String sql = "SELECT DISTINCT city FROM theatres ORDER BY city";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cities.add(rs.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    // Fetch User Preferred Genre
    public String fetchUserPreferredGenre(int userId) {
        String sql = "SELECT preferred_genre FROM users WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("preferred_genre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Recommended Movies for User (based on genre)
    public List<Movie> fetchRecommendedMovies(int userId) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE genre = (SELECT preferred_genre FROM users WHERE user_id = ?) ORDER BY rating DESC LIMIT 5";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
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
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
