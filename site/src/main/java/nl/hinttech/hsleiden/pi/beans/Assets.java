// $Id: Assets.java 422 2013-07-15 14:50:43Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Convenience class to group {@link Asset}s.
 */
public class Assets {

    private List<Group> groups = new ArrayList<Assets.Group>();

    public void addAsset(String groupTitle, Asset asset) {
        if (asset != null) {
            Group group = getGroup(groupTitle);
            if (group == null) {
                group = new Group(groupTitle);
                groups.add(group);
            }
            group.addAsset(asset);
        }
    }

    private Group getGroup(String title) {

        for (Group group : groups) {
            if (group.title.equals(title)) {
                return group;
            }
        }
        return null;
    }

    public class Group {
        private String title;
        private List<Asset> assets = new ArrayList<Asset>();

        public Group(String title) {
            this.title = title;
        }

        public void addAsset(Asset asset) {
            if (asset != null) {
                if (!assets.contains(asset)) {
                    assets.add(asset);
                }
            }
        }

        public List<Asset> getAssets() {
            return assets;
        }

        public String getTitle() {
            return title;
        }

    }

    public List<Group> getGroups() {
        return groups;
    }
}
