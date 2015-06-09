// $Id: GroupedResult.java 1477 2014-01-22 13:00:57Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Convenience class for grouping documents by categories.
 * 
 * @author rob
 */
public class GroupedResult<T extends BaseDocument> {

    private List<Group<T>> groups = new ArrayList<Group<T>>();

    public void addDocument(final T document, final String category) {
        if (category != null) {
            Group<T> group = getGroup(category);
            if (group == null) {
                group = new Group<T>(category);
                groups.add(group);
            }
            group.addDocument(document);
        }
    }

    private Group<T> getGroup(final String category) {
        if (category != null) {
            for (Group<T> group : groups) {
                if (category.equals(group.getCategory())) {
                    return group;
                }
            }
        }
        return null;
    }
    
    public List<Group<T>> getGroups() {
        return groups;
    }

    public class Group<R> {

        private String category;
        private List<R> documents = new ArrayList<R>();

        Group(final String category) {
            this.category = category;
        }

        public void addDocument(final R document) {
            documents.add(document);
        }

        public List<R> getDocuments() {
            return documents;
        }

        public String getCategory() {
            return category;
        }

    }
}
