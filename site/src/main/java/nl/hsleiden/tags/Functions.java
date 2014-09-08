package nl.hsleiden.tags;

import hslbeans.ArticlePage;
import hslbeans.Image;
import hslbeans.WebPage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.hsleiden.channels.WebsiteInfo;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.configuration.site.HstSite;
import org.hippoecm.hst.configuration.sitemenu.HstSiteMenuConfiguration;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoResource;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.sitemenu.EditableMenu;
import org.hippoecm.hst.core.sitemenu.EditableMenuItem;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuImpl;

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
    
    public static EditableMenuItem getTopMenuItem(EditableMenu menu, Integer level) {
        EditableMenuItem result = null;
        
        for (EditableMenuItem menuItem : menu.getMenuItems()) {
            if(menuItem!=null && menuItem.isExpanded()){
                while(level!=0){
                    level--;
                    menuItem = recurseMenuItems(menuItem);
                }
                result = menuItem;
            }
        }        
        return result;
    }
    
    public static Integer indexOf(List<String> list, String item) {
        return list.indexOf(item);
    }
    
    private static EditableMenuItem recurseMenuItems(EditableMenuItem menuItem) {
        EditableMenuItem result = null;
        if(menuItem!=null){            
            for (EditableMenuItem child : menuItem.getChildMenuItems()) {
                if(child.isExpanded()){
                    result = child;
                }
            }
        }
        if(result==null){
            result = menuItem;
        }
        return result;
    }
    
    public static EditableMenuItem getMenuItem(HttpServletRequest req, EditableMenuItem originalMenuItem){
        EditableMenuItem result = originalMenuItem;
        
        String sitemenuConfigParameter = getSitemenuConfigParameter(originalMenuItem, "useMainMenuConfig");
        
        if(sitemenuConfigParameter!=null && sitemenuConfigParameter.equalsIgnoreCase("true")){
            HstRequest request = (HstRequest) req;
            
            EditableMenu mainMenu = getSiteMainMenu(request);
            
            String originalMenuItemName = originalMenuItem.getName();
            for (EditableMenuItem menuItem : mainMenu.getMenuItems()) {
                if(originalMenuItemName.equals(menuItem.getName())){
                    result = menuItem;
                    break;
                }
            }
        }

        return result;
    }

    private static EditableMenu getSiteMainMenu(HstRequest request) {
        HstRequestContext requestContext = request.getRequestContext();
        Mount mount = requestContext.getMount("hsl");
        HstSite hstSite = mount.getHstSite();
        HstSiteMenuConfiguration siteMenuConfiguration = hstSite
                .getSiteMenusConfiguration().getSiteMenuConfiguration("main");
        
        EditableMenu mainMenu = new HstSiteMenuImpl(null, siteMenuConfiguration, requestContext).getEditableMenu();
        return mainMenu;
    }
    
}
