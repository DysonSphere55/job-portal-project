CREATE TABLE recruiter_profile (
	id INTEGER AUTO_INCREMENT,
    company VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),
    profile_photo VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES users(id)
);