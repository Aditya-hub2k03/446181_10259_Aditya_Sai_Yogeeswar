CREATE DATABASE bookmyshow;

USE bookmyshow;

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    created_on DATETIME NOT NULL,
    modified_by VARCHAR(100),
    modified_on DATETIME
);

select * from users; 

CREATE TABLE IF NOT EXISTS movies (
    movie_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    genre VARCHAR(100),
    duration INT,
    release_date VARCHAR(50)
);

select * from movies;

CREATE TABLE IF NOT EXISTS movie_ratings (
    rating_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    rating INT NOT NULL,
    created_on DATETIME NOT NULL,
    modified_on DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    UNIQUE KEY unique_user_movie (user_id, movie_id)
);

select * from movie_ratings;

-- Insert sample movies
INSERT INTO movies (title, description, genre, duration, release_date) VALUES
('Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology.', 'Sci-Fi', 148, '2010-07-16'),
('The Shawshank Redemption', 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 'Drama', 142, '1994-09-23'),
('The Dark Knight', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 'Action', 152, '2008-07-18');

