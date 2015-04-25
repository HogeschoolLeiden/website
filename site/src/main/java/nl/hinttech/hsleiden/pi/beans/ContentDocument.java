package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import java.util.List;

import org.hippoecm.hst.content.beans.Node;

/**
 * Subclass of TextDocument that adds some extra properties (like links and attachments) 
 * to a document.
 */
@Node(jcrType = "intranet:ContentPagina")
public class ContentDocument extends TextDocument {

    public static final String PROPERTY_EXTERNAL_LINKS = NAMESPACE_PREFIX + "externelinks";
    public static final String PROPERTY_INTERNAL_LINKS = NAMESPACE_PREFIX + "links";
    public static final String PROPERTY_ATTACHMENTS = NAMESPACE_PREFIX + "bijlagen";
    public static final String PROPERTY_ATTACHMENTS_TITLE = NAMESPACE_PREFIX + "bijlagenTitel";
    public static final String PROPERTY_TOC_TITLE = NAMESPACE_PREFIX + "inhoudTitel";
    public static final String PROPERTY_INTERNAL_LINKS_TITLE = NAMESPACE_PREFIX + "linksTitel";
    public static final String PROPERTY_EXTERNAL_LINKS_TITLE = NAMESPACE_PREFIX + "externeLinksTitel";

    public List<InternalLink> getInternalLinks() {
        return getChildBeansByName(PROPERTY_INTERNAL_LINKS, InternalLink.class);
    }

    public List<ExternalLink> getExternalLinks() {
        return getChildBeansByName(PROPERTY_EXTERNAL_LINKS, ExternalLink.class);
    }

    public List<InternalLink> getAttachments() {
        return getChildBeansByName(PROPERTY_ATTACHMENTS, InternalLink.class);
    }

    public String getTocTitle() {
        return (String) getProperty(PROPERTY_TOC_TITLE);
    }

    public String getInternalLinksTitle() {
        return (String) getProperty(PROPERTY_INTERNAL_LINKS_TITLE);
    }

    public String getExternalLinksTitle() {
        return (String) getProperty(PROPERTY_EXTERNAL_LINKS_TITLE);
    }

    public String getAttachmentsTitle() {
        return (String) getProperty(PROPERTY_ATTACHMENTS_TITLE);
    }

}