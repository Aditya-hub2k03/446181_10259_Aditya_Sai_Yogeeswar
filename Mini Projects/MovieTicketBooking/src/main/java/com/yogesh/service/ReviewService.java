package com.yogesh.service;

import com.yogesh.dao.ReviewDAO;
import com.yogesh.dto.ReviewDTO;
import com.yogesh.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;

    public List<ReviewDTO> fetchMovieReviews(int movieId) {
        List<Review> reviews = reviewDAO.fetchMovieReviews(movieId);
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public boolean addMovieReview(int movieId, int userId, double rating, String comment) {
        return reviewDAO.addMovieReview(movieId, userId, rating, comment);
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setReviewId(review.getReviewId());
        dto.setMovieId(review.getMovieId());
        dto.setUserId(review.getUserId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedOn(review.getCreatedOn());
        return dto;
    }
}
