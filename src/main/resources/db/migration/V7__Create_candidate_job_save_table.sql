CREATE TABLE candidate_job_save (
	id INTEGER AUTO_INCREMENT,
    save_date TIMESTAMP DEFAULT NOW(),
    candidate_profile_id INTEGER,
    job_post_id INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (job_post_id)
    REFERENCES job_post(id) ON DELETE CASCADE,
    FOREIGN KEY (candidate_profile_id)
    REFERENCES candidate_profile(id) ON DELETE CASCADE
);