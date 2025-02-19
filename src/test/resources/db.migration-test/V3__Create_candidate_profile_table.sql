CREATE TABLE candidate_profile (
	id INTEGER AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),
    profile_photo VARCHAR(255),
    resume VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES users(id)
);