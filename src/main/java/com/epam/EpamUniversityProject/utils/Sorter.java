package com.epam.EpamUniversityProject.utils;

import java.util.Comparator;
import java.util.List;

public abstract class Sorter<T> {
    public void sort(List<T> list, Comparator<T> comparator){
        list.sort(comparator);
    }

    abstract public void sort(String command,List<T> list);
}
