package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;

/**
 * Compound type for specifying an internal link.
 */
@Node(jcrType = "hsleiden:TitelLinkBlok")
public class InternalLink extends BaseDocument {

    public static final String PROPERTY_TITLE = NAMESPACE_PREFIX + "titel";
    public static final String PROPERTY_LINK = NAMESPACE_PREFIX + "link";

    public String getTitle() {
        return getProperty(PROPERTY_TITLE);
    }

    public HippoBean getDocument() {
        return getLinkedBean(PROPERTY_LINK, HippoBean.class);
    }
}