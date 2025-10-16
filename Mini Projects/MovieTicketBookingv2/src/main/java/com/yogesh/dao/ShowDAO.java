package com.yogesh.dao;

import com.yogesh.model.Show;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ShowDAO {

    // Fetch Show Details by Show ID
    public Show fetchShowDetails(int showId) {
        String sql = "SELECT show_id, movie_id, theatre_id, show_date, show_time, format FROM shows WHERE show_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Show show = new Show();
                show.setShowId(rs.getInt("show_id"));
                show.setMovieId(rs.getInt("movie_id"));
                show.setTheatreId(rs.getInt("theatre_id"));
                show.setShowDate(rs.getDate("show_date"));
                show.setShowTime(rs.getTime("show_time"));
                show.setFormat(rs.getString("format"));
                return show;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Show> getShowsByTheatreId(int theatreId) {
        List<Show> shows = new ArrayList<>();
        String sql = "SELECT * FROM shows WHERE theatre_id = ? ORDER BY show_date, show_time";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, theatreId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Show show = new Show();
                show.setShowId(rs.getInt("show_id"));
                show.setTheatreId(rs.getInt("theatre_id"));
                show.setMovieId(rs.getInt("movie_id"));
                show.setShowDate(rs.getDate("show_date"));
                show.setShowTime(rs.getTime("show_time"));
                show.setFormat(rs.getString("format"));
                shows.add(show);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shows;
    }

    public Show getShowById(int showId) {
        String sql = "SELECT * FROM shows WHERE show_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Show show = new Show();
                show.setShowId(rs.getInt("show_id"));
                show.setTheatreId(rs.getInt("theatre_id"));
                show.setMovieId(rs.getInt("movie_id"));
                show.setShowDate(rs.getDate("show_date"));
                show.setShowTime(rs.getTime("show_time"));
                show.setFormat(rs.getString("format"));
                return show;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addShow(int theatreId, int movieId, String showDate, String showTime, String format) {
        String sql = "INSERT INTO shows (theatre_id, movie_id, show_date, show_time, format) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, theatreId);
            stmt.setInt(2, movieId);
            stmt.setString(3, showDate);
            stmt.setString(4, showTime);
            stmt.setString(5, format);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateShow(int showId, String showDate, String showTime, String format) {
        String sql = "UPDATE shows SET show_date = ?, show_time = ?, format = ? WHERE show_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, showDate);
            stmt.setString(2, showTime);
            stmt.setString(3, format);
            stmt.setInt(4, showId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch Shows by Theatre and Movie
    public List<Show> fetchShowsByTheatreAndMovie(int theatreId, int movieId) {
        List<Show> shows = new ArrayList<>();
        String sql = "SELECT show_id, movie_id, theatre_id, show_date, show_time, format " +
                     "FROM shows WHERE theatre_id = ? AND movie_id = ? AND show_date >= CURDATE() " +
                     "ORDER BY show_date, show_time";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, theatreId);
            stmt.setInt(2, movieId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Show show = new Show();
                show.setShowId(rs.getInt("show_id"));
                show.setMovieId(rs.getInt("movie_id"));
                show.setTheatreId(rs.getInt("theatre_id"));
                show.setShowDate(rs.getDate("show_date"));
                show.setShowTime(rs.getTime("show_time"));
                show.setFormat(rs.getString("format"));
                shows.add(show);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shows;
    }
}
