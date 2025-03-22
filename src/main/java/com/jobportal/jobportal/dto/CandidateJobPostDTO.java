package com.jobportal.jobportal.dto;


import java.sql.Timestamp;
import java.sql.Time;
import java.time.LocalDateTime;

public class CandidateJobPostDTO {

    private Integer jobId;
    private String title;
    private String type;
    private String remote;
    private String description;
    private String salary;
    private Timestamp postedDate;
    private Integer companyId;
    private String name;
    private String brand;
    private Integer locationId;
    private String city;
    private String country;

    public CandidateJobPostDTO() {
    }

    public CandidateJobPostDTO(Integer jobId, String title, String type, String remote, String description,
                               String salary, Timestamp postedDate, Integer companyId, String name, String brand,
                               Integer locationId, String city, String country) {
        this.jobId = jobId;
        this.title = title;
        this.type = type;
        this.remote = remote;
        this.description = description;
        this.salary = salary;
        this.postedDate = postedDate;
        this.companyId = companyId;
        this.name = name;
        this.brand = brand;
        this.locationId = locationId;
        this.city = city;
        this.country = country;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setPostedDate(Timestamp postedDate) {
        this.postedDate = postedDate;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getJobId() {
        return jobId;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getRemote() {
        return remote;
    }

    public String getDescription() {
        return description;
    }

    public String getSalary() {
        return salary;
    }

    public LocalDateTime getPostedDate() {
        return postedDate.toLocalDateTime();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}


//public class Builder {
//    private Integer jobId;
//    private String title;
//    private String type;
//    private String description;
//    private String salary;
//    private LocalDateTime postedDate;
//    private Integer companyId;
//    private String name;
//    private String brand;
//    private Integer locationId;
//    private String city;
//    private String country;
//
//    public Builder withJobId(Integer jobId) {
//        this.jobId = jobId;
//        return this;
//    }
//
//    public Builder withTitle(String title) {
//        this.title = title;
//        return this;
//    }
//
//    public Builder withType(String type) {
//        this.type = type;
//        return this;
//    }
//
//    public Builder withDescription(String description) {
//        this.description = description;
//        return this;
//    }
//
//    public Builder withSalary(String salary) {
//        this.salary = salary;
//        return this;
//    }
//
//    public Builder withPostedDate(LocalDateTime postedDate) {
//        this.postedDate = postedDate;
//        return this;
//    }
//
//    public Builder withCompanyId(Integer companyId) {
//        this.companyId = companyId;
//        return this;
//    }
//
//    public Builder withName(String name) {
//        this.name = name;
//        return this;
//    }
//
//    public Builder withBrand(String brand) {
//        this.brand = brand;
//        return this;
//    }
//
//    public Builder withLocationId(Integer locationId) {
//        this.locationId = locationId;
//        return this;
//    }
//
//    public Builder withCity(String city) {
//        this.city = city;
//        return this;
//    }
//
//    public Builder withCountry(String country) {
//        this.country = country;
//        return this;
//    }
//}
