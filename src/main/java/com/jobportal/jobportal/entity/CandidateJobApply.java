package com.jobportal.jobportal.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_job_apply")
public class CandidateJobApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime applyDate;
    @ManyToOne
    @JoinColumn(name = "candidate_profile_id", referencedColumnName = "id")
    private CandidateProfile candidateProfile;
    @ManyToOne
    @JoinColumn(name = "job_post_id", referencedColumnName = "id")
    private JobPost jobPost;

    public CandidateJobApply() {
    }

    public CandidateJobApply(Integer id, LocalDateTime applyDate, CandidateProfile candidateProfile, JobPost jobPost) {
        this.id = id;
        this.applyDate = applyDate;
        this.candidateProfile = candidateProfile;
        this.jobPost = jobPost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDateTime applyDate) {
        this.applyDate = applyDate;
    }

    public CandidateProfile getCandidateProfile() {
        return candidateProfile;
    }

    public void setCandidateProfile(CandidateProfile candidateProfile) {
        this.candidateProfile = candidateProfile;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }
}
