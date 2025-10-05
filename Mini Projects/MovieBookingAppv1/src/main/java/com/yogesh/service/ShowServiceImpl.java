package com.yogesh.service;

import com.yogesh.dto.ShowDto;
import com.yogesh.util.DbUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {

    @Override
    public List<ShowDto.ShowResponse> getAllShows() throws Exception {
        List<ShowDto.ShowResponse> shows = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM shows");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                shows.add(mapToResponse(rs));
            }
        }
        return shows;
    }

    @Override
    public ShowDto.ShowResponse getShowById(Long showId) throws Exception {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM shows WHERE show_id = ?")) {
            ps.setLong(1, showId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapToResponse(rs);
            }
        }
        return null;
    }

    @Override
    public void addShow(ShowDto.ShowRequest request) throws Exception {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO shows (movie_id, theater_id, show_time, format) VALUES (?, ?, ?, ?)")) {
            ps.setLong(1, request.getMovieId());
            ps.setLong(2, request.getTheaterId());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(request.getShowTime()));
            ps.setString(4, request.getFormat());
            ps.executeUpdate();
        }
    }

    @Override
    public void updateShow(Long showId, ShowDto.ShowRequest request) throws Exception {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE shows SET movie_id=?, theater_id=?, show_time=?, format=? WHERE show_id=?")) {
            ps.setLong(1, request.getMovieId());
            ps.setLong(2, request.getTheaterId());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(request.getShowTime()));
            ps.setString(4, request.getFormat());
            ps.setLong(5, showId);
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteShow(Long showId) throws Exception {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM shows WHERE show_id=?")) {
            ps.setLong(1, showId);
            ps.executeUpdate();
        }
    }

    private ShowDto.ShowResponse mapToResponse(ResultSet rs) throws Exception {
        ShowDto.ShowResponse res = new ShowDto.ShowResponse();
        res.setShowId(rs.getLong("show_id"));
        res.setMovieId(rs.getLong("movie_id"));
        res.setTheaterId(rs.getLong("theater_id"));
        res.setShowTime(rs.getTimestamp("show_time").toLocalDateTime().toString());
        res.setFormat(rs.getString("format"));
        return res;
    }
}
