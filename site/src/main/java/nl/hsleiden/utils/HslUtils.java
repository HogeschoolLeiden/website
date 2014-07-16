package nl.hsleiden.utils;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.manager.ObjectBeanManager;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HslUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HslUtils.class);

    private HslUtils() {
        super();
    }


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
    
    public static String getMatchingSitemap(final HstRequest request, String beanPath) {
        String result = null;
        try {
            HstRequestContext requestContext = request.getRequestContext();
            ObjectBeanManager objectBeanManager = requestContext.getObjectBeanManager();
            HippoBean bean = (HippoBean) objectBeanManager.getObject(beanPath);
            HstLink link = requestContext.getHstLinkCreator().create(bean, requestContext);
            result = getRelativeUrlPath(request, link.toUrlForm(requestContext, false));
        } catch (ObjectBeanManagerException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }
    
    public static String getRelativeUrlPath(HstRequest request, String relativeUrlform) {
        String result = relativeUrlform;

        HstRequestContext requestContext = request.getRequestContext();
        Mount mount = requestContext.getResolvedMount().getMount();

        String defaultContextPath = mount.getVirtualHost().getVirtualHosts().getDefaultContextPath();
        if (relativeUrlform.startsWith(defaultContextPath) && mount.isContextPathInUrl()) {
            result = result.substring(defaultContextPath.length());
        }

        return result;
    }
    
    public static Filter addFilterOnField(HstQuery query, String[] filterValues, String fieldToFilter) throws FilterException {
        Filter f = query.createFilter();
        for (String value : filterValues) {
            Filter tagfilter = query.createFilter();
            tagfilter.addEqualTo(fieldToFilter, value);
            f.addOrFilter(tagfilter);
        }
        return f;
    }
}
