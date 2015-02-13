package nl.hsleiden.components.catalog;

import static com.tdclighthouse.prototype.utils.Constants.AttributesConstants.MENU;
import static com.tdclighthouse.prototype.utils.Constants.AttributesConstants.MODEL;
import static com.tdclighthouse.prototype.utils.Constants.HstParametersConstants.DISABLED;
import static com.tdclighthouse.prototype.utils.Constants.ValuesConstants.TRUE;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.beans.BreadcrumbItemBean;
import nl.hsleiden.tags.ParametersFunctions;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.sitemenu.EditableMenu;
import org.hippoecm.hst.core.sitemenu.EditableMenuItem;

import com.tdclighthouse.prototype.components.Navigation;

public class Breadcrumb extends Navigation {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        List<BreadcrumbItemBean> model = new ArrayList<>();
        EditableMenu editableMenu = (EditableMenu) request.getAttribute(MENU);
        for (EditableMenuItem item : editableMenu.getMenuItems()) {
            if (item.isExpanded() || item.isSelected()) {
                if (!TRUE.equals(ParametersFunctions.getSitemenuConfigParameter(item, DISABLED))) {
                    model.add(new BreadcrumbItemBean(item.getName(), item.getHstLink()));
                }
                processChildren(model, item);
                break;
            }
        }
        request.setAttribute(MODEL, model);
    }

    private void processChildren(List<BreadcrumbItemBean> model, EditableMenuItem item) {
        for (EditableMenuItem subitem : item.getChildMenuItems()) {
            if (subitem.isExpanded() || subitem.isSelected()) {
                if (!TRUE.equals(ParametersFunctions.getSitemenuConfigParameter(subitem, DISABLED))) {
                    model.add(new BreadcrumbItemBean(subitem.getName(), subitem.getHstLink()));
                }
                processChildren(model, subitem);
                break;
            }
        }
    }

}
