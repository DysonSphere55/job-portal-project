package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Integer> {


    /*
    CREATE TABLE job_company (
	id INTEGER AUTO_INCREMENT,
    name VARCHAR(255),
    brand VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE job_location (
	id INTEGER AUTO_INCREMENT,
    city VARCHAR(255),
    country VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE job_post (
	id INTEGER AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    type VARCHAR(255),
    remote VARCHAR(255),
    description VARCHAR(10000),
    salary VARCHAR(255),
    posted_date TIMESTAMP DEFAULT NOW(),
    job_company_id INTEGER,
    job_location_id INTEGER,
    recruiter_profile_id INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (job_company_id)
    REFERENCES job_company(id) ON DELETE CASCADE,
    FOREIGN KEY (job_location_id)
    REFERENCES job_location(id) ON DELETE CASCADE,
    FOREIGN KEY (recruiter_profile_id)
    REFERENCES recruiter_profile(id) ON DELETE CASCADE
);
     */

//    @Query(value = """SELECT
//            job_post.id AS id,
//            job_post.title AS title,
//            COUNT(
//            """, nativeQuery = true)
//    List<IRecruiterJobPost> getRecruiterJobPosts(int recruiterId);

}
