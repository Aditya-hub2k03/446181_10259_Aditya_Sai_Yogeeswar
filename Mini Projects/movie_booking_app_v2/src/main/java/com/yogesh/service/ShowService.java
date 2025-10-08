package com.yogesh.service;

import com.yogesh.model.Show;
import com.yogesh.util.DBConnectionUtil;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    public List<Show> getShowsByMovie(int movieId) {
        List<Show> list = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM shows WHERE movie_id=?");
            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Show s = new Show();
                s.setShowId(rs.getInt("show_id"));
                s.setMovieId(rs.getInt("movie_id"));
                s.setTheatreId(rs.getInt("theatre_id"));
                s.setShowTime(rs.getString("show_time"));
                s.setShowDate(rs.getDate("show_date"));
                s.setPrice(rs.getDouble("price"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addShow(Show show) {
        try (Connection con = DBConnectionUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO shows(movie_id, theatre_id, show_time, show_date, price) VALUES (?, ?, ?, ?, ?)"
            );
            ps.setInt(1, show.getMovieId());
            ps.setInt(2, show.getTheatreId());
            ps.setString(3, show.getShowTime());
            ps.setDate(4, new java.sql.Date(show.getShowDate().getTime()));
            ps.setDouble(5, show.getPrice());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ New method to fetch a show by its ID
    public Show getShowById(int showId) {
        try (Connection con = DBConnectionUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM shows WHERE show_id=?");
            ps.setInt(1, showId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Show s = new Show();
                s.setShowId(rs.getInt("show_id"));
                s.setMovieId(rs.getInt("movie_id"));
                s.setTheatreId(rs.getInt("theatre_id"));
                s.setShowTime(rs.getString("show_time"));
                s.setShowDate(rs.getDate("show_date"));
                s.setPrice(rs.getDouble("price"));
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
