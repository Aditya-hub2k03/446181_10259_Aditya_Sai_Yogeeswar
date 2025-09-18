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
