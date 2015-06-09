// $Id: Accessibility.java 1478 2014-01-22 14:14:32Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.Node;

/**
 * Compound document type that represents an accessibility aspect for a {@link Minor} document.
 * 
 * @author rob
 */
@Node(jcrType = "intranet:Toegankelijkheid")
public class Accessibility extends BaseDocument {

    public static final String PROPERTY_EDUCATION_TYPES = NAMESPACE_PREFIX + "educationtypes";
    public static final String PROPERTY_REQUIREMENTS = NAMESPACE_PREFIX + "requirements";
    public static final String PROPERTY_EDUCATIONS = NAMESPACE_PREFIX + "educations";

    public List<String> getEducationTypes() {
        return getPropertyAsList(PROPERTY_EDUCATION_TYPES);
    }

    public String getRequirements() {
        return getProperty(PROPERTY_REQUIREMENTS);
    }

    public String getEducations() {
        return getProperty(PROPERTY_EDUCATIONS);
    }

    public boolean getIsFilledIn() {
        return (StringUtils.isNotBlank(getRequirements()) || StringUtils.isNotBlank(getEducations()) || (getEducationTypes()
                .size() > 0));
    }
}
