package nl.hinttech.hsleiden.pi.beans;

import java.util.ArrayList;
import java.util.List;

import nl.hinttech.hsleiden.pi.util.HSLeiden;

/**
 * Convenience class to group metadata about a document.
 */
public class Metadata {

    private List<Group> groups = new ArrayList<Metadata.Group>();

    public void addItem(String groupTitle, String title, String path) {

        Group group = getGroup(groupTitle);
        if (group == null) {
            group = new Group(groupTitle);
            groups.add(group);
        }
        group.addItem(title, path);
    }

    private Group getGroup(String groupTitle) {
        for (Group group : groups) {
            if (groupTitle.equals(group.getTitle())) {
                return group;
            }
        }
        return null;
    }
    
    public Group getInfoForGroup() {
        return getGroup(HSLeiden.METADATA_LABEL_INFO_FOR);
    }
    
    public List<Group> getGroups() {
        return groups;
    }

    public class Group {
        private String title;
        private List<Item> items = new ArrayList<Metadata.Item>();

        Group(String title) {
            this.title = title;
        }

        public void addItem(String title, String path) {
            
            Item item = new Item(title, path);
            if (!items.contains(item)) {
                items.add(item);
            }
        }

        public String getTitle() {
            return title;
        }

        public List<Item> getItems() {
            return items;
        }
    }

    public class Item {
        private String title;
        private String path;
        
        Item(String title, String path) {
            this.title = title;
            this.path = path;
        }

        public String getTitle() {
            return title;
        }

        public String getPath() {
            return path;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj != null && obj instanceof Item) {
                return title.equals(((Item)obj).getTitle());
            }
            return false;
        }
    }
}
