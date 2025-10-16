package com.yogesh.dao;

import com.yogesh.model.Review;
import com.yogesh.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDAO {

    // Fetch Movie Reviews
    public List<Review> fetchMovieReviews(int movieId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT review_id, movie_id, user_id, rating, comment, created_on " +
                     "FROM reviews WHERE movie_id = ? ORDER BY created_on DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Review review = new Review();
                review.setReviewId(rs.getInt("review_id"));
                review.setMovieId(rs.getInt("movie_id"));
                review.setUserId(rs.getInt("user_id"));
                review.setRating(rs.getDouble("rating"));
                review.setComment(rs.getString("comment"));
                review.setCreatedOn(rs.getTimestamp("created_on"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // Add Movie Review
    public boolean addMovieReview(int movieId, int userId, double rating, String comment) {
        String sql = "INSERT INTO reviews (movie_id, user_id, rating, comment, created_on) VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            stmt.setInt(2, userId);
            stmt.setDouble(3, rating);
            stmt.setString(4, comment);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
