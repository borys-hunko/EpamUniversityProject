package UniProject.utils;

import java.util.Comparator;
import java.util.List;
/**
 * class to sort list of items
 * */
public interface Sorter<T> {
    /**
     * sort list depending on comparator
     * @param list list to sort
     * @param comparator comparator determining way of sort
     * */
    default void sort(List<T> list, Comparator<T> comparator){
        list.sort(comparator);
    }
    /**
     * sort list depending on command
     * @param list list to sort
     * @param command command determining way of sort
     * */
    void sort(String command,List<T> list);
}
