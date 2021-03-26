package com.epam.EpamUniversityProject.model;

import java.util.List;

public class Application {
    private long id;
    private User applicant;
    private Faculty faculty;
    private int priority;
    private ApplicationStatus status;
    private List<Grade> grades;

    public long getId() {
        return id;
    }

    public Application setId(long id) {
        this.id = id;
        return this;
    }

    public User getApplicant() {
        return applicant;
    }

    public Application setApplicant(User applicant) {
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

    public List<Grade> getGrades() {
        return grades;
    }

    public Application setGrades(List<Grade> grades) {
        this.grades = grades;
        return this;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", applicant=" + applicant +
                ", faculty=" + faculty +
                ", priority=" + priority +
                ", status=" + status +
                ", grades=" + grades +
                '}';
    }
}
