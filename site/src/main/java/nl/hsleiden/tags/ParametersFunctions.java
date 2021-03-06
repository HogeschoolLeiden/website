package nl.hsleiden.tags;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.sitemenu.CommonMenuItem;

import com.tdclighthouse.prototype.provider.RepoBasedMenuProvider;

public class ParametersFunctions {

    private ParametersFunctions() {
    }
    
    //components parameters
    public static String getSitemapConfigParameter(HttpServletRequest request, String paramName) {
        HstRequestContext requestContext = ((HstRequest) request).getRequestContext();
        return requestContext.getResolvedSiteMapItem().getLocalParameter(paramName);
    }

    public static String getSitemenuConfigParameter(CommonMenuItem menuItem, String paramName) {
        String result = StringUtils.EMPTY;
        if(menuItem != null && paramName!=null && !paramName.isEmpty()){            
            result = RepoBasedMenuProvider.getParameterValue(paramName, menuItem);
        }
        return result;
    }

    public static String getComponentConfigParameter(HttpServletRequest request, String paramName) {
        HstRequestContext requestContext = ((HstRequest) request).getRequestContext();
        return requestContext.getResolvedSiteMapItem().getHstComponentConfiguration().getParameter(paramName);
    }
    
    //request parameters
    public static boolean hasParameter(HttpServletRequest request, String paramName) {
        boolean result = false;
        if (request.getParameter(paramName) != null) {
            result = true;
        }
        return result;
    }

    public static String getParameter(HttpServletRequest request, String paramName) {
        return request.getParameter(paramName);
    }
}
