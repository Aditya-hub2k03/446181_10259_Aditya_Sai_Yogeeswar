package com.yogesh.dto;

public class MovieDto {

    public static class MovieRequest {
        private String title;
        private String posterUrl;
        private String genre;
        private String language;
        public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getPosterUrl() {
			return posterUrl;
		}
		public void setPosterUrl(String posterUrl) {
			this.posterUrl = posterUrl;
		}
		public String getGenre() {
			return genre;
		}
		public void setGenre(String genre) {
			this.genre = genre;
		}
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		public String getReleaseDate() {
			return releaseDate;
		}
		public void setReleaseDate(String releaseDate) {
			this.releaseDate = releaseDate;
		}
		public Integer getRuntime() {
			return runtime;
		}
		public void setRuntime(Integer runtime) {
			this.runtime = runtime;
		}
		private String releaseDate;  // ISO string e.g. "2023-05-31"
        private Integer runtime;
        // getters and setters
    }

    public static class MovieResponse {
        private Long movieId;
        private String title;
        private String posterUrl;
        private String genre;
        private String language;
        public Long getMovieId() {
			return movieId;
		}
		public void setMovieId(Long movieId) {
			this.movieId = movieId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getPosterUrl() {
			return posterUrl;
		}
		public void setPosterUrl(String posterUrl) {
			this.posterUrl = posterUrl;
		}
		public String getGenre() {
			return genre;
		}
		public void setGenre(String genre) {
			this.genre = genre;
		}
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		public String getReleaseDate() {
			return releaseDate;
		}
		public void setReleaseDate(String releaseDate) {
			this.releaseDate = releaseDate;
		}
		public Integer getRuntime() {
			return runtime;
		}
		public void setRuntime(Integer runtime) {
			this.runtime = runtime;
		}
		private String releaseDate;
        private Integer runtime;
        // getters and setters
    }
}
