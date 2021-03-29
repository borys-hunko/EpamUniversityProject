package com.epam.EpamUniversityProject.utils;

import com.epam.EpamUniversityProject.model.Faculty;

import java.util.List;

public class FacultySorter extends Sorter<Faculty> {
    public static final String NAME = "name";
    public static final String NAME_DESC = "nameDesc";
    public static final String BUDGET_PLACES = "budgetPlaces";
    public static final String BUDGET_PLACES_DESC = "budgetPlacesDesc";
    public static final String TOTAL_PLACES = "totalPlaces";
    public static final String TOTAL_PLACES_DESC = "totalPlacesDesc";


    @Override
    public void sort(String command, List<Faculty> list) {
        switch (command) {
            case NAME:
                sort(list, Faculty.COMPARE_BY_NAME);
                break;
            case NAME_DESC:
                sort(list, Faculty.COMPARE_BY_NAME.reversed());
                break;
            case BUDGET_PLACES:
                sort(list, Faculty.COMPARE_BY_BUDGET_PLACES);
                break;
            case BUDGET_PLACES_DESC:
                sort(list, Faculty.COMPARE_BY_BUDGET_PLACES.reversed());
                break;
            case TOTAL_PLACES:
                sort(list, Faculty.COMPARE_BY_TOTAL_PLACES);
                break;
            case TOTAL_PLACES_DESC:
                sort(list, Faculty.COMPARE_BY_TOTAL_PLACES.reversed());
                break;
            default:
                throw new IllegalArgumentException("no such type of sort");
        }
    }
}
