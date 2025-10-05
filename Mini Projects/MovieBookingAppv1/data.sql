USE movie_booking_db;

INSERT INTO users (name, email, password, roles, email_verified) VALUES
  ('John Doe', 'john@example.com', 'encryptedpassword1', 'USER', TRUE),
  ('Admin User', 'admin@example.com', 'encryptedpassword2', 'ADMIN', TRUE);

INSERT INTO movies (title, poster_url, genre, language, release_date, runtime) VALUES
  ('Avengers: Endgame', 'https://example.com/posters/avengers.jpg', 'Action', 'English', '2019-04-26', 181),
  ('Inception', 'https://example.com/posters/inception.jpg', 'Sci-Fi', 'English', '2010-07-16', 148);

INSERT INTO theaters (name, address, capacity) VALUES
  ('City Cinema', '123 Main St, Metro City', 200),
  ('Grand Theater', '456 Broadway Ave, Big City', 350);
