package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.entity.CandidateJobApply;
import com.jobportal.jobportal.entity.CandidateJobSave;
import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateJobSaveRepository extends JpaRepository<CandidateJobSave, Integer> {

    List<CandidateJobSave> findByJobPost(JobPost jobPost);

    List<CandidateJobSave> findByCandidateProfile(CandidateProfile candidateProfile);
}
