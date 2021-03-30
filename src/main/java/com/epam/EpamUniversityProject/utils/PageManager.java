package com.epam.EpamUniversityProject.utils;

import com.epam.EpamUniversityProject.model.Faculty;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * sort and allocate items for page
 */
public class PageManager<T> {
    private List<T> items;
    private Sorter<T> sorter;
    private Paginator<T> paginator;
    private String defaultSort;

    public PageManager() {
    }
    /**
     * @param items all items,which will be sorted and trimmed
     * @param sorter sorter for items
     * @param paginator paginator, which will allocate needed items
     * @param defaultSort way of sort which will be used if there is no request parameter for sort
     * */
    public PageManager(List<T> items, Sorter<T> sorter, Paginator<T> paginator, String defaultSort) {
        this.items = items;
        this.sorter = sorter;
        this.paginator = paginator;
        this.defaultSort = defaultSort;
    }

    public String getDefaultSort() {
        return defaultSort;
    }

    public void setDefaultSort(String defaultSort) {
        this.defaultSort = defaultSort;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Sorter<T> getSorter() {
        return sorter;
    }

    public void setSorter(Sorter<T> sorter) {
        this.sorter = sorter;
    }

    public Paginator<T> getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator<T> paginator) {
        this.paginator = paginator;
    }

    /**
     * sort and allocate items for specific page, set attribute for quantity of pages
     * @param request servlet request from which we
     * @return items for specific page
     * */
    public List<T> getItemsForPage(HttpServletRequest request) {
        //retrieve way of sort
        String sort = request.getParameter("sort");
        //retrieve number of page
        String pageAsString=request.getParameter("page");
        //check sort and page for nullability
        if (sort==null||sort.isEmpty()){
            sort=defaultSort;
        }
        if (pageAsString==null||pageAsString.isEmpty()){
            pageAsString="1";
        }
        sorter.sort(sort, items);
        //set attribute for pages quantity
        request.setAttribute("pageQty",paginator.getNumberOfPages(items));
        int pageNum = Integer.parseInt(pageAsString);
        return paginator.getItemsForPage(items, pageNum);
    }
}
