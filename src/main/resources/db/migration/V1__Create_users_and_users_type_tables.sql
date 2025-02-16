CREATE TABLE users_type (
	id INTEGER AUTO_INCREMENT,
    type VARCHAR(255) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users (
	id INTEGER AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_active BIT(1) DEFAULT 1,
    registered_date TIMESTAMP DEFAULT NOW(),
    users_type_id INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (users_type_id) REFERENCES users_type(id)
);

INSERT INTO users_type (type) VALUES
	('RECRUITER'),
    ('CANDIDATE')
;