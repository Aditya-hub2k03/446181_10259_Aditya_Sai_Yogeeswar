CREATE DATABASE userdb;
USE userdb;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    gender VARCHAR(10),
    dob DATE,
    hobbies VARCHAR(255)
);
