package com.yogesh.dto;

import java.util.List;

public class AuthDto {

    public static class LoginRequest {
        private String email;
        public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		private String password;
        // getters and setters
    }

    public static class RegisterRequest {
        private String name;
        private String email;
        private String password;
        // getters and setters
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
    }

    public static class AuthResponse {
        private String token;
        private String refreshToken;
        private List<String> roles;
        private long tokenExpiry;
        // getters and setters
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getRefreshToken() {
			return refreshToken;
		}
		public void setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
		}
		public List<String> getRoles() {
			return roles;
		}
		public void setRoles(List<String> roles) {
			this.roles = roles;
		}
		public long getTokenExpiry() {
			return tokenExpiry;
		}
		public void setTokenExpiry(long tokenExpiry) {
			this.tokenExpiry = tokenExpiry;
		}
    }
}
