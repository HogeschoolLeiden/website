// $Id: RosterDay.java 1477 2014-01-22 13:00:57Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import java.util.List;

import org.hippoecm.hst.content.beans.Node;

/**
 * Compound document type that represents a roster day within a {@link Roster}.
 *
 * @author rob
 */
@Node(jcrType = "intranet:LesDag")
public class RosterDay extends BaseDocument {

    public static final String PROPERTY_DAY = NAMESPACE_PREFIX + "day";
    public static final String PROPERTY_DAY_PARTS = NAMESPACE_PREFIX + "dayparts";
    
    public String getDay() {
        return getProperty(PROPERTY_DAY);
    }
    
    public List<String> getDayParts() {
        return getPropertyAsList(PROPERTY_DAY_PARTS);
    }
}
