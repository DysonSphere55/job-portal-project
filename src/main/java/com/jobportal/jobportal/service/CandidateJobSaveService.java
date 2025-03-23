package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.CandidateJobApply;
import com.jobportal.jobportal.entity.CandidateJobSave;
import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.JobPost;
import com.jobportal.jobportal.repository.CandidateJobApplyRepository;
import com.jobportal.jobportal.repository.CandidateJobSaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateJobSaveService {

    private final CandidateJobSaveRepository candidateJobSaveRepository;

    public CandidateJobSaveService(CandidateJobSaveRepository candidateJobSaveRepository) {
        this.candidateJobSaveRepository = candidateJobSaveRepository;
    }

    List<CandidateJobSave> findByJobPost(JobPost jobPost) {
        return candidateJobSaveRepository.findByJobPost(jobPost);
    }

    List<CandidateJobSave> findByCandidateProfile(CandidateProfile candidateProfile) {
        return candidateJobSaveRepository.findByCandidateProfile(candidateProfile);
    }
}
