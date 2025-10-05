CREATE DATABASE IF NOT EXISTS movie_booking_db;
USE movie_booking_db;

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  roles VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  email_verified BOOLEAN DEFAULT FALSE
);

CREATE TABLE movies (
  movie_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  poster_url VARCHAR(300),
  genre VARCHAR(100),
  language VARCHAR(50),
  release_date DATE,
  runtime INT
);

CREATE TABLE theaters (
  theater_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  address VARCHAR(300),
  capacity INT
);

CREATE TABLE shows (
  show_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  movie_id BIGINT NOT NULL,
  theater_id BIGINT NOT NULL,
  show_time DATETIME NOT NULL,
  format VARCHAR(50),
  FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
  FOREIGN KEY (theater_id) REFERENCES theaters(theater_id)
);

CREATE TABLE seats (
  seat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  theater_id BIGINT NOT NULL,
  seat_number VARCHAR(10) NOT NULL,
  type VARCHAR(50),
  price_category VARCHAR(50),
  is_available BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (theater_id) REFERENCES theaters(theater_id)
);

CREATE TABLE bookings (
  booking_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  show_id BIGINT NOT NULL,
  booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  cancellation_status BOOLEAN DEFAULT FALSE,
  payment_status VARCHAR(50),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

CREATE TABLE booking_seats (
  booking_id BIGINT NOT NULL,
  seat_id BIGINT NOT NULL,
  PRIMARY KEY (booking_id, seat_id),
  FOREIGN KEY (booking_id) REFERENCES bookings(booking_id),
  FOREIGN KEY (seat_id) REFERENCES seats(seat_id)
);

CREATE TABLE payments (
  payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  booking_id BIGINT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  transaction_id VARCHAR(100),
  payment_method VARCHAR(50),
  gateway_response TEXT,
  FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);
