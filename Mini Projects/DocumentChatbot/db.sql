CREATE DATABASE chatbot_db;

USE chatbot_db;

CREATE TABLE chat_messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    message TEXT,
    response TEXT, 
    model VARCHAR(50)
);

select * form chat_messages;

CREATE TABLE documents (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    content LONGTEXT,
    type VARCHAR(100)
);

select * from documents;
