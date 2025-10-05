package com.yogesh.dto;

public class ShowDto {

    public static class ShowRequest {
        private Long movieId;
        private Long theaterId;
        private String showTime;  // ISO datetime string e.g. "2025-10-04T14:00:00"
        private String format;
        // getters and setters
		public Long getMovieId() {
			return movieId;
		}
		public void setMovieId(Long movieId) {
			this.movieId = movieId;
		}
		public Long getTheaterId() {
			return theaterId;
		}
		public void setTheaterId(Long theaterId) {
			this.theaterId = theaterId;
		}
		public String getShowTime() {
			return showTime;
		}
		public void setShowTime(String showTime) {
			this.showTime = showTime;
		}
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
    }

    public static class ShowResponse {
        private Long showId;
        private Long movieId;
        private Long theaterId;
        private String showTime;
        public Long getShowId() {
			return showId;
		}
		public void setShowId(Long showId) {
			this.showId = showId;
		}
		public Long getMovieId() {
			return movieId;
		}
		public void setMovieId(Long movieId) {
			this.movieId = movieId;
		}
		public Long getTheaterId() {
			return theaterId;
		}
		public void setTheaterId(Long theaterId) {
			this.theaterId = theaterId;
		}
		public String getShowTime() {
			return showTime;
		}
		public void setShowTime(String showTime) {
			this.showTime = showTime;
		}
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
		private String format;
        // getters and setters
    }
}
