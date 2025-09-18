package com.yogesh.dto.movie;

import lombok.Data;

@Data
public class GetAverageMovieRequestDto {
    private Long movieId;

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
}
