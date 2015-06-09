// $Id: ContactPerson.java 1477 2014-01-22 13:00:57Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.Node;

/**
 * Compound document type that represents a contact person for a {@link Minor} document.
 * 
 * @author rob
 */
@Node(jcrType = "intranet:ContactPersoon")
public class ContactPerson extends BaseDocument {

    public static final String PROPERTY_NAME = NAMESPACE_PREFIX + "name";
    public static final String PROPERTY_EMAIL_ADDRESS = NAMESPACE_PREFIX + "emailaddress";
    public static final String PROPERTY_TELEPHONE_NUMBER = NAMESPACE_PREFIX + "telephonenumber";

    @Override
    public String getName() {
        return getProperty(PROPERTY_NAME);
    }

    public String getEmailAddress() {
        return getProperty(PROPERTY_EMAIL_ADDRESS);
    }

    public String getTelephoneNumber() {
        return getProperty(PROPERTY_TELEPHONE_NUMBER);
    }
    
    public boolean getIsFilledIn() {
        return StringUtils.isNotBlank(getName()) || StringUtils.isNotBlank(getEmailAddress()) || StringUtils.isNotBlank(getTelephoneNumber());
    }
}
