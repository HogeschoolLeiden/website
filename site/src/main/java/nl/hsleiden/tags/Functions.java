package nl.hsleiden.tags;

import hslbeans.ArticlePage;
import hslbeans.Image;
import hslbeans.WebPage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.hsleiden.channels.WebsiteInfo;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoResource;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.sitemenu.EditableMenuItem;

import com.tdclighthouse.prototype.provider.RepoBasedMenuProvider;

public class Functions {

    private Functions() {
    }

    public static String getDefaultBrowserTitle(HttpServletRequest request) {
        String result = "";
        HstRequest req = (HstRequest) request;
        final Mount mount = req.getRequestContext().getResolvedMount().getMount();
        final WebsiteInfo info = mount.getChannelInfo();
        result = info.getDefaultBrowserTitle();

        return result;
    }

    public static boolean isSubclassOfWebPage(HippoBean hippoBean) {
        boolean result = false;
        if (hippoBean instanceof WebPage) {
            result = true;
        }
        return result;
    }

    public static String getAssetTitle(HippoResource asset) {
        String result = asset.getParentBean().getPath();
        result = result.substring(result.lastIndexOf("/") + 1);
        return result;
    }

    public static HippoBean getFirstFlexibleBlockImage(WebPage bean) {
        HippoBean result = null;
        if (bean instanceof ArticlePage) {
            ArticlePage article = (ArticlePage) bean;
            List<HippoBean> flexibleblock = article.getFlexibleblock();
            for (HippoBean hippoBean : flexibleblock) {
                if (hippoBean instanceof Image && ((Image) hippoBean).getImage() != null) {
                    result = hippoBean;
                }
            }
        }
        return result;
    }

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
