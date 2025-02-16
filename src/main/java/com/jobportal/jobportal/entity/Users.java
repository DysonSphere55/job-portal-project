package com.jobportal.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Entity
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private Boolean isActive;
    private Date registeredDate;
    @ManyToOne()
    @JoinColumn(name = "users_type_id")
    private UsersType usersType;

    public Users() {
    }

    public Users(Integer id, String email, String password, Boolean isActive, Date registeredDate, UsersType usersType) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.registeredDate = registeredDate;
        this.usersType = usersType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public UsersType getUsersType() {
        return usersType;
    }

    public void setUsersType(UsersType usersType) {
        this.usersType = usersType;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", registeredDate=" + registeredDate +
                ", usersType=" + usersType +
                '}';
    }
}
