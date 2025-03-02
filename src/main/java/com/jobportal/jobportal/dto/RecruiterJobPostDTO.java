package com.jobportal.jobportal.dto;

import com.jobportal.jobportal.entity.JobCompany;
import com.jobportal.jobportal.entity.JobLocation;

public class RecruiterJobPostDTO {

    private final Integer id;
    private final String title;
    private final Integer candidatesApplied;
    private final JobLocation jobLocation;
    private final JobCompany jobCompany;

    private RecruiterJobPostDTO() {
        this.id = null;
        this.title = null;
        this.candidatesApplied = null;
        this.jobLocation = null;
        this.jobCompany = null;
    }

    private RecruiterJobPostDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.candidatesApplied = builder.candidatesApplied;
        this.jobLocation = builder.jobLocation;
        this.jobCompany = builder.jobCompany;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCandidatesApplied() {
        return candidatesApplied;
    }

    public JobLocation getJobLocation() {
        return jobLocation;
    }

    public JobCompany getJobCompany() {
        return jobCompany;
    }

    public static class Builder {

        private Integer id;
        private String title;
        private Integer candidatesApplied;
        private JobLocation jobLocation;
        private JobCompany jobCompany;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder setCandidatesApplied(Integer candidatesApplied) {
            this.candidatesApplied = candidatesApplied;
            return this;
        }

        public Builder setJobLocation(JobLocation jobLocation) {
            this.jobLocation = jobLocation;
            return this;
        }

        public Builder setJobCompany(JobCompany jobCompany) {
            this.jobCompany = jobCompany;
            return this;
        }

        public RecruiterJobPostDTO build() {
            return new RecruiterJobPostDTO(this);
        }
    }
}
