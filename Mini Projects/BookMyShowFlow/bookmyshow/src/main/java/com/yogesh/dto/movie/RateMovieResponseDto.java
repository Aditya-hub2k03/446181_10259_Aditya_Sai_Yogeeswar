package com.yogesh.dto.movie;

import com.yogesh.dto.ResponseStatus;
import com.yogesh.model.MovieRating;
import lombok.Data;

@Data
public class RateMovieResponseDto {
    private MovieRating movieRating;
    public MovieRating getMovieRating() {
		return movieRating;
	}
	public void setMovieRating(MovieRating movieRating) {
		this.movieRating = movieRating;
	}
	public ResponseStatus getStatus() {
		return status;
	}
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
	private ResponseStatus status;
}
