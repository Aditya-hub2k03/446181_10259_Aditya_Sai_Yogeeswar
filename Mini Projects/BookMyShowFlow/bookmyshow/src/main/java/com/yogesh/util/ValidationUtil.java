package com.yogesh.util;

import com.yogesh.exception.MovieRatingException;

public class ValidationUtil {
    public static void validateRating(int rating) throws MovieRatingException {
        if (rating < 1 || rating > 5) {
            throw new MovieRatingException("Rating must be between 1 and 5");
        }
    }
}
