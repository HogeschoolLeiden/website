package nl.hsleiden.utils;

import nl.hsleiden.components.catalog.RelatedNews;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HslUtils {

    private HslUtils() {
        super();
    }

    private static final Logger LOG = LoggerFactory.getLogger(RelatedNews.class);

    public static String getContextPath(HstRequest request) {
        String contextPath = request.getRequestContext().getVirtualHost().getVirtualHosts().getDefaultContextPath();
        boolean contextPathInUrl = request.getRequestContext().getResolvedMount().getMount().isContextPathInUrl();
        if (StringUtils.isBlank(contextPath)) {
            contextPath = request.getContextPath();
        }
        return contextPathInUrl ? contextPath : "";
    }
    
    public static String getNamespacedFieldName(String displayName){
        String result = "";
        
        if(Constants.DisplayedFieldNames.RELEASE_DATE.equals(displayName)){
            result = Constants.FieldName.RELEASE_DATE;
        }else if(Constants.DisplayedFieldNames.TITLE.equals(displayName)){
            result = Constants.FieldName.TITLE;
        }
        
        return result;
    }
    
    public static HippoBean getSelectedBean(HstRequest request, String contentBeanPath) {
        try {
            LOG.debug("content bean path = " + contentBeanPath);
            return(HippoBean) request.getRequestContext().getObjectBeanManager().getObject(contentBeanPath);
        } catch (ObjectBeanManagerException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }
}
