package com.jobportal.jobportal.service;

import com.jobportal.jobportal.repository.RecruiterRepository;
import org.springframework.stereotype.Service;

@Service
public class RecruiterService {

    private final RecruiterRepository recruiterRepository;

    public RecruiterService(RecruiterRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
    }

}
