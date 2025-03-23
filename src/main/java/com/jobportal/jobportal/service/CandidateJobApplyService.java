package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.CandidateJobApply;
import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.entity.JobPost;
import com.jobportal.jobportal.repository.CandidateJobApplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateJobApplyService {

    private final CandidateJobApplyRepository candidateJobApplyRepository;

    public CandidateJobApplyService(CandidateJobApplyRepository candidateJobApplyRepository) {
        this.candidateJobApplyRepository = candidateJobApplyRepository;
    }

    public List<CandidateJobApply> findByJobPost(JobPost jobPost) {
        return candidateJobApplyRepository.findByJobPost(jobPost);
    }

    public List<CandidateJobApply> findByCandidateProfile(CandidateProfile candidateProfile) {
        return candidateJobApplyRepository.findByCandidateProfile(candidateProfile);
    }
}
