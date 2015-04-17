package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import org.hippoecm.hst.content.beans.Node;

/**
 * Compound type (used within {@link WebsiteTexts}) for specifying piece of (unformatted) text.
 */
@Node(jcrType = "hsleiden:PlatteTekst")
public class SimpleText extends BaseDocument {

    public static final String PROPERTY_KEY = NAMESPACE_PREFIX + "key";
    public static final String PROPERTY_VALUE = NAMESPACE_PREFIX + "value";

    public String getKey() {
        return getProperty(PROPERTY_KEY);
    }

    public String getValue() {
        return getProperty(PROPERTY_VALUE);
    }

}
