package com.epam.EpamUniversityProject.model;

import java.io.Serializable;
import java.util.List;


public class User implements Serializable {
    private long id;
    private String email;
    private String password;
    private Role role;
    private boolean isBlocked;
    private String firstName;
    private String lastName;
    private String fathersName;
    private String region;
    private String city;
    private String school;

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public User setBlocked(boolean blocked) {
        isBlocked = blocked;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFathersName() {
        return fathersName;
    }

    public User setFathersName(String fathersName) {
        this.fathersName = fathersName;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public User setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getCity() {
        return city;
    }

    public User setCity(String city) {
        this.city = city;
        return this;
    }

    public String getSchool() {
        return school;
    }

    public User setSchool(String school) {
        this.school = school;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", isBlocked=" + isBlocked +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fathersName='" + fathersName + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
