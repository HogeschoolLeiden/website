package nl.hsleiden.components.base;

import nl.hsleiden.componentsinfo.NavigationInfo;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.HslUtils;
import nl.hsleiden.utils.HslUtils.Call;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.sitemenu.EditableMenu;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;

@ParametersInfo(type = NavigationInfo.class)
public class Navigation extends WebPageDetail {

    private static final String EDITABLE_MENU_ATTRIBUTE = "editableMenu";

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        final NavigationInfo parametersInfo = this.<NavigationInfo> getComponentParametersInfo(request);
        EditableMenu editableMenu = HslUtils.getCachedCall(new Call<EditableMenu>() {

            public EditableMenu makeCall(HstRequest request) {
                EditableMenu result = null;
                String menuName = parametersInfo.getMenuName();
                HstSiteMenu menu = request.getRequestContext().getHstSiteMenus().getSiteMenu(menuName);
                if (menu != null) {
                    result = menu.getEditableMenu();
                    boolean showFacet = parametersInfo.isShowFacetedNavigation();
                    new RepoBasedMenuProvider(request.getRequestContext().getSiteContentBaseBean(), showFacet, request)
                            .addRepoBasedMenuItems(result);
                }
                return result;
            }

            public Class<EditableMenu> getType() {
                return EditableMenu.class;
            }

        }, request, EDITABLE_MENU_ATTRIBUTE);

        request.setAttribute(Attributes.MENU, editableMenu);

    }

}
