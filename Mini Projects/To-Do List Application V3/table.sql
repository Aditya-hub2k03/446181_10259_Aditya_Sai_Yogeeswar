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
