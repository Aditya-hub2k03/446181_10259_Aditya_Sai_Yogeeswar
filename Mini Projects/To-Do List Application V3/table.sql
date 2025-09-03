-- Create schema
CREATE SCHEMA IF NOT EXISTS tododb;
USE tododb;

-- Lookup table 
CREATE TABLE IF NOT EXISTS todo_status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    todo_status_code VARCHAR(1) NOT NULL UNIQUE,   -- P/I/C
    todo_status_name VARCHAR(50) NOT NULL,
    todo_status_desc VARCHAR(255),
    created_by VARCHAR(100),
    created_on DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by VARCHAR(100),
    modified_on DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert default statuses
INSERT INTO todo_status (todo_status_code, todo_status_name, todo_status_desc, created_by)
VALUES 
('P', 'Pending', 'Task is pending', 'system'),
('I', 'InProgress', 'Task is in progress', 'system'),
('C', 'Completed', 'Task is completed', 'system')
ON DUPLICATE KEY UPDATE todo_status_code = VALUES(todo_status_code);

-- Main todo_items table (simplified: stores status code directly)
CREATE TABLE IF NOT EXISTS todo_items (
    todo_id INT AUTO_INCREMENT PRIMARY KEY,
    todo_title VARCHAR(255) NOT NULL,
    todo_desc TEXT,
    target_datetime DATETIME NOT NULL,
    todo_status_code VARCHAR(1) NOT NULL DEFAULT 'P',  

    -- Audit fields
    created_by VARCHAR(100),
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by VARCHAR(100),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

select * from todo_items;
select * from todo_status;
delete from todo_items;

--  User table

-- Add user table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,  -- Store hashed passwords only
    email VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
select * from users;
-- Add a sample user (password should be hashed in a real application)
INSERT INTO users (username, password, email) VALUES
('yogesh', 'yogesh123', 'yogesh@gmail.com')
ON DUPLICATE KEY UPDATE username = username;

-- Update todo_items table to reference users
ALTER TABLE todo_items
MODIFY created_by VARCHAR(50) NOT NULL,
MODIFY modified_by VARCHAR(50);


-- First, check if the user_id column exists in todo_items
SELECT COLUMN_NAME
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'tododb'
AND TABLE_NAME = 'todo_items'
AND COLUMN_NAME = 'user_id';

-- If it doesn't exist, add the column
ALTER TABLE todo_items ADD COLUMN user_id INT;

-- Add foreign key constraint
ALTER TABLE todo_items
ADD CONSTRAINT fk_user
FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;

-- Update existing records to have a user_id (if needed)
-- For example, assign all existing todos to user with ID 1
UPDATE todo_items SET user_id = 1 WHERE user_id IS NULL;

-- Make user_id NOT NULL
ALTER TABLE todo_items MODIFY user_id INT NOT NULL;
