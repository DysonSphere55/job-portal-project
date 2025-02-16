CREATE TABLE candidate_skills (
	id INTEGER AUTO_INCREMENT,
    name VARCHAR(255),
    experience_level VARCHAR(255),
    years_of_experience VARCHAR(255),
    candidate_profile_id INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (candidate_profile_id) REFERENCES candidate_profile(id)
);