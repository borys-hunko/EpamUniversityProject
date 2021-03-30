package com.epam.EpamUniversityProject.utils;

import com.epam.EpamUniversityProject.model.User;

import java.util.List;

public class UserSorter implements Sorter<User>{
    public static final String NAME="name";
    public static final String NAME_DESC="nameDesc";
    public static final String EMAIL="email";
    public static final String EMAIL_DESC="emailDesc";

    @Override
    public void sort(String command, List<User> list) {
        switch (command){
            case NAME:
                sort(list,User.NAME_COMPARATOR);
                break;
            case NAME_DESC:
                sort(list,User.NAME_COMPARATOR.reversed());
                break;
            case EMAIL:
                sort(list,User.EMAIL_COMPARATOR);
                break;
            case EMAIL_DESC:
                sort(list,User.EMAIL_COMPARATOR.reversed());
                break;
            default:
                throw new IllegalArgumentException("no such type of sort");
        }
    }
}
