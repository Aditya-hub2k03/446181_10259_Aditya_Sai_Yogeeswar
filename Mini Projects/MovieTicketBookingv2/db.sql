-- Database: movie_ticket_booking
-- Tables: users, movies, theatres, shows, seats, bookings, payments, reviews, bookmarks

CREATE DATABASE movie_ticket_booking;
USE movie_ticket_booking;

-- Users Table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    city VARCHAR(50),
    preferred_genre VARCHAR(50),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Movies Table
CREATE TABLE movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    synopsis TEXT,
    cast TEXT,
    crew TEXT,
    release_date DATE,
    poster_url VARCHAR(255),
    rating DECIMAL(3,1),
    genre VARCHAR(50),
    language VARCHAR(50),
    formats VARCHAR(50)
);

-- Theatres Table
CREATE TABLE theatres (
    theatre_id INT AUTO_INCREMENT PRIMARY KEY,
    theatre_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    address TEXT
);

-- Shows Table
CREATE TABLE shows (
    show_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    theatre_id INT NOT NULL,
    show_date DATE NOT NULL,
    show_time TIME NOT NULL,
    format VARCHAR(20),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (theatre_id) REFERENCES theatres(theatre_id)
);

-- Seats Table
CREATE TABLE seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    show_id INT NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    seat_type VARCHAR(20) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    is_handicapped BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

-- Bookings Table
CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    show_id INT NOT NULL,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

-- Payments Table
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    user_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    transaction_id VARCHAR(100),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Reviews Table
CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    user_id INT NOT NULL,
    rating DECIMAL(3,1) NOT NULL,
    comment TEXT,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Bookmarks Table
CREATE TABLE bookmarks (
    bookmark_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);







--Sample Data


INSERT INTO users (user_name, email, password, city, preferred_genre, created_on, modified_on)
VALUES
    ('John Doe', 'john.doe@example.com', 'password123', 'Mumbai', 'Action', NOW(), NOW()),
    ('Jane Smith', 'jane.smith@example.com', 'password456', 'Delhi', 'Comedy', NOW(), NOW()),
    ('Alice Johnson', 'alice.johnson@example.com', 'password789', 'Bangalore', 'Drama', NOW(), NOW()),
    ('Bob Brown', 'bob.brown@example.com', 'passwordabc', 'Hyderabad', 'Sci-Fi', NOW(), NOW()),
    ('Charlie Davis', 'charlie.davis@example.com', 'passworddef', 'Chennai', 'Thriller', NOW(), NOW());

INSERT INTO movies (title, synopsis, cast, crew, release_date, poster_url, rating, genre, language, formats)
VALUES
    ('The Dark Knight', 'A superhero movie about Batman.', 'Christian Bale, Heath Ledger', 'Christopher Nolan', '2008-07-18', 'https://example.com/posters/dark_knight.jpg', 9.0, 'Action', 'English', '2D, IMAX'),
    ('Inception', 'A thief who steals corporate secrets through dream-sharing technology.', 'Leonardo DiCaprio, Joseph Gordon-Levitt', 'Christopher Nolan', '2010-07-16', 'https://example.com/posters/inception.jpg', 8.8, 'Sci-Fi', 'English', '2D, 3D'),
    ('Dangal', 'A former wrestler trains his daughters to become world-class wrestlers.', 'Aamir Khan, Fatima Sana Shaikh', 'Nitesh Tiwari', '2016-12-23', 'https://example.com/posters/dangal.jpg', 8.4, 'Drama', 'Hindi', '2D'),
    ('Baahubali: The Beginning', 'A young man learns about his past and seeks justice for his family.', 'Prabhas, Rana Daggubati', 'S.S. Rajamouli', '2015-07-10', 'https://example.com/posters/baahubali.jpg', 8.2, 'Action', 'Telugu', '2D, 3D'),
    ('3 Idiots', 'Two friends embark on a journey to find their lost friend.', 'Aamir Khan, R. Madhavan', 'Rajkumar Hirani', '2009-12-25', 'https://example.com/posters/3idiots.jpg', 8.4, 'Comedy', 'Hindi', '2D');
