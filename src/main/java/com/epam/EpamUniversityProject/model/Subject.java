package com.epam.EpamUniversityProject.model;

public class Subject {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public Subject setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Subject setName(String name) {
        this.name = name;
        return this;
    }
}
