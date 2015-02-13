package nl.hsleiden.beans;

import org.hippoecm.hst.core.linking.HstLink;

public class BreadcrumbItemBean {

    private final String name;
    private final HstLink link;

    public BreadcrumbItemBean(String name, HstLink link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public HstLink getLink() {
        return link;
    }

}
