// $Id: SearchResult.java 295 2013-07-08 14:12:10Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import java.util.ArrayList;
import java.util.List;

import org.hippoecm.hst.content.beans.query.HstQueryResult;

/**
 * Simple class that holds the searchs result after a search action.
 * 
 */
public class SearchResult {

    private List<SearchResultItem> items = new ArrayList<SearchResultItem>();
    private HstQueryResult originalResult;

    public SearchResult(List<SearchResultItem> items, HstQueryResult originalResult) {
        super();
        this.items = items;
        this.originalResult = originalResult;
    }

    public List<SearchResultItem> getItems() {
        return items;
    }

    public int totalSize() {
        return originalResult.getTotalSize();
    }

}
