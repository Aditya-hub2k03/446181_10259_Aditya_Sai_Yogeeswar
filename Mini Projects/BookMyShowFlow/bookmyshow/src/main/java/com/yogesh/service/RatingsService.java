package com.yogesh.service;

import com.yogesh.dto.ResponseStatus;
import com.yogesh.dto.movie.GetAverageMovieRequestDto;
import com.yogesh.dto.movie.GetAverageMovieResponseDto;
import com.yogesh.dto.movie.MovieRatingInfo;
import com.yogesh.dto.movie.RateMovieRequestDto;
import com.yogesh.dto.movie.RateMovieResponseDto;
import com.yogesh.exception.MovieRatingException;
import com.yogesh.model.Movie;
import com.yogesh.model.MovieRating;
import com.yogesh.util.DBCon;
import com.yogesh.util.ValidationUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingsService {

    @Autowired
    private UserService userService;

    public RateMovieResponseDto rateMovie(RateMovieRequestDto request, HttpSession session) throws MovieRatingException, ClassNotFoundException {
        // Set user ID from session if not provided
        if (request.getUserId() == null) {
            Long userId = userService.getCurrentUserId(session);
            if (userId == null) {
                throw new MovieRatingException("User not logged in");
            }
            request.setUserId(userId);
        }

        ValidationUtil.validateRating(request.getRating());

        if (!movieExists(request.getMovieId())) {
            throw new MovieRatingException("Movie not found with ID: " + request.getMovieId());
        }

        Optional<MovieRating> existingRating = findByUserIdAndMovieId(request.getUserId(), request.getMovieId());

        MovieRating movieRating;
        if (existingRating.isPresent()) {
            movieRating = existingRating.get();
            movieRating.setRating(request.getRating());
            movieRating.setModifiedOn(LocalDateTime.now());
            updateMovieRating(movieRating);
        } else {
            movieRating = new MovieRating();
            movieRating.setUserId(request.getUserId());
            movieRating.setMovieId(request.getMovieId());
            movieRating.setRating(request.getRating());
            movieRating.setCreatedOn(LocalDateTime.now());
            saveMovieRating(movieRating);
        }

        RateMovieResponseDto response = new RateMovieResponseDto();
        response.setMovieRating(movieRating);
        response.setStatus(ResponseStatus.SUCCESS);

        return response;
    }

    public GetAverageMovieResponseDto getAverageMovieRating(GetAverageMovieRequestDto request, HttpSession session) throws MovieRatingException, ClassNotFoundException {
        // Check if user is logged in
        if (!userService.isUserLoggedIn(session)) {
            throw new MovieRatingException("User not logged in");
        }

        if (!movieExists(request.getMovieId())) {
            throw new MovieRatingException("Movie not found with ID: " + request.getMovieId());
        }

        double averageRating = getAverageRatingByMovieId(request.getMovieId());

        GetAverageMovieResponseDto response = new GetAverageMovieResponseDto();
        response.setAverageRating(averageRating);
        response.setStatus(ResponseStatus.SUCCESS);

        return response;
    }

    public boolean isUserLoggedIn(HttpSession session) {
        return userService.isUserLoggedIn(session);
    }

    public List<Movie> getAllMovies() throws ClassNotFoundException {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies";

        try (Connection connection = DBCon.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setMovieId(resultSet.getLong("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDescription(resultSet.getString("description"));
                movie.setGenre(resultSet.getString("genre"));
                movie.setDuration(resultSet.getInt("duration"));
                movie.setReleaseDate(resultSet.getString("release_date"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return movies;
    }

    public List<MovieRatingInfo> getAllMoviesWithRatings() throws ClassNotFoundException {
        List<MovieRatingInfo> movieRatings = new ArrayList<>();
        String query = "SELECT m.movie_id, m.title, m.description, m.genre, m.duration, m.release_date, " +
                "AVG(mr.rating) as average_rating, COUNT(mr.rating_id) as rating_count " +
                "FROM movies m LEFT JOIN movie_ratings mr ON m.movie_id = mr.movie_id " +
                "GROUP BY m.movie_id";

        try (Connection connection = DBCon.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                MovieRatingInfo info = new MovieRatingInfo();
                info.setMovieId(resultSet.getLong("movie_id"));
                info.setTitle(resultSet.getString("title"));
                info.setDescription(resultSet.getString("description"));
                info.setGenre(resultSet.getString("genre"));
                info.setDuration(resultSet.getInt("duration"));
                info.setReleaseDate(resultSet.getString("release_date"));
                info.setAverageRating(resultSet.getDouble("average_rating"));
                info.setRatingCount(resultSet.getInt("rating_count"));
                movieRatings.add(info);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return movieRatings;
    }

    private boolean movieExists(Long movieId) throws ClassNotFoundException {
        String query = "SELECT 1 FROM movies WHERE movie_id = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, movieId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    private Optional<MovieRating> findByUserIdAndMovieId(Long userId, Long movieId) throws ClassNotFoundException {
        String query = "SELECT * FROM movie_ratings WHERE user_id = ? AND movie_id = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, userId);
            statement.setLong(2, movieId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    MovieRating movieRating = new MovieRating();
                    movieRating.setRatingId(resultSet.getLong("rating_id"));
                    movieRating.setUserId(resultSet.getLong("user_id"));
                    movieRating.setMovieId(resultSet.getLong("movie_id"));
                    movieRating.setRating(resultSet.getInt("rating"));
                    movieRating.setCreatedOn(resultSet.getTimestamp("created_on").toLocalDateTime());
                    if (resultSet.getTimestamp("modified_on") != null) {
                        movieRating.setModifiedOn(resultSet.getTimestamp("modified_on").toLocalDateTime());
                    }
                    return Optional.of(movieRating);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    private void saveMovieRating(MovieRating movieRating) throws ClassNotFoundException {
        String query = "INSERT INTO movie_ratings (user_id, movie_id, rating, created_on) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, movieRating.getUserId());
            statement.setLong(2, movieRating.getMovieId());
            statement.setInt(3, movieRating.getRating());
            statement.setTimestamp(4, Timestamp.valueOf(movieRating.getCreatedOn()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating movie rating failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    movieRating.setRatingId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating movie rating failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    private void updateMovieRating(MovieRating movieRating) throws ClassNotFoundException {
        String query = "UPDATE movie_ratings SET rating = ?, modified_on = ? WHERE rating_id = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, movieRating.getRating());
            statement.setTimestamp(2, Timestamp.valueOf(movieRating.getModifiedOn()));
            statement.setLong(3, movieRating.getRatingId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating movie rating failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    private double getAverageRatingByMovieId(Long movieId) throws ClassNotFoundException {
        String query = "SELECT AVG(rating) as average_rating FROM movie_ratings WHERE movie_id = ?";

        try (Connection connection = DBCon.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, movieId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("average_rating");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return 0.0;
    }
}
