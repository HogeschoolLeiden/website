package nl.hsleiden.tags;

import javax.servlet.http.HttpServletRequest;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.sitemenu.EditableMenuItem;

import com.tdclighthouse.prototype.provider.RepoBasedMenuProvider;

public class ParametersFunctions {

    private ParametersFunctions() {
    }
    
    public static String getSitemapConfigParameter(HttpServletRequest request, String paramName) {
        HstRequestContext requestContext = ((HstRequest) request).getRequestContext();
        return requestContext.getResolvedSiteMapItem().getLocalParameter(paramName);
    }

    public static String getSitemenuConfigParameter(EditableMenuItem menuItem, String paramName) {
        String result = "";
        if(menuItem != null && paramName!=null && !paramName.isEmpty()){            
            result = RepoBasedMenuProvider.getParameterValue(paramName, menuItem);
        }
        return result;
    }

    public static String getComponentConfigParameter(HttpServletRequest request, String paramName) {
        HstRequestContext requestContext = ((HstRequest) request).getRequestContext();
        return requestContext.getResolvedSiteMapItem().getHstComponentConfiguration().getParameter(paramName);
    }
}
