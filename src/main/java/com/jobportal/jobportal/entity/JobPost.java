package com.jobportal.jobportal.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String type;
    private String remote;
    private String description;
    private String salary;
    private LocalDateTime postedDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "job_company_id", referencedColumnName = "id")
    private JobCompany jobCompany;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "job_location_id", referencedColumnName = "id")
    private JobLocation jobLocation;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "recruiter_profile_id", referencedColumnName = "id")
    private RecruiterProfile recruiterProfile;

    public JobPost() {
    }

    public JobPost(Integer id, String title, String type, String remote, String description, String salary, LocalDateTime postedDate, JobCompany jobCompany, JobLocation jobLocation, RecruiterProfile recruiterProfile) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.remote = remote;
        this.description = description;
        this.salary = salary;
        this.postedDate = postedDate;
        this.jobCompany = jobCompany;
        this.jobLocation = jobLocation;
        this.recruiterProfile = recruiterProfile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }

    public JobCompany getJobCompany() {
        return jobCompany;
    }

    public void setJobCompany(JobCompany jobCompany) {
        this.jobCompany = jobCompany;
    }

    public JobLocation getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(JobLocation jobLocation) {
        this.jobLocation = jobLocation;
    }

    public RecruiterProfile getRecruiterProfile() {
        return recruiterProfile;
    }

    public void setRecruiterProfile(RecruiterProfile recruiterProfile) {
        this.recruiterProfile = recruiterProfile;
    }

    @Override
    public String toString() {
        return "JobPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", remote='" + remote + '\'' +
                ", description='" + description + '\'' +
                ", salary='" + salary + '\'' +
                ", postedDate=" + postedDate +
                ", jobCompany=" + jobCompany +
                ", jobLocation=" + jobLocation +
                ", recruiterProfile=" + recruiterProfile +
                '}';
    }
}
