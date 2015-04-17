// $Id: Breadcrumb.java 460 2013-07-17 08:35:02Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import java.util.ArrayList;
import java.util.List;

import org.hippoecm.hst.core.component.HstRequest;

/**
 * Convenience class that represents a breadcrumb in the site.
 */
public class Breadcrumb {

    private List<BreadcrumbItem> items = new ArrayList<BreadcrumbItem>();

    public List<BreadcrumbItem> getItems() {
        return items;
    }
    
    public Breadcrumb() {
        items.add(new BreadcrumbItem("Home", "/"));
    }
    
    private  void removeLevel(int level) {
        
        for (int i=items.size() - 1; items.size() >= level; i--) {
            items.remove(i);
        }
    }

    public void add(int level, BreadcrumbItem breadcrumbItem) {   
        boolean removeAndAdd = true;

        if (level == 3 && items.contains(breadcrumbItem)) {
            removeAndAdd = false;  
        }  

        if (removeAndAdd) {
            removeLevel(level);
            items.add(breadcrumbItem);
        }
    }
    
    public static void setBreadcrumb(HstRequest request, int level, String title, String relativeUrl) {
        Breadcrumb breadcrumb = getBreadcrumb(request);    
        breadcrumb.add(level, new BreadcrumbItem(title, relativeUrl));    
        request.getSession().setAttribute("breadcrumb", breadcrumb);
    }
    
    public static Breadcrumb getBreadcrumb(HstRequest request) {
        Breadcrumb breadcrumb = (Breadcrumb) request.getSession().getAttribute("breadcrumb");
        if (breadcrumb == null) {
            breadcrumb = new Breadcrumb();
        }
        return breadcrumb;
    }

    public static void clear(HstRequest request) {
        request.getSession().setAttribute("breadcrumb", null);
    }
}
