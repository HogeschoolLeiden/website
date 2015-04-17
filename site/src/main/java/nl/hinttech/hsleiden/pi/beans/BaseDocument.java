package nl.hinttech.hsleiden.pi.beans;

import java.util.ArrayList;
import java.util.List;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;

/**
 * The base class of all our hippo document beans.
 */
@Node(jcrType = "hsleiden:basedocument")
public class BaseDocument extends HippoDocument {

    protected List<String> getPropertyAsList(String propertyName) {
        List<String> result = new ArrayList<String>();
        try {
            String[] values = getProperty(propertyName);
            if (values != null) {
                for (String value : values) {
                    result.add(value);
                }
            }
        } catch (Exception x) {
            // do nothing
        }
        return result;
    }
}
