package nl.hsleiden.tags;

import javax.servlet.http.HttpServletRequest;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.configuration.site.HstSite;
import org.hippoecm.hst.configuration.sitemenu.HstSiteMenuConfiguration;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.sitemenu.EditableMenu;
import org.hippoecm.hst.core.sitemenu.EditableMenuItem;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuImpl;

public class MenuFunctions {

    private MenuFunctions() {
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
        
        String sitemenuConfigParameter = ParametersFunctions.getSitemenuConfigParameter(originalMenuItem, "useMainMenuConfig");
        
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
}
