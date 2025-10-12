package com.yogesh.dao;

import com.yogesh.model.Bookmark;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class BookmarkDAO {

    // Fetch Interested / Bookmarked Movies
    public List<Bookmark> fetchBookmarkedMovies(int userId) {
        List<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT bookmark_id, user_id, movie_id, created_on " +
                     "FROM bookmarks WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bookmark bookmark = new Bookmark();
                bookmark.setBookmarkId(rs.getInt("bookmark_id"));
                bookmark.setUserId(rs.getInt("user_id"));
                bookmark.setMovieId(rs.getInt("movie_id"));
                bookmark.setCreatedOn(rs.getTimestamp("created_on"));
                bookmarks.add(bookmark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarks;
    }

    // Add Bookmark
    public boolean addBookmark(int userId, int movieId) {
        String sql = "INSERT INTO bookmarks (user_id, movie_id, created_on) VALUES (?, ?, NOW())";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, movieId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