INSERT INTO theatres (theatre_name, city, address)
VALUES
    ('PVR Cinemas', 'Mumbai', 'Phoenix Marketcity, Kurla'),
    ('INOX', 'Delhi', 'Saket, New Delhi'),
    ('Cinepolis', 'Bangalore', 'Forum Mall, Koramangala'),
    ('PVR', 'Hyderabad', 'Inorbit Mall, Cyberabad'),
    ('AGS Cinemas', 'Chennai', 'AGS Cinema, OMR');
INSERT INTO shows (movie_id, theatre_id, show_date, show_time, format)
VALUES
    (1, 1, '2025-10-15', '18:00:00', '2D'),
    (1, 1, '2025-10-15', '21:00:00', 'IMAX'),
    (2, 2, '2025-10-16', '15:00:00', '2D'),
    (2, 2, '2025-10-16', '19:00:00', '3D'),
    (3, 3, '2025-10-17', '12:00:00', '2D'),
    (3, 3, '2025-10-17', '16:00:00', '2D'),
    (4, 4, '2025-10-18', '14:00:00', '3D'),
    (4, 4, '2025-10-18', '20:00:00', '2D'),
    (5, 5, '2025-10-19', '11:00:00', '2D'),
    (5, 5, '2025-10-19', '17:00:00', '2D');
-- For Show ID 1
INSERT INTO seats (show_id, seat_number, seat_type, price, is_available, is_handicapped)
VALUES
    (1, 'A1', 'Standard', 250.00, TRUE, FALSE),
    (1, 'A2', 'Standard', 250.00, TRUE, FALSE),
    (1, 'A3', 'Standard', 250.00, TRUE, FALSE),
    (1, 'A4', 'Standard', 250.00, TRUE, FALSE),
    (1, 'A5', 'Standard', 250.00, TRUE, FALSE),
    (1, 'B1', 'Premium', 350.00, TRUE, FALSE),
    (1, 'B2', 'Premium', 350.00, TRUE, FALSE),
    (1, 'B3', 'Premium', 350.00, TRUE, FALSE),
    (1, 'B4', 'Premium', 350.00, TRUE, FALSE),
    (1, 'B5', 'Premium', 350.00, TRUE, FALSE);

-- For Show ID 2
INSERT INTO seats (show_id, seat_number, seat_type, price, is_available, is_handicapped)
VALUES
    (2, 'A1', 'Standard', 300.00, TRUE, FALSE),
    (2, 'A2', 'Standard', 300.00, TRUE, FALSE),
    (2, 'A3', 'Standard', 300.00, TRUE, FALSE),
    (2, 'A4', 'Standard', 300.00, TRUE, FALSE),
    (2, 'A5', 'Standard', 300.00, TRUE, FALSE),
    (2, 'B1', 'Premium', 400.00, TRUE, FALSE),
    (2, 'B2', 'Premium', 400.00, TRUE, FALSE),
    (2, 'B3', 'Premium', 400.00, TRUE, FALSE),
    (2, 'B4', 'Premium', 400.00, TRUE, FALSE),
    (2, 'B5', 'Premium', 400.00, TRUE, FALSE);
INSERT INTO bookings (user_id, show_id, booking_time, total_price, status, created_on, modified_on)
VALUES
    (1, 1, NOW(), 500.00, 'CONFIRMED', NOW(), NOW()),
    (2, 2, NOW(), 800.00, 'CONFIRMED', NOW(), NOW()),
    (3, 3, NOW(), 500.00, 'PENDING', NOW(), NOW()),
    (4, 4, NOW(), 700.00, 'CONFIRMED', NOW(), NOW()),
    (5, 5, NOW(), 500.00, 'FAILED', NOW(), NOW());
INSERT INTO payments (booking_id, user_id, amount, payment_method, payment_status, transaction_id, created_on)
VALUES
    (1, 1, 500.00, 'CREDIT_CARD', 'SUCCESS', 'TXN123456', NOW()),
    (2, 2, 800.00, 'DEBIT_CARD', 'SUCCESS', 'TXN789012', NOW()),
    (4, 4, 700.00, 'UPI', 'SUCCESS', 'TXN345678', NOW()),
    (5, 5, 500.00, 'CREDIT_CARD', 'FAILED', 'TXN901234', NOW());
INSERT INTO reviews (movie_id, user_id, rating, comment, created_on)
VALUES
    (1, 1, 9.0, 'One of the best superhero movies ever!', NOW()),
    (1, 2, 8.5, 'Heath Ledger was amazing as Joker.', NOW()),
    (2, 3, 9.0, 'Mind-blowing concept and execution.', NOW()),
    (3, 4, 8.0, 'Aamir Khan delivered a powerful performance.', NOW()),
    (4, 5, 8.5, 'Visual effects were stunning!', NOW()),
    (5, 1, 9.0, 'A must-watch for everyone.', NOW());
