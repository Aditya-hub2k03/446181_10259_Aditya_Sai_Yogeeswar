-- Users
INSERT INTO users(user_name, email, password, role) VALUES
('Alice', 'alice@example.com', '$2a$12$hashedpassword1', 'USER'),
('Bob', 'bob@example.com', '$2a$12$hashedpassword2', 'USER');

-- Movies
INSERT INTO movies(title, language, genre, synopsis, release_date, poster_url) VALUES
('Avengers: Endgame', 'English', 'Action', 'Superhero film...', '2019-04-26', 'https://image.tmdb.org/t/p/w500/avengers_endgame.jpg'),
('The Lion King', 'English', 'Animation', 'Disney animated...', '2019-07-19', 'https://image.tmdb.org/t/p/w500/lion_king.jpg'),
('Parasite', 'Korean', 'Thriller', 'South Korean dark comedy...', '2019-05-30', 'https://image.tmdb.org/t/p/w500/parasite.jpg'),
('Spider-Man: No Way Home', 'English', 'Action', 'Spider-Man faces multiverse...', '2021-12-17', 'https://image.tmdb.org/t/p/w500/spiderman_nwh.jpg'),
('Joker', 'English', 'Drama', 'Origin story of Joker...', '2019-10-04', 'https://image.tmdb.org/t/p/w500/joker.jpg');

-- Theatres
INSERT INTO theatres(theatre_name, city, address) VALUES
('Cineplex Downtown', 'New York', '123 Main St'),
('Regal Cinema', 'Los Angeles', '456 Sunset Blvd'),
('IMAX Theater', 'San Francisco', '789 Market St');

-- Shows
INSERT INTO shows(movie_id, theatre_id, show_time, show_date, price) VALUES
(1, 1, '10:00', '2025-10-10', 250),
(1, 2, '14:00', '2025-10-10', 300),
(2, 1, '12:00', '2025-10-11', 200),
(3, 3, '18:00', '2025-10-12', 350),
(4, 2, '20:00', '2025-10-13', 400),
(5, 1, '16:00', '2025-10-14', 250);

-- Seats
INSERT INTO seats(show_id, seat_no, type, status) VALUES
(1, 'A1', 'REGULAR', 'AVAILABLE'),
(1, 'A2', 'REGULAR', 'SOLD'),
(1, 'A3', 'HANDICAPPED', 'AVAILABLE'),
(1, 'A4', 'REGULAR', 'BEST_SELLING'),
(2, 'B1', 'REGULAR', 'AVAILABLE'),
(2, 'B2', 'REGULAR', 'AVAILABLE'),
(3, 'C1', 'REGULAR', 'AVAILABLE'),
(3, 'C2', 'HANDICAPPED', 'AVAILABLE');

-- Bookings
INSERT INTO bookings(user_id, show_id, seat_count, selected_seats, total_amount, status) VALUES
(1, 1, 2, 'A1,A2', 500, 'CONFIRMED'),
(2, 2, 1, 'B1', 200, 'CONFIRMED');
