package nl.hsleiden.tags;

import javax.servlet.http.HttpServletRequest;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.configuration.site.HstSite;
import org.hippoecm.hst.configuration.sitemenu.HstSiteMenuConfiguration;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.sitemenu.CommonMenu;
import org.hippoecm.hst.core.sitemenu.CommonMenuItem;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuImpl;

import com.tdclighthouse.prototype.utils.NavigationUtils;

public class MenuFunctions {

    private MenuFunctions() {
    }

    public static CommonMenuItem getTopMenuItem(CommonMenu menu, Integer level) {
        CommonMenuItem result = null;
        if (menu != null) {
            result = getTopMenuItemOfNotNullMenu(menu, level);
        }
        return result;

    }

    // just for making sonar happy :)
    private static CommonMenuItem getTopMenuItemOfNotNullMenu(CommonMenu menu, Integer level) {
        CommonMenuItem result = null;
        Integer depthLevel = level;
        for (CommonMenuItem menuItem : NavigationUtils.getMenuItems(menu)) {
            if (menuItem != null && menuItem.isExpanded()) {
                while (depthLevel != 0) {
                    depthLevel--;
                    menuItem = recurseMenuItems(menuItem);
                }
                result = menuItem;
            }
        }
        return result;
    }

    private static CommonMenuItem recurseMenuItems(CommonMenuItem menuItem) {
        CommonMenuItem result = null;
        if (menuItem != null) {
            for (CommonMenuItem child : NavigationUtils.getSubmenuItems(menuItem)) {
                if (child.isExpanded()) {
                    result = child;
                }
            }
        }
        if (result == null) {
            result = menuItem;
        }
        return result;
    }

    public static CommonMenuItem getMenuItem(HttpServletRequest req, CommonMenuItem originalMenuItem) {
        CommonMenuItem result = originalMenuItem;

        String sitemenuConfigParameter = ParametersFunctions.getSitemenuConfigParameter(originalMenuItem,
                "useMainMenuConfig");

        if (sitemenuConfigParameter != null && "true".equalsIgnoreCase(sitemenuConfigParameter)) {
            HstRequest request = (HstRequest) req;

            CommonMenu mainMenu = getSiteMainMenu(request);

            String originalMenuItemName = originalMenuItem.getName();
            for (CommonMenuItem menuItem : NavigationUtils.getMenuItems(mainMenu)) {
                if (originalMenuItemName.equals(menuItem.getName())) {
                    result = menuItem;
                    break;
                }
            }
        }

        return result;
    }

    private static CommonMenu getSiteMainMenu(HstRequest request) {
        HstRequestContext requestContext = request.getRequestContext();
        Mount mount = requestContext.getMount("hsl");
        HstSite hstSite = mount.getHstSite();
        HstSiteMenuConfiguration siteMenuConfiguration = hstSite.getSiteMenusConfiguration().getSiteMenuConfiguration(
                "main");

        return new HstSiteMenuImpl(null, siteMenuConfiguration, requestContext);
    }
}