INSERT INTO bookmarks (user_id, movie_id, created_on)
VALUES
    (1, 2, NOW()),
    (1, 3, NOW()),
    (2, 1, NOW()),
    (2, 4, NOW()),
    (3, 5, NOW()),
    (4, 1, NOW()),
    (5, 2, NOW()),
    (5, 3, NOW());

    
    
    
    
-- New Added tables and changes by 15-10-2025
    
CREATE TABLE roles (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
    user_role_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE theatre_admins (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    theatre_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (theatre_id) REFERENCES theatres(theatre_id)
);

ALTER TABLE users ADD COLUMN password_hash VARCHAR(255);

INSERT INTO roles (role_name) VALUES
('USER'),
('THEATRE_ADMIN'),
('APPLICATION_ADMIN');









-- ////////////////////////////////////////////////////////////////////////

-- Database: movie_ticket_booking
-- Tables: users, movies, theatres, shows, seats, bookings, payments, reviews, bookmarks
CREATE DATABASE IF NOT EXISTS movie_ticket_booking;
USE movie_ticket_booking;

-- Users Table (updated with password_hash column)
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100),
    password_hash VARCHAR(255),
    city VARCHAR(50),
    preferred_genre VARCHAR(50),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Movies Table (updated with your schema)
CREATE TABLE IF NOT EXISTS movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    synopsis TEXT,
    cast TEXT,
    crew TEXT,
    release_date DATE,
    poster_url VARCHAR(255),
    rating DECIMAL(3,1),
    genre VARCHAR(50),
    language VARCHAR(50),
    formats VARCHAR(50)
);

-- Theatres Table
CREATE TABLE IF NOT EXISTS theatres (
    theatre_id INT AUTO_INCREMENT PRIMARY KEY,
    theatre_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    address TEXT
);

-- Shows Table
CREATE TABLE IF NOT EXISTS shows (
    show_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    theatre_id INT NOT NULL,
    show_date DATE NOT NULL,
    show_time TIME NOT NULL,
    format VARCHAR(20),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (theatre_id) REFERENCES theatres(theatre_id)
);

-- Seats Table
CREATE TABLE IF NOT EXISTS seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    show_id INT NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    seat_type VARCHAR(20) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    is_handicapped BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

-- Bookings Table
CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    show_id INT NOT NULL,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

-- Payments Table
CREATE TABLE IF NOT EXISTS payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    user_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    transaction_id VARCHAR(100),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Reviews Table
CREATE TABLE IF NOT EXISTS reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    user_id INT NOT NULL,
    rating DECIMAL(3,1) NOT NULL,
    comment TEXT,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Bookmarks Table
CREATE TABLE IF NOT EXISTS bookmarks (
    bookmark_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);

