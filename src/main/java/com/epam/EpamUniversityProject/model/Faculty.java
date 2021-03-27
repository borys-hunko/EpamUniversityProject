package com.epam.EpamUniversityProject.model;

import java.util.List;
import java.util.Objects;

public class Faculty {
    private long id;
    private String name;
    private int budgetPlaces;
    private int totalPlaces;
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

    public int getBudgetPlaces() {
        return budgetPlaces;
    }

    public Faculty setBudgetPlaces(int budgetPlaces) {
        this.budgetPlaces = budgetPlaces;
        return this;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public Faculty setTotalPlaces(int totalPlaces) {
        this.totalPlaces = totalPlaces;
        return this;
    }

    public List<Subject> getRequiredSubjects() {
        return requiredSubjects;
    }

    public Faculty setRequiredSubjects(List<Subject> requiredSubjects) {
        this.requiredSubjects = requiredSubjects;
        return this;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", budgedPlaces=" + budgetPlaces +
                ", totalPaces=" + totalPlaces +
                ", requiredSubjects=" + requiredSubjects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id
                && budgetPlaces == faculty.budgetPlaces
                && totalPlaces == faculty.totalPlaces
                && Objects.equals(name, faculty.name)
                && Objects.equals(requiredSubjects, faculty.requiredSubjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, budgetPlaces, totalPlaces, requiredSubjects);
    }
}
