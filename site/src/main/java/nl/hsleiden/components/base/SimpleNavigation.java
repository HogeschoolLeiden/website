package nl.hsleiden.components.base;

import nl.hsleiden.componentsinfo.SimpleNavigationInfo;
import nl.hsleiden.utils.Constants.Attributes;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;

@ParametersInfo(type = SimpleNavigationInfo.class)
public class SimpleNavigation extends BaseHstComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        String menuName = this.<SimpleNavigationInfo> getComponentParametersInfo(request).getMenuName();
        HstSiteMenu menu = request.getRequestContext().getHstSiteMenus().getSiteMenu(menuName);
        request.setAttribute(Attributes.MENU, menu);
    }

}
