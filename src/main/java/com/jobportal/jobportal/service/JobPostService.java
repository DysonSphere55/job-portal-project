package com.jobportal.jobportal.service;

import com.jobportal.jobportal.dto.IRecruiterJobPost;
import com.jobportal.jobportal.dto.RecruiterJobPostDTO;
import com.jobportal.jobportal.entity.JobCompany;
import com.jobportal.jobportal.entity.JobLocation;
import com.jobportal.jobportal.entity.JobPost;
import com.jobportal.jobportal.repository.JobPostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    public JobPostService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public JobPost save(JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }

    public List<RecruiterJobPostDTO> getRecruiterJobPosts(int recruiterId) {

        List<IRecruiterJobPost> recruiterJobPosts = jobPostRepository.getRecruiterJobPosts(recruiterId);

        List<RecruiterJobPostDTO> recruiterJobPostDTOList = new ArrayList<>();

        for (IRecruiterJobPost iRecruiterJobPost : recruiterJobPosts) {

            RecruiterJobPostDTO recruiterJobPostDTO = new RecruiterJobPostDTO.Builder()
                    .id(iRecruiterJobPost.getId())
                    .title(iRecruiterJobPost.getTitle())
                    .setCandidatesApplied(iRecruiterJobPost.getCandidatesApplied())
                    .setJobLocation(new JobLocation(
                            iRecruiterJobPost.getJobLocationId(),
                            iRecruiterJobPost.getCity(),
                            iRecruiterJobPost.getCountry()))
                    .setJobCompany(new JobCompany(
                            iRecruiterJobPost.getJobCompanyId(),
                            iRecruiterJobPost.getName(),
                            null
                    ))
                    .build();

            recruiterJobPostDTOList.add(recruiterJobPostDTO);
        }

        return recruiterJobPostDTOList;
    }
}
