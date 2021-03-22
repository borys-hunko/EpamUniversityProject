package com.epam.EpamUniversityProject.model;

import java.util.Map;

public class Subject {
    private long id;
    //first parameter-lang, second-name
    private Map<String, String> name;

    public long getId() {
        return id;
    }

    public Subject setId(long id) {
        this.id = id;
        return this;
    }

    public Map<String, String> getName() {
        return name;
    }

    public Subject setName(Map<String, String> name) {
        this.name = name;
        return this;
    }
}
