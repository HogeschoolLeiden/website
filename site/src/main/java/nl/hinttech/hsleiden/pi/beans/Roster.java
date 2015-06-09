// $Id: Roster.java 1500 2014-01-27 07:36:45Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import java.util.List;

import org.hippoecm.hst.content.beans.Node;

/**
 * Compound document type that represents a Roster for a {@link Minor} document.
 *
 * @author rob
 */
@Node(jcrType = "intranet:Rooster")
public class Roster extends BaseDocument {
    
    public static final String PROPERTY_ROSTER_DAYS = NAMESPACE_PREFIX + "rosterdays";
    
    public List<RosterDay> getRosterDays() {
        return getChildBeansByName(PROPERTY_ROSTER_DAYS, RosterDay.class);
    }
    
    public boolean getIsFilledIn() {
        return (getRosterDays().size() > 0);
    }



    public RosterDay getDay(final String key) {
        for (RosterDay day: getRosterDays()) {
            if (key.equals(day.getDay())) {
                return day;
            }
        }
        return null;
    }
}
