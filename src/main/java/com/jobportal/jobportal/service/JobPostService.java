package com.jobportal.jobportal.service;

import com.jobportal.jobportal.repository.JobPostRepository;
import org.springframework.stereotype.Service;

@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    public JobPostService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }
}