-- New tables for role management
CREATE TABLE IF NOT EXISTS roles (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_role_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE IF NOT EXISTS theatre_admins (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    theatre_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (theatre_id) REFERENCES theatres(theatre_id)
);

-- Sample Data for Users
INSERT IGNORE INTO users (user_name, email, password, password_hash, city, preferred_genre, created_on, modified_on)
VALUES
    ('John Doe', 'john.doe@example.com', 'password123', NULL, 'Mumbai', 'Action', NOW(), NOW()),
    ('Jane Smith', 'jane.smith@example.com', 'password456', NULL, 'Delhi', 'Comedy', NOW(), NOW()),
    ('Alice Johnson', 'alice.johnson@example.com', 'password789', NULL, 'Bangalore', 'Drama', NOW(), NOW()),
    ('Bob Brown', 'bob.brown@example.com', 'passwordabc', NULL, 'Hyderabad', 'Sci-Fi', NOW(), NOW()),
    ('Charlie Davis', 'charlie.davis@example.com', 'passworddef', NULL, 'Chennai', 'Thriller', NOW(), NOW());

-- Sample Data for Movies
INSERT IGNORE INTO movies (title, synopsis, cast, crew, release_date, poster_url, rating, genre, language, formats)
VALUES
    ('The Dark Knight', 'A superhero movie about Batman.', 'Christian Bale, Heath Ledger', 'Christopher Nolan', '2008-07-18', 'https://example.com/posters/dark_knight.jpg', 9.0, 'Action', 'English', '2D, IMAX'),
    ('Inception', 'A thief who steals corporate secrets through dream-sharing technology.', 'Leonardo DiCaprio, Joseph Gordon-Levitt', 'Christopher Nolan', '2010-07-16', 'https://example.com/posters/inception.jpg', 8.8, 'Sci-Fi', 'English', '2D, 3D'),
    ('Dangal', 'A former wrestler trains his daughters to become world-class wrestlers.', 'Aamir Khan, Fatima Sana Shaikh', 'Nitesh Tiwari', '2016-12-23', 'https://example.com/posters/dangal.jpg', 8.4, 'Drama', 'Hindi', '2D'),
    ('Baahubali: The Beginning', 'A young man learns about his past and seeks justice for his family.', 'Prabhas, Rana Daggubati', 'S.S. Rajamouli', '2015-07-10', 'https://example.com/posters/baahubali.jpg', 8.2, 'Action', 'Telugu', '2D, 3D'),
    ('3 Idiots', 'Two friends embark on a journey to find their lost friend.', 'Aamir Khan, R. Madhavan', 'Rajkumar Hirani', '2009-12-25', 'https://example.com/posters/3idiots.jpg', 8.4, 'Comedy', 'Hindi', '2D');

-- Sample Data for Theatres
INSERT IGNORE INTO theatres (theatre_name, city, address)
VALUES
    ('PVR Cinemas', 'Mumbai', 'Phoenix Marketcity, Kurla'),
    ('INOX', 'Delhi', 'Saket, New Delhi'),
    ('Cinepolis', 'Bangalore', 'Forum Mall, Koramangala'),
    ('PVR', 'Hyderabad', 'Inorbit Mall, Cyberabad'),
    ('AGS Cinemas', 'Chennai', 'AGS Cinema, OMR');

-- Sample Data for Shows
INSERT IGNORE INTO shows (movie_id, theatre_id, show_date, show_time, format)
VALUES
    (1, 1, '2025-10-15', '18:00:00', '2D'),
    (1, 1, '2025-10-15', '21:00:00', 'IMAX'),
    (2, 2, '2025-10-16', '15:00:00', '2D'),
    (2, 2, '2025-10-16', '19:00:00', '3D'),
    (3, 3, '2025-10-17', '12:00:00', '2D'),
    (3, 3, '2025-10-17', '16:00:00', '2D'),
    (4, 4, '2025-10-18', '14:00:00', '3D'),
    (4, 4, '2025-10-18', '20:00:00', '2D'),
    (5, 5, '2025-10-19', '11:00:00', '2D'),
    (5, 5, '2025-10-19', '17:00:00', '2D');

-- Sample Data for Seats (Show ID 1)
INSERT IGNORE INTO seats (show_id, seat_number, seat_type, price, is_available, is_handicapped)
VALUES
    (1, 'A1', 'Standard', 250.00, TRUE, FALSE),
    (1, 'A2', 'Standard', 250.00, TRUE, FALSE),
    (1, 'A3', 'Standard', 250.00, TRUE, FALSE),
    (1, 'A4', 'Standard', 250.00, TRUE, FALSE),
    (1, 'A5', 'Standard', 250.00, TRUE, FALSE),
    (1, 'B1', 'Premium', 350.00, TRUE, FALSE),
    (1, 'B2', 'Premium', 350.00, TRUE, FALSE),
    (1, 'B3', 'Premium', 350.00, TRUE, FALSE),
    (1, 'B4', 'Premium', 350.00, TRUE, FALSE),
    (1, 'B5', 'Premium', 350.00, TRUE, FALSE);

-- Sample Data for Seats (Show ID 2)
INSERT IGNORE INTO seats (show_id, seat_number, seat_type, price, is_available, is_handicapped)
VALUES
    (2, 'A1', 'Standard', 300.00, TRUE, FALSE),
    (2, 'A2', 'Standard', 300.00, TRUE, FALSE),
    (2, 'A3', 'Standard', 300.00, TRUE, FALSE),
    (2, 'A4', 'Standard', 300.00, TRUE, FALSE),
    (2, 'A5', 'Standard', 300.00, TRUE, FALSE),
    (2, 'B1', 'Premium', 400.00, TRUE, FALSE),
    (2, 'B2', 'Premium', 400.00, TRUE, FALSE),
    (2, 'B3', 'Premium', 400.00, TRUE, FALSE),
    (2, 'B4', 'Premium', 400.00, TRUE, FALSE),
    (2, 'B5', 'Premium', 400.00, TRUE, FALSE);

-- Sample Data for Bookings
INSERT IGNORE INTO bookings (user_id, show_id, booking_time, total_price, status, created_on, modified_on)
VALUES
    (1, 1, NOW(), 500.00, 'CONFIRMED', NOW(), NOW()),
    (2, 2, NOW(), 800.00, 'CONFIRMED', NOW(), NOW()),
    (3, 3, NOW(), 500.00, 'PENDING', NOW(), NOW()),
    (4, 4, NOW(), 700.00, 'CONFIRMED', NOW(), NOW()),
    (5, 5, NOW(), 500.00, 'FAILED', NOW(), NOW());

-- Sample Data for Payments
INSERT IGNORE INTO payments (booking_id, user_id, amount, payment_method, payment_status, transaction_id, created_on)
VALUES
    (1, 1, 500.00, 'CREDIT_CARD', 'SUCCESS', 'TXN123456', NOW()),
    (2, 2, 800.00, 'DEBIT_CARD', 'SUCCESS', 'TXN789012', NOW()),
    (4, 4, 700.00, 'UPI', 'SUCCESS', 'TXN345678', NOW()),
    (5, 5, 500.00, 'CREDIT_CARD', 'FAILED', 'TXN901234', NOW());

-- Sample Data for Reviews
INSERT IGNORE INTO reviews (movie_id, user_id, rating, comment, created_on)
VALUES
    (1, 1, 9.0, 'One of the best superhero movies ever!', NOW()),
    (1, 2, 8.5, 'Heath Ledger was amazing as Joker.', NOW()),
    (2, 3, 9.0, 'Mind-blowing concept and execution.', NOW()),
    (3, 4, 8.0, 'Aamir Khan delivered a powerful performance.', NOW()),
    (4, 5, 8.5, 'Visual effects were stunning!', NOW()),
    (5, 1, 9.0, 'A must-watch for everyone.', NOW());

-- Sample Data for Bookmarks
INSERT IGNORE INTO bookmarks (user_id, movie_id, created_on)
VALUES
    (1, 2, NOW()),
    (1, 3, NOW()),
    (2, 1, NOW()),
    (2, 4, NOW()),
    (3, 5, NOW()),
    (4, 1, NOW()),
    (5, 2, NOW()),
    (5, 3, NOW());

-- New data for role management
INSERT IGNORE INTO roles (role_name) VALUES
('USER'),
('THEATRE_ADMIN'),
('APPLICATION_ADMIN');

-- Assign roles to existing users
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(1, 1),  -- John Doe as USER
(2, 1),  -- Jane Smith as USER
(3, 1),  -- Alice Johnson as USER
(4, 1),  -- Bob Brown as USER
(5, 1);  -- Charlie Davis as USER

-- Make user 1 an APPLICATION_ADMIN for testing
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(1, 3);

-- Make user 2 a THEATRE_ADMIN for testing
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(2, 2);

-- Assign theatre to theatre admin
INSERT IGNORE INTO theatre_admins (user_id, theatre_id) VALUES
(2, 1);  -- User 2 (Jane Smith) is admin for theatre 1 (PVR Cinemas, Mumbai)







-- /////////////////////////////////////////Final Database Code/////////////////////////////////////////////////////////////////////////

-- Database creation (keep as is)
CREATE DATABASE movie_ticket_booking;
USE movie_ticket_booking;

-- Your original tables (keep exactly as they are)
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    city VARCHAR(50),
    preferred_genre VARCHAR(50),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    synopsis TEXT,
    cast TEXT,
    crew TEXT,
    release_date DATE,
    poster_url VARCHAR(255),
    rating DECIMAL(3,1),
    genre VARCHAR(50),
    language VARCHAR(50),
    formats VARCHAR(50)
);

