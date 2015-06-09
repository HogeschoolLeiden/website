package nl.hinttech.hsleiden.pi.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;

/**
 * The base class of all our hippo document beans.
 */
@Node(jcrType = "intranet:basedocument")
public class BaseDocument extends HippoDocument {

    @SuppressWarnings("unchecked")
    protected <T> List<T> getPropertyAsList(final String propertyName) {
        
        List<T> result = null;
        Object value = getProperty(propertyName);
        if (value != null) {
            if (value instanceof Object[]) {
                T[] array = getProperty(propertyName);
                if (array != null) {
                    result = Arrays.asList(array);
                }
            } else {
                if (StringUtils.isNotBlank((String)value)) {
                    result = new ArrayList<T>();
                    result.add((T) value);
                }
            }
        }
        if (result == null) {
            result = new ArrayList<T>();
        }
        return result;
    }
    
    protected <T extends BaseDocument> T getChildBeanByName(final String propertyName) {
        List<T> beans = getChildBeansByName(propertyName);
        if (beans.size() > 0) {
            return beans.get(0);
        }
        return null;
    }
}
