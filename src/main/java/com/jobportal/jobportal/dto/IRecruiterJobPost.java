package com.jobportal.jobportal.dto;


public interface IRecruiterJobPost {

    Integer getId();
    String getTitle();
    Integer getCandidatesApplied();
    Integer getJobLocationId();
    String getCity();
    String getCountry();
    Integer getJobCompanyId();
    String getName();
}
