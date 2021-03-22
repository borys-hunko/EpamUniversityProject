package com.epam.EpamUniversityProject.model;

import java.io.Serializable;
import java.util.List;

public class Applicant extends User implements Serializable {
    private String firstName;
    private String lastName;
    private String fathersName;
    private String region;
    private String city;
    private String school;
    private List<Grade> grades;

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

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}
