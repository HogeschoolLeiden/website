// $Id: SiteSettings.java 422 2013-07-15 14:50:43Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import java.util.List;

import org.hippoecm.hst.content.beans.Node;

/**
 * Document type that can be used to store user configurable site settings.
 * For HSLeiden we use it to set the Google Analytics Account ID.
 *
 */
@Node(jcrType = "intranet:SiteSettings")
public class SiteSettings extends BaseDocument {

    public static final String PROPERTY_SETTINGS = NAMESPACE_PREFIX + "settings";
    
    public List<KeyValuePair> getSettings() {
        return getChildBeansByName(PROPERTY_SETTINGS, KeyValuePair.class);
    }
    
    public String getSetting(String key) {
        KeyValuePair setting = getKeyValuePair(key);
        if (setting != null) {
            return setting.getValue();
        }
        return null;
    }

    private KeyValuePair getKeyValuePair(String key) {
        
        List<KeyValuePair> settings = getSettings();
        if (settings != null) {
            for (KeyValuePair setting : settings) {
                if (setting.getKey().equals(key)) {
                    return setting;
                }
            }
        }
        return null;
    }
    
    
}
