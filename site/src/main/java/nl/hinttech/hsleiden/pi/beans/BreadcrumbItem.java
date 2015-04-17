// $Id: BreadcrumbItem.java 460 2013-07-17 08:35:02Z rharing $
package nl.hinttech.hsleiden.pi.beans;

/**
 * Simple class that represents a single breadcrumb item.
 */
public class BreadcrumbItem {

    private String relativeUrl;
    private String title;
    
    public BreadcrumbItem(String title, String relativeUrl) {
        this.relativeUrl = relativeUrl;
        this.title = title;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof BreadcrumbItem) {
            return (relativeUrl.equals(((BreadcrumbItem) obj).getRelativeUrl()));
        }
        return false;
    }
}
