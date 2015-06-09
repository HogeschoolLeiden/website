package nl.hinttech.hsleiden.pi.components;

import nl.hinttech.hsleiden.pi.util.HSLeiden;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component for rendering the main menu tabs.
 */
public class MainMenuComponent extends BaseHstComponent {

    public static final Logger log = LoggerFactory.getLogger(MainMenuComponent.class);

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
        
        HstSiteMenu mainMenu = request.getRequestContext().getHstSiteMenus().getSiteMenu("main");
        request.setAttribute("mainMenu", mainMenu);
        request.setAttribute("activeMenu", getActiveMenu(request, mainMenu));

    }

    private HstSiteMenuItem getActiveMenu(HstRequest request, HstSiteMenu mainMenu) {
        HstSiteMenuItem activeMenu = HSLeiden.getRememberedActiveMenu(request);
        if (activeMenu == null) {
            activeMenu = mainMenu.getSelectSiteMenuItem();
        }
        return activeMenu;
    }

}
