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
