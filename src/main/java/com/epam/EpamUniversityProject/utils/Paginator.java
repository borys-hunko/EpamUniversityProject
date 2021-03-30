package com.epam.EpamUniversityProject.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * class that split allocate items for specific page
 * */
public class Paginator<T> {
    private int itemsPerPage;


    public Paginator(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public Paginator<T> setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
        return this;
    }

    /**
     * return list of items needed for page
     * @param allItems list of all items
     * @param pageNum number of pages
     * */
    public List<T> getItemsForPage(List<T> allItems, int pageNum){
        int from=(pageNum-1)*itemsPerPage;
        int to=pageNum*itemsPerPage;
        if (to>allItems.size()){
            to=allItems.size();
        }
        return allItems.subList(from,to);
    }

    /**
     * calculate number of pages for the list of items depending on its size
     * @param allItems list of all items
     * */
    public int getNumberOfPages(List<T> allItems){
        int pageNum=allItems.size()/itemsPerPage;
        //if number of items isn't multiple of number of items per page
        //page number round to integer
        // that's why we need increase number of page by 1
        // to have access to all items
        if (allItems.size()%itemsPerPage!=0) {
            ++pageNum;
        }
        return pageNum;
    }
}
