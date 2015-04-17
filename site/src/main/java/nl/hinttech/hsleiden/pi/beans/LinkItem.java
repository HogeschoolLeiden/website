package nl.hinttech.hsleiden.pi.beans;

import org.hippoecm.hst.content.beans.standard.HippoBean;

/**
 * Convenience class that represents a link to a (internal) document.
 */
public class LinkItem {

    private HippoBean document;
    private String title;

    public LinkItem(HippoBean document, String title) {
        super();
        this.document = document;
        this.title = title;
    }

    public HippoBean getDocument() {
        return document;
    }

    public String getTitle() {
        return title;
    }

}
