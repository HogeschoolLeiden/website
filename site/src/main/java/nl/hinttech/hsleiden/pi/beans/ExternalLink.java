package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import org.hippoecm.hst.content.beans.Node;

/**
 * Compound type for an external link.
 */
@Node(jcrType = "hsleiden:TitelExterneLinkBlok")
public class ExternalLink extends BaseDocument {

    public static final String PROPERTY_TITLE = NAMESPACE_PREFIX + "titel";
    public static final String PROPERTY_LINK = NAMESPACE_PREFIX + "link";

    public String getTitle() {
        return getProperty(PROPERTY_TITLE);
    }

    public String getUrl() {
        return getProperty(PROPERTY_LINK);
    }
}