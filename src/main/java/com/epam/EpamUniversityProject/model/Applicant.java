package com.epam.EpamUniversityProject.model;

import java.io.Serializable;

public class Applicant extends User implements Serializable {
    private String firstName;
    private String lastName;
    private String fathersName;
    private String region;
    private String city;
    private String school;

    public String getFirstName() {
        return firstName;
    }

    public Applicant setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Applicant setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFathersName() {
        return fathersName;
    }

    public Applicant setFathersName(String fathersName) {
        this.fathersName = fathersName;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public Applicant setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Applicant setCity(String city) {
        this.city = city;
        return this;
    }

    public String getSchool() {
        return school;
    }

    public Applicant setSchool(String school) {
        this.school = school;
        return this;
    }
}
