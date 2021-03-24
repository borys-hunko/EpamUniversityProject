package com.epam.EpamUniversityProject.model;

import java.util.List;

public class Faculty {
    private long id;
    private String name;
    private int budgedPlaces;
    private int totalPaces;
    private List<Subject> requiredSubjects;

    public long getId() {
        return id;
    }

    public Faculty setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Faculty setName(String name) {
        this.name = name;
        return this;
    }

    public int getBudgedPlaces() {
        return budgedPlaces;
    }

    public Faculty setBudgedPlaces(int budgedPlaces) {
        this.budgedPlaces = budgedPlaces;
        return this;
    }

    public int getTotalPaces() {
        return totalPaces;
    }

    public Faculty setTotalPaces(int totalPaces) {
        this.totalPaces = totalPaces;
        return this;
    }

    public List<Subject> getRequiredSubjects() {
        return requiredSubjects;
    }

    public void setRequiredSubjects(List<Subject> requiredSubjects) {
        this.requiredSubjects = requiredSubjects;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", budgedPlaces=" + budgedPlaces +
                ", totalPaces=" + totalPaces +
                ", requiredSubjects=" + requiredSubjects +
                '}';
    }
}
