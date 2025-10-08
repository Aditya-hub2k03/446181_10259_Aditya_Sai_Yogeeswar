CREATE DATABASE movie_booking_app;
USE movie_booking_app;

-- USERS TABLE
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password_hash VARCHAR(255),
    role ENUM('USER','OWNER','ADMIN') DEFAULT 'USER',
    city VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- THEATRES
CREATE TABLE theatres (
    theatre_id INT AUTO_INCREMENT PRIMARY KEY,
    theatre_name VARCHAR(150),
    city VARCHAR(100),
    address VARCHAR(255),
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES users(user_id)
);

-- MOVIES
CREATE TABLE movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200),
    language VARCHAR(50),
    genre VARCHAR(100),
    release_date DATE,
    duration INT,
    poster_url VARCHAR(255),
    rating FLOAT DEFAULT 0,
    synopsis TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- SHOWS
CREATE TABLE shows (
    show_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT,
    theatre_id INT,
    show_time DATETIME,
    price DECIMAL(8,2),
    available_seats INT,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (theatre_id) REFERENCES theatres(theatre_id)
);

-- BOOKINGS
CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    show_id INT,
    total_amount DECIMAL(10,2),
    status ENUM('PENDING','CONFIRMED','FAILED') DEFAULT 'PENDING',
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

-- PAYMENTS
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT,
    payment_method VARCHAR(50),
    amount DECIMAL(10,2),
    payment_status ENUM('SUCCESS','FAILED','PENDING'),
    payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);

-- BOOKMARKS
CREATE TABLE bookmarks (
    user_id INT,
    movie_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, movie_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);
