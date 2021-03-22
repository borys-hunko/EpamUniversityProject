package com.epam.EpamUniversityProject.model;

import java.util.List;

public class Application {
    private long id;
    private Applicant applicant;
    private Faculty faculty;
    private List<Grade> grades;
    private int priority;
    private ApplicationStatus status;

    public long getId() {
        return id;
    }

    public Application setId(long id) {
        this.id = id;
        return this;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Application setApplicant(Applicant applicant) {
        this.applicant = applicant;
        return this;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Application setFaculty(Faculty faculty) {
        this.faculty = faculty;
        return this;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public Application setGrades(List<Grade> grades) {
        this.grades = grades;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public Application setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public Application setStatus(ApplicationStatus status) {
        this.status = status;
        return this;
    }
}
