package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.entity.CandidateJobApply;
import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateJobApplyRepository extends JpaRepository<CandidateJobApply, Integer> {

    List<CandidateJobApply> findByJobPost(JobPost jobPost);

    List<CandidateJobApply> findByCandidateProfile(CandidateProfile candidateProfile);
}
