CREATE SCHEMA IF NOT EXISTS meeting_calendar_db;
USE meeting_calendar_db;

CREATE TABLE users(
	username VARCHAR(255) NOT NULL PRIMARY KEY,
	_password VARCHAR(255) NOT NULL,
    expired TINYINT DEFAULT FALSE,
    create_date DATETIME DEFAULT now()
);

CREATE TABLE meeting_calendars(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    create_date DATETIME DEFAULT now(),
	FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE meetings (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    _description TEXT,
	calendar_id INT NOT NULL,
    create_date DATETIME DEFAULT now(),
    FOREIGN KEY (calendar_id) REFERENCES meeting_calendars(id)
);