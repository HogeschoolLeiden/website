package nl.hsleiden.components.base;

import hslbeans.WebPage;
import nl.hsleiden.componentsinfo.ContentBeanPathInfo;
import nl.hsleiden.utils.Constants.HstParameters;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseHslComponent extends BaseHstComponent {

    private static final Logger LOG = LoggerFactory.getLogger(BaseHslComponent.class);

    public HippoBean getWebDocumetBean(final HstRequest request) {
        WebPage result = null;
        HippoBean doc = request.getRequestContext().getContentBean();
        if (!(doc instanceof WebPage)) {
            Object parameters = getComponentParametersInfo(request);
            if (parameters instanceof ContentBeanPathInfo) {
                doc = getContentBeanViaParameters(request, (ContentBeanPathInfo) parameters);
            }
        }
        if (doc instanceof WebPage) {
            result = (WebPage) doc;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T extends HippoBean> T getContentBeanViaParameters(HstRequest request, ContentBeanPathInfo parametersInfo) {
        T result = null;
        String indexFilePath = request.getRequestContext().getResolvedSiteMapItem()
                .getParameter(HstParameters.CONTENT_BEAN_PATH);
        if (StringUtils.isBlank(indexFilePath)) {
            indexFilePath = parametersInfo.getContentBeanPath();
        }
        if (StringUtils.isNotBlank(indexFilePath)) {
            if (indexFilePath.startsWith("/")) {
                try {
                    result = (T) request.getRequestContext().getObjectBeanManager().getObject(indexFilePath);
                } catch (ObjectBeanManagerException e) {
                    LOG.info("Could not retrive the document of the specfied indexFilePath: {}", indexFilePath);
                    LOG.info(e.getMessage());
                }
            } else {
                result = (T) getBean(indexFilePath, request);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T extends HippoBean> T getBean(String relativePath, HstRequest request) {
        T result = null;
        String path = PathUtils.normalizePath(relativePath);
        result = (T) request.getRequestContext().getSiteContentBaseBean().getBean(path);
        return result;
    }
}
