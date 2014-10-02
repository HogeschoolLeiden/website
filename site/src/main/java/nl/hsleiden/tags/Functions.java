package nl.hsleiden.tags;

import hslbeans.ArticlePage;
import hslbeans.BachelorPage;
import hslbeans.EventPage;
import hslbeans.ExternalLink;
import hslbeans.Image;
import hslbeans.ImageTeaser;
import hslbeans.InfoBlock;
import hslbeans.InternalLink;
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
        Integer depthLevel = level;
        
        for (EditableMenuItem menuItem : menu.getMenuItems()) {
            if(menuItem!=null && menuItem.isExpanded()){
                while(depthLevel!=0){
                    depthLevel--;
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
        
        if(sitemenuConfigParameter!=null && "true".equalsIgnoreCase(sitemenuConfigParameter)){
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
        
        return new HstSiteMenuImpl(null, siteMenuConfiguration, requestContext).getEditableMenu();
    }
    
    public static String getConfiguredLink(ImageTeaser imgTeaser){
        String result = "";
        InternalLink internallink = imgTeaser.getInternallink();
        ExternalLink externallink = imgTeaser.getExternallink();

        if(internallink !=null && internallink.getLink()!=null && !internallink.getLinkTitle().isEmpty()){
            result = "int";
        }

        if(result.isEmpty()){
            result = checkExternalLink(externallink);
        }

        return result;
    }

    private static String checkExternalLink(ExternalLink externallink) {
        String result = "";
        if(externallink !=null && !externallink.getLinkUrl().isEmpty() && !externallink.getLinkTitle().isEmpty()){
            result = "ext";
        }
        return result;
    }
    
    public static Boolean isInfoBlockDisplayable(HippoBean document){
        boolean result = false;
        
        if(document instanceof EventPage){
            EventPage event = (EventPage) document;
            result = checkInfoBlock(event.getInfoBlock());
        }else if (document instanceof BachelorPage){
            BachelorPage bachelor = (BachelorPage) document;
            result = checkInfoBlock(bachelor.getInfoBlock());
        }
        
        return result;
    }

    private static boolean checkInfoBlock(InfoBlock infoBlock) {
        boolean result = false;
        if(infoBlock!=null && 
           (!infoBlock.getInfoText().isEmpty() || infoBlock.getInfoLink()!=null || !infoBlock.getInfoLines().isEmpty())){
           result = true; 
        }
        return result;
    }    
}
