package nl.hsleiden.components;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.sitemenu.EditableMenu;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;

import com.tdclighthouse.prototype.components.Navigation;
import com.tdclighthouse.prototype.componentsinfo.NavigationInfo;
import com.tdclighthouse.prototype.provider.RepoBasedMenuProvider;
import com.tdclighthouse.prototype.utils.Constants;

@ParametersInfo(type = NavigationInfo.class)
public class FooterNavigation extends Navigation{

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        final NavigationInfo parametersInfo = this.<NavigationInfo> getComponentParametersInfo(request);
        
        EditableMenu result = null;
        String menuName = parametersInfo.getMenuName();
        HstSiteMenu menu = request.getRequestContext().getHstSiteMenus().getSiteMenu(menuName);
        if (menu != null) {
            result = menu.getEditableMenu();
            boolean showFacet = parametersInfo.isShowFacetedNavigation();
            new RepoBasedMenuProvider(request.getRequestContext().getSiteContentBaseBean(), showFacet, request)
                    .addRepoBasedMenuItems(result);
        }
        
        request.setAttribute(Constants.AttributesConstants.MENU, result);

    }
}
