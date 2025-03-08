package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.dto.IRecruiterJobPost;
import com.jobportal.jobportal.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Integer> {


    @Query(value = """
            SELECT
                job_post.id AS id,
                job_post.title AS title,
                COUNT(candidate_job_apply.id) AS candidatesApplied,
                job_location.id AS jobLocationId,
                job_location.city AS city,
                job_location.country AS country,
                job_company.id AS jobCompanyId,
                job_company.name AS name
            FROM job_post
                JOIN job_company ON job_company.id = job_post.job_company_id
                JOIN job_location ON job_location.id = job_post.job_location_id
                LEFT JOIN candidate_job_apply ON candidate_job_apply.job_post_id = job_post.id
            WHERE job_post.recruiter_profile_id = :recruiterId
            GROUP BY job_post.id;
            """, nativeQuery = true)
    List<IRecruiterJobPost> getRecruiterJobPosts(@Param("recruiterId") int recruiterId);


    @Query(value = """
            SELECT
            	j.id,
            	j.title,
            	j.type,
            	j.remote,
            	j.description,
            	j.salary,
            	j.posted_date,
            	j.job_company_id,
            	j.job_location_id,
            	j.recruiter_profile_id,
            	c.id AS job_company_id,
            	c.name,
            	c.brand,
            	l.id AS job_location_id,
            	l.city,
            	l.country
            FROM job_post j
            JOIN job_company c ON c.id = j.job_company_id
            JOIN job_location l ON l.id = j.job_location_id
            WHERE
            	(j.title LIKE CONCAT('%', :job, '%'))
                AND
                (l.city LIKE CONCAT('%', :location, '%') OR l.country LIKE CONCAT('%', :location, '%') )
            	AND
            	(j.type IN (:type))
            	AND
            	(j.remote IN (:remote))
            ;
            """, nativeQuery = true)
    List<JobPost> findWithFiltersWithoutDate(
            @Param("job") String job,
            @Param("location") String location,
            @Param("type") List<String> type,
            @Param("remote") List<String> remote);


    @Query(value = """
            SELECT
            	j.id,
            	j.title,
            	j.type,
            	j.remote,
            	j.description,
            	j.salary,
            	j.posted_date,
            	j.job_company_id,
            	j.job_location_id,
            	j.recruiter_profile_id,
            	c.id,
            	c.name,
            	c.brand,
            	l.id,
            	l.city,
            	l.country
            FROM job_post j
            JOIN job_company c ON c.id = j.job_company_id
            JOIN job_location l ON l.id = j.job_location_id
            WHERE
            	(j.title LIKE CONCAT('%', :job, '%'))
                AND
                (l.city LIKE CONCAT('%', :location, '%') OR l.country LIKE CONCAT('%', :location, '%') )
            	AND
            	(j.type IN (:type))
            	AND
            	(j.remote IN (:remote))
            	AND
            	(j.posted_date > :searchDate)
            ;
            """, nativeQuery = true)
    List<JobPost> findWithFilters(
            @Param("job") String job,
            @Param("location") String location,
            @Param("type") List<String> type,
            @Param("remote") List<String> remote,
            @Param("searchDate") LocalDateTime searchDate);
}
