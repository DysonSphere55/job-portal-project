package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.CandidateProfile;
import com.jobportal.jobportal.repository.CandidateProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;

    public CandidateProfileService(CandidateProfileRepository candidateProfileRepository) {
        this.candidateProfileRepository = candidateProfileRepository;
    }

    public CandidateProfile save(CandidateProfile candidateProfile) {
        return candidateProfileRepository.save(candidateProfile);
    }

    public Optional<CandidateProfile> findById(int id) {
        return candidateProfileRepository.findById(id);
    }
}
