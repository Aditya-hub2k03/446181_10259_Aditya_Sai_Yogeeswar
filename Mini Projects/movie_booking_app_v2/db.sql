CREATE DATABASE IF NOT EXISTS movie_booking_db;
USE movie_booking_db;

-- Users
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Movies
CREATE TABLE movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    language VARCHAR(50),
    genre VARCHAR(50),
    synopsis TEXT,
    release_date DATE,
    poster_url VARCHAR(500)
);

-- Theatres
CREATE TABLE theatres (
    theatre_id INT AUTO_INCREMENT PRIMARY KEY,
    theatre_name VARCHAR(255) NOT NULL,
    city VARCHAR(100),
    address VARCHAR(255)
);

-- Shows
CREATE TABLE shows (
    show_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    theatre_id INT NOT NULL,
    show_time VARCHAR(10),
    show_date DATE,
    price DOUBLE,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (theatre_id) REFERENCES theatres(theatre_id)
);

-- Seats
CREATE TABLE seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    show_id INT NOT NULL,
    seat_no VARCHAR(10),
    type VARCHAR(20) DEFAULT 'REGULAR', -- REGULAR, HANDICAPPED
    status VARCHAR(20) DEFAULT 'AVAILABLE', -- AVAILABLE, SOLD, BEST_SELLING
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

-- Bookings
CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    show_id INT NOT NULL,
    seat_count INT,
    selected_seats VARCHAR(255),
    total_amount DOUBLE,
    status VARCHAR(20) DEFAULT 'CONFIRMED',
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);
