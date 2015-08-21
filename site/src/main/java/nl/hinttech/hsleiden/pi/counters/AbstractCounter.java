// $Id: AbstractCounter.java 237 2013-07-03 06:32:10Z rharing $
package nl.hinttech.hsleiden.pi.counters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.IndexSearcher;
import org.hippoecm.hst.content.beans.standard.HippoDocumentBean;

/**
 * Abstract counter class that contains the shared code for both the {@link PageViewCounter} and the
 * {@link CommentsCounter} classes.
 * 
 * @author Rob Haring
 */
public class AbstractCounter {

    protected Map<String, Count> counts;

    public AbstractCounter() {
        counts = Collections.synchronizedMap(new HashMap<String, Count>());
    }

    /**
     * Returns a List containing the Count objects from the cached article map. The list is sorted (on count attribute)
     * in the specified sort direction.
     * 
     * @return the list
     */
    protected List<Count> sort(final Sort.SortDirection sortDirection) {

        List<Count> list = new ArrayList<Count>(counts.values());
        Collections.sort(list, new Comparator<Count>() {
            public int compare(final Count o1, final Count o2) {
                if (sortDirection.equals(Sort.SortDirection.DESCENDING)) {
                    return (o2.getCount() - o1.getCount());
                } else {
                    return (o1.getCount() - o2.getCount());
                }
            }
        });
        return list;
    }

    /**
     * @param items
     * @param sortDirection
     * @return
     */
    public List<IndexSearcher> sort(final List<IndexSearcher> items, final Sort.SortDirection sortDirection) {

        List<IndexSearcher> sortedList = new ArrayList<IndexSearcher>();

        // sort the values within the internal counts map in the specified direction
        List<Count> list = sort(sortDirection);

        if (sortDirection.equals(Sort.SortDirection.ASCENDING)) {
            for (IndexSearcher item : items) {
                if (!isCounted(((HippoDocumentBean) item).getCanonicalHandleUUID())) {
                    sortedList.add(item);
                }
            }
        }
        for (Count count : list) {
            String uuid = count.getUuid();
            IndexSearcher item = getAndRemoveItemFromList(uuid, items);
            if (item != null) {
                sortedList.add(item);
            }
        }
        if (sortDirection.equals(Sort.SortDirection.DESCENDING)) {
            for (IndexSearcher item : items) {
                sortedList.add(item);
            }
        }

        return sortedList;
    }

    private boolean isCounted(final String uuid) {

        Count count = counts.get(uuid);
        return (count != null);
    }

    private IndexSearcher getAndRemoveItemFromList(final String uuid, final List<IndexSearcher> items) {

        for (int i = 0; i < items.size(); i++) {

            HippoDocumentBean item = (HippoDocumentBean) items.get(i);
            if (item.getCanonicalHandleUUID().equals(uuid)) {
                items.remove(i);
                return (IndexSearcher) item;
            }
        }
        return null;
    }
}
