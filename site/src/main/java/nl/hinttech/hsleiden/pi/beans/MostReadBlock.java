package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import java.util.List;

import nl.hinttech.hsleiden.pi.counters.PageViewCounter;

import org.hippoecm.hst.content.beans.Node;

/**
 * Simple document type that a user can use to configure a couple of (most-used) internal links. The front-end will fill
 * this list up with dynamically determined most read documents (using the {@link PageViewCounter}.
 * 
 */
@Node(jcrType = "intranet:VeelGelezenBlok")
public class MostReadBlock extends BaseDocument {

    public static final String PROPERTY_ABOUT = NAMESPACE_PREFIX + "about";
    public static final String PROPERTY_LINKS = NAMESPACE_PREFIX + "links";

    public String getAbout() {
        return getProperty(PROPERTY_ABOUT);
    }

    public List<InternalLink> getLinks() {
        return getChildBeansByName(PROPERTY_LINKS, InternalLink.class);
    }
}
