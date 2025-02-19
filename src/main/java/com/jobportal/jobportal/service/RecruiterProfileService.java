package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.RecruiterProfile;
import com.jobportal.jobportal.repository.RecruiterProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;

    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public RecruiterProfile save(RecruiterProfile recruiterProfile) {
        return recruiterProfileRepository.save(recruiterProfile);
    }

    public Optional<RecruiterProfile> findById(int id) {
        return recruiterProfileRepository.findById(id);
    }
}
