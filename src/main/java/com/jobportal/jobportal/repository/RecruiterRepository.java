package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruiterRepository extends JpaRepository<RecruiterProfile, Integer> {

}
