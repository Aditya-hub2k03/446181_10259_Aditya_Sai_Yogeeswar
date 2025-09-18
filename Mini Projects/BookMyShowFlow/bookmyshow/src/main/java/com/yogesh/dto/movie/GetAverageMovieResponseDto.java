package com.yogesh.dto.movie;

import com.yogesh.dto.ResponseStatus;
import lombok.Data;

@Data
public class GetAverageMovieResponseDto {
    private double averageRating;
    private ResponseStatus status;
	public double getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	public ResponseStatus getStatus() {
		return status;
	}
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
}
