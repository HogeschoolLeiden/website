// $Id: Paginator.java 353 2013-07-10 14:41:19Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to make the rendering of a paginator (in the search result) an easy task.
 * 
 */
public class Paginator {

    private int currentPage = 1;
    private int totalItems;
    private int pageSize = 10;
    private int radius = 5;
    private int totalPages = 0;

    public Paginator (int pageSize, int currentPage, int totalItems) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        
        this.totalItems = totalItems;
        if (this.pageSize < 1) {
            this.pageSize = 1;
        }

        this.totalPages = this.totalItems / this.pageSize;
        if (this.totalItems % this.pageSize > 0) {
            this.totalPages = this.totalPages + 1;
        }

    }

    public int getCurrentPage() {
        return currentPage;
    }
   
    public void setCurrentPage(int currentPage) {
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public boolean hasPreviousPage() {
        return currentPage > 1;
    }

    public boolean hasNextPage() {
        return currentPage < totalPages;
    }

    public int getPreviousPage() {
        if (hasPreviousPage()) {
            return currentPage - 1;
        } else {
            return 1;
        }
    }

    public int getNextPage() {
        if (hasNextPage()) {
            return currentPage + 1;
        } else {
            return totalPages;
        }
    }

    public int getStartIndex() {
        return (this.currentPage - 1) * this.pageSize + 1;
    }

    public int getEndIndex() {
        int endIndex = this.currentPage * this.pageSize;
        if (endIndex > this.totalItems) {
            endIndex = this.totalItems;
        }
        return endIndex;
    }

    public List<Integer> getPages() {
        List<Integer> pageList = new ArrayList<Integer>();
        
        int startPage = getCurrentPage() - radius;
        if (startPage < 1) {
            startPage = 1;
        }
        
        int endPage = getCurrentPage() + radius;
        if (endPage > getTotalPages()) {
            endPage = getTotalPages();
        }
        
        for (int page = startPage; page <= endPage; page++) {
            pageList.add(page);
        }
        
        return pageList;
    }

    public int getTotalItems() {
        return totalItems;
    }


}
