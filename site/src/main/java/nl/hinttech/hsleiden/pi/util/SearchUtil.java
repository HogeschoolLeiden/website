// $Id: SearchUtil.java 295 2013-07-08 14:12:10Z rharing $
package nl.hinttech.hsleiden.pi.util;

import java.util.ArrayList;
import java.util.List;

import nl.hinttech.hsleiden.pi.beans.TextDocument;
import nl.hinttech.hsleiden.pi.components.BaseComponent;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoDocumentBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.util.ContentBeanUtils;

public class SearchUtil {

    public static List<TextDocument> findReferingDocuments(BaseComponent component, HstRequest request, HippoDocumentBean bean) {
        
        List<TextDocument> referingDocuments = new ArrayList<TextDocument>();
        
        try {
            
            HippoBean scope = component.getContentRoot(request);
            HstQuery query = ContentBeanUtils.createIncomingBeansQuery(bean, scope, 3, TextDocument.class, true);
    
            HstQueryResult result = query.execute();
    
            HippoBeanIterator it = result.getHippoBeans();
            while (it.hasNext()) {
                referingDocuments.add((TextDocument)it.next());
            }
            
        } catch (Exception x) {
            HSLeiden.log.error("Failed to find referring document", x);
        }
        return referingDocuments;
    }

}
