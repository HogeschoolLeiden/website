package nl.hinttech.hsleiden.pi.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Convenience class that is used to group documents in the browse screen.
 * 
 * @author rob
 * 
 */
public class BrowseResult {

    private List<Group> groups = new ArrayList<BrowseResult.Group>();

    public Integer getLeftColumnStartIndex() {
        return 1;
    }

    public Integer getLeftColumnEndIndex() {
        return Math.round(groups.size() / 2);
    }

    public Integer getRightColumnStartIndex() {
        return getLeftColumnEndIndex() + 1;
    }

    public Integer getRightColumnEndIndex() {
        return groups.size() - 1;
    }

    public void addDocument(final TextDocument document, final String category, final String categoryTitle) {

        Group group = getGroup(category);
        if (group == null) {
            group = new Group(category, categoryTitle);
            groups.add(group);
        }
        group.addDocument(document);
    }

    private Group getGroup(final String category) {

        for (Group group : groups) {
            if (group.category.equals(category)) {
                return group;
            }
        }
        return null;
    }

    public class Group {

        private String category;
        private String categoryTitle;
        private List<TextDocument> documents = new ArrayList<TextDocument>();

        Group(final String category, final String categoryTitle) {
            this.category = category;
            this.categoryTitle = categoryTitle;
        }

        public void addDocument(final TextDocument document) {
            if (document != null && !documents.contains(document)) {
                documents.add(document);
            }
        }

        public List<TextDocument> getDocuments() {
            return documents;
        }

        public String getCategory() {
            return category;
        }

        public String getCategoryTitle() {
            return categoryTitle;
        }

        void sort() {
            Collections.sort(documents, new DocumentComparator());
        }
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void sort() {

        // First sort the groups on alphabet.
        Collections.sort(getGroups(), new GroupComparator());

        // Second... sort all documents within the groups on alphabet
        for (Group group : groups) {
            group.sort();
        }
    }

    private class GroupComparator implements Comparator<Group> {

        @Override
        public int compare(final Group o1, final Group o2) {
            return o1.getCategoryTitle().compareToIgnoreCase(o2.getCategoryTitle());
        }
    }

    private class DocumentComparator implements Comparator<TextDocument> {

        @Override
        public int compare(final TextDocument o1, final TextDocument o2) {
            return o1.getTitle().compareToIgnoreCase(o2.getTitle());
        }

    }
}
