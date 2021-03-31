package com.epam.EpamUniversityProject.utils;

import com.epam.EpamUniversityProject.model.Application;

import java.util.List;

public class ApplicationSorter implements Sorter<Application> {
    public static final String USER_EMAIL="email";
    public static final String USER_EMAIL_DESC = "emailDesc";
    public static final String FACULTY_NAME="faculty";

    @Override
    public void sort(String command, List<Application> list) {

    }
}
