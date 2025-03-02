package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.JobPost;
import com.jobportal.jobportal.repository.JobPostRepository;
import org.springframework.stereotype.Service;

@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    public JobPostService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public JobPost save(JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }
}
