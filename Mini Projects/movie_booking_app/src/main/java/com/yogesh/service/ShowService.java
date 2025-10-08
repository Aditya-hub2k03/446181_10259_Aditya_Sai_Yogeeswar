package com.yogesh.service;

import com.yogesh.model.Show;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    public List<Show> getShowsByMovie(int movieId) {
        List<Show> shows = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM shows WHERE movie_id = ? ORDER BY show_time ASC");
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                shows.add(mapShow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shows;
    }

    private Show mapShow(ResultSet rs) throws SQLException {
        return Show.builder()
                .showId(rs.getInt("show_id"))
                .movieId(rs.getInt("movie_id"))
                .theatreId(rs.getInt("theatre_id"))
                .showTime(rs.getTimestamp("show_time"))
                .price(rs.getBigDecimal("price"))
                .availableSeats(rs.getInt("available_seats"))
                .build();
    }
}
