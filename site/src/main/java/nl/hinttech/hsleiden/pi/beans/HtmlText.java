package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

/**
 * Compound type (used within {@link WebsiteTexts}) for specifying a HTML text.
 */
@Node(jcrType = "intranet:HtmlTekst")
public class HtmlText extends BaseDocument {

    public static final String PROPERTY_KEY = NAMESPACE_PREFIX + "key";
    public static final String PROPERTY_VALUE = NAMESPACE_PREFIX + "value";

    public String getKey() {
        return getProperty(PROPERTY_KEY);
    }

    public HippoHtml getValue() {
        return getHippoHtml(PROPERTY_VALUE);
    }

}
