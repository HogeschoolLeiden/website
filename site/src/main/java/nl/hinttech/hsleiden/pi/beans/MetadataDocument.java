// $Id: MetadataDocument.java 1477 2014-01-22 13:00:57Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstRequest;

/**
 * Abstract super class for all documents that must expose metadata.
 *
 * @author rob
 */
public abstract class MetadataDocument extends BaseDocument {
    
    public static final String PROPERTY_TITLE = NAMESPACE_PREFIX + "title";
    
    public String getTitle() {
        return getProperty(PROPERTY_TITLE);
    }
    
    public abstract boolean isForStudent();

    public abstract boolean isForEmployee();

    public abstract boolean isForService();
    
    public abstract Metadata getMetadata(final BaseHstComponent component, final HstRequest request);
}
