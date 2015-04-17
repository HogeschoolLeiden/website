// $Id: KeyValuePair.java 422 2013-07-15 14:50:43Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;

/**
 * Compound type to store one setting in a {@link SiteSettings} document.
 *
 */
@Node(jcrType = "hsleiden:KeyValuePair")
public class KeyValuePair extends HippoDocument {
    
    public static final String PROPERTY_KEY = NAMESPACE_PREFIX + "key";
    public static final String PROPERTY_VALUE = NAMESPACE_PREFIX + "value";
    
    public String getKey() {
        return getProperty(PROPERTY_KEY);
    }
    
    public String getValue() {
        return getProperty(PROPERTY_VALUE);
    }
}