CREATE TABLE theatres (
    theatre_id INT AUTO_INCREMENT PRIMARY KEY,
    theatre_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    address TEXT
);

CREATE TABLE shows (
    show_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    theatre_id INT NOT NULL,
    show_date DATE NOT NULL,
    show_time TIME NOT NULL,
    format VARCHAR(20),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (theatre_id) REFERENCES theatres(theatre_id)
);

CREATE TABLE seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    show_id INT NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    seat_type VARCHAR(20) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    is_handicapped BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    show_id INT NOT NULL,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    user_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    transaction_id VARCHAR(100),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    user_id INT NOT NULL,
    rating DECIMAL(3,1) NOT NULL,
    comment TEXT,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE bookmarks (
    bookmark_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);

-- Your original sample data (keep exactly as is)
INSERT INTO users (user_name, email, password, city, preferred_genre, created_on, modified_on)
VALUES
    ('John Doe', 'john.doe@example.com', 'password123', 'Mumbai', 'Action', NOW(), NOW()),
    ('Jane Smith', 'jane.smith@example.com', 'password456', 'Delhi', 'Comedy', NOW(), NOW()),
    ('Alice Johnson', 'alice.johnson@example.com', 'password789', 'Bangalore', 'Drama', NOW(), NOW()),
    ('Bob Brown', 'bob.brown@example.com', 'passwordabc', 'Hyderabad', 'Sci-Fi', NOW(), NOW()),
    ('Charlie Davis', 'charlie.davis@example.com', 'passworddef', 'Chennai', 'Thriller', NOW(), NOW());

