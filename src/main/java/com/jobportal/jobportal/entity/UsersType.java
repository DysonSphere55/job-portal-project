package com.jobportal.jobportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users_type")
public class UsersType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;

    public UsersType() {
    }

    public UsersType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UsersType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
