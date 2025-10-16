package com.yogesh.dao;

import com.yogesh.model.Theatre;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TheatreDAO {

    // Fetch Theatres and Show Timings for a Movie
    public List<Theatre> fetchTheatresAndShowTimingsForMovie(int movieId) {
        List<Theatre> theatres = new ArrayList<>();
        String sql = "SELECT t.theatre_id, t.theatre_name, t.city, s.show_id, s.show_date, s.show_time, s.format " +
                     "FROM theatres t JOIN shows s ON t.theatre_id = s.theatre_id " +
                     "WHERE s.movie_id = ? AND s.show_date >= CURDATE() " +
                     "ORDER BY t.city, s.show_date, s.show_time";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Theatre theatre = new Theatre();
                theatre.setTheatreId(rs.getInt("theatre_id"));
                theatre.setTheatreName(rs.getString("theatre_name"));
                theatre.setCity(rs.getString("city"));
                theatres.add(theatre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theatres;
    }
    public List<Theatre> getAllTheatres() {
        List<Theatre> theatres = new ArrayList<>();
        String sql = "SELECT * FROM theatres";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Theatre theatre = new Theatre();
                theatre.setTheatreId(rs.getInt("theatre_id"));
                theatre.setTheatreName(rs.getString("theatre_name"));
                theatre.setCity(rs.getString("city"));
                theatre.setAddress(rs.getString("address"));
                theatres.add(theatre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theatres;
    }
    public Theatre findTheatreById(int theatreId) {
        String sql = "SELECT * FROM theatres WHERE theatre_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, theatreId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Theatre theatre = new Theatre();
                theatre.setTheatreId(rs.getInt("theatre_id"));
                theatre.setTheatreName(rs.getString("theatre_name"));
                theatre.setCity(rs.getString("city"));
                theatre.setAddress(rs.getString("address"));
                return theatre;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Fetch Available Dates for a Movie
    public List<String> fetchAvailableDatesForMovie(int movieId) {
        List<String> dates = new ArrayList<>();
        String sql = "SELECT DISTINCT show_date FROM shows WHERE movie_id = ? AND show_date >= CURDATE() ORDER BY show_date";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dates.add(rs.getString("show_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }
}