-- All your other INSERT statements for movies, theatres, shows, etc. (keep exactly as is)
INSERT INTO movies (title, synopsis, cast, crew, release_date, poster_url, rating, genre, language, formats)
VALUES
    ('The Dark Knight', 'A superhero movie about Batman.', 'Christian Bale, Heath Ledger', 'Christopher Nolan', '2008-07-18', 'https://example.com/posters/dark_knight.jpg', 9.0, 'Action', 'English', '2D, IMAX'),
    ('Inception', 'A thief who steals corporate secrets through dream-sharing technology.', 'Leonardo DiCaprio, Joseph Gordon-Levitt', 'Christopher Nolan', '2010-07-16', 'https://example.com/posters/inception.jpg', 8.8, 'Sci-Fi', 'English', '2D, 3D'),
    ('Dangal', 'A former wrestler trains his daughters to become world-class wrestlers.', 'Aamir Khan, Fatima Sana Shaikh', 'Nitesh Tiwari', '2016-12-23', 'https://example.com/posters/dangal.jpg', 8.4, 'Drama', 'Hindi', '2D'),
    ('Baahubali: The Beginning', 'A young man learns about his past and seeks justice for his family.', 'Prabhas, Rana Daggubati', 'S.S. Rajamouli', '2015-07-10', 'https://example.com/posters/baahubali.jpg', 8.2, 'Action', 'Telugu', '2D, 3D'),
    ('3 Idiots', 'Two friends embark on a journey to find their lost friend.', 'Aamir Khan, R. Madhavan', 'Rajkumar Hirani', '2009-12-25', 'https://example.com/posters/3idiots.jpg', 8.4, 'Comedy', 'Hindi', '2D');

-- All your other sample data INSERT statements (keep exactly as is)

-- NEW ADDITIONS START HERE

-- Add password_hash column to users table
ALTER TABLE users CHANGE password password_hash VARCHAR(255);


-- Create new tables for role management
CREATE TABLE roles (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles (
    user_role_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    UNIQUE (user_id, role_id)
);

CREATE TABLE theatre_admins (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    theatre_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (theatre_id) REFERENCES theatres(theatre_id) ON DELETE CASCADE
);

-- Insert roles
INSERT INTO roles (role_name) VALUES
('USER'),
('THEATRE_ADMIN'),
('APPLICATION_ADMIN');

-- Assign roles to existing users
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),  -- John Doe as USER
(2, 1),  -- Jane Smith as USER
(3, 1),  -- Alice Johnson as USER
(4, 1),  -- Bob Brown as USER
(5, 1);  -- Charlie Davis as USER

-- Make user 1 an APPLICATION_ADMIN for testing
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 3);

-- Make user 2 a THEATRE_ADMIN for testing
INSERT INTO user_roles (user_id, role_id) VALUES
(2, 2);

-- Assign theatre to theatre admin
INSERT INTO theatre_admins (user_id, theatre_id) VALUES
(2, 1);  -- User 2 (Jane Smith) is admin for theatre 1 (PVR Cinemas, Mumbai)
