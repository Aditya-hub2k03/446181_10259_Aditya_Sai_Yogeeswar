package com.yogesh.service;

import com.yogesh.model.Movie;
import com.yogesh.util.DBConnectionUtil;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    public List<Movie> getAllMovies() {
        List<Movie> list = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM movies");
            while (rs.next()) {
                Movie m = new Movie();
                m.setMovieId(rs.getInt("movie_id"));
                m.setTitle(rs.getString("title"));
                m.setLanguage(rs.getString("language"));
                m.setGenre(rs.getString("genre"));
                m.setSynopsis(rs.getString("synopsis"));
                m.setReleaseDate(rs.getDate("release_date"));
                m.setPosterUrl(rs.getString("poster_url")); // <-- Poster URL
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Movie getMovieById(int id) {
        try (Connection con = DBConnectionUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM movies WHERE movie_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Movie m = new Movie();
                m.setMovieId(rs.getInt("movie_id"));
                m.setTitle(rs.getString("title"));
                m.setLanguage(rs.getString("language"));
                m.setGenre(rs.getString("genre"));
                m.setSynopsis(rs.getString("synopsis"));
                m.setReleaseDate(rs.getDate("release_date"));
                m.setPosterUrl(rs.getString("poster_url")); // <-- Poster URL
                return m;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addMovie(Movie movie) {
        try (Connection con = DBConnectionUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO movies(title, language, genre, release_date, synopsis, poster_url) VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getLanguage());
            ps.setString(3, movie.getGenre());
            ps.setDate(4, new java.sql.Date(movie.getReleaseDate().getTime()));
            ps.setString(5, movie.getSynopsis());
            ps.setString(6, movie.getPosterUrl()); // <-- Poster URL
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
