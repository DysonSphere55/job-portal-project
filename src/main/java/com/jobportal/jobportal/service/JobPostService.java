package com.jobportal.jobportal.service;

import com.jobportal.jobportal.dto.CandidateJobPostDTO;
import com.jobportal.jobportal.dto.IRecruiterJobPost;
import com.jobportal.jobportal.dto.RecruiterJobPostDTO;
import com.jobportal.jobportal.entity.JobCompany;
import com.jobportal.jobportal.entity.JobLocation;
import com.jobportal.jobportal.entity.JobPost;
import com.jobportal.jobportal.repository.JobPostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    public JobPostService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public JobPost save(JobPost jobPost) {
        return jobPostRepository.save(jobPost);
    }

    public Optional<JobPost> findById(int id) {
        return jobPostRepository.findById(id);
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

    public List<JobPost> getAll() {
        System.out.println("getAll()");
        return jobPostRepository.findAll();
    }

    public List<JobPost> getWithFilters(
            String job, String location, List<String> type, List<String> remote, LocalDate searchDate) {

        List<CandidateJobPostDTO> candidateJobPostDTOList = null;

        if (searchDate == null) {
            candidateJobPostDTOList = jobPostRepository.findWithFiltersWithoutDate(job, location, type, remote);
        } else {
            candidateJobPostDTOList = jobPostRepository.findWithFilters(job, location, type, remote, searchDate);
        }

        List<JobPost> result = new ArrayList<>();

        for (CandidateJobPostDTO jobPostDTO : candidateJobPostDTOList) {

            JobPost jobPost = new JobPost();

            jobPost.setId(jobPostDTO.getJobId());
            jobPost.setTitle(jobPostDTO.getTitle());
            jobPost.setType(jobPostDTO.getType());
            jobPost.setRemote(jobPostDTO.getRemote());
            jobPost.setDescription(jobPostDTO.getDescription());
            jobPost.setSalary(jobPostDTO.getSalary());
            jobPost.setPostedDate(jobPostDTO.getPostedDate());
            jobPost.setJobCompany(
                    new JobCompany(
                            jobPostDTO.getCompanyId(),
                            jobPostDTO.getName(),
                            jobPostDTO.getBrand()
                    )
            );
            jobPost.setJobLocation(
                    new JobLocation(
                            jobPostDTO.getLocationId(),
                            jobPostDTO.getCity(),
                            jobPostDTO.getCountry()
                    )
            );

            result.add(jobPost);
        }

        result.forEach(System.out::println);

        return result;
    }

    public void deleteById(int jobPostId) {
        jobPostRepository.deleteById(jobPostId);
    }

}
