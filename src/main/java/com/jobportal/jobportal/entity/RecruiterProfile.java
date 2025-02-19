package com.jobportal.jobportal.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "recruiter_profile")
public class RecruiterProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String company;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String profilePhoto;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private Users user;

    public RecruiterProfile() {
    }

    public RecruiterProfile(Users user) {
        this.user = user;
    }

    public RecruiterProfile(Integer id, String company, String firstName, String lastName, String city, String country, String profilePhoto, Users user) {
        this.id = id;
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
        this.profilePhoto = profilePhoto;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RecruiterProfile{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", user=" + user +
                '}';
    }

    public String getProfilePhotoPath() {
        if (id == null || profilePhoto == null) return null;
        return "/recruiter/"+id+"/"+profilePhoto;
    }
}
