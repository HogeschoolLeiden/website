package nl.hsleiden.components;

import hslbeans.OverviewPage;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.beans.OverviewBean;
import nl.hsleiden.utils.Constants.Attributes;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.core.sitemenu.EditableMenu;
import org.hippoecm.hst.core.sitemenu.EditableMenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.components.Navigation;
import com.tdclighthouse.prototype.componentsinfo.NavigationInfo;
import com.tdclighthouse.prototype.utils.BeanUtils;
import com.tdclighthouse.prototype.utils.Constants;

@ParametersInfo(type = NavigationInfo.class)
public class LandingPage extends Navigation {

    private static final Logger LOG = LoggerFactory.getLogger(LandingPage.class);

    public void doBeforeRender(HstRequest request, HstResponse response) {

        super.doBeforeRender(request, response);
        
        HippoBean contentBean = request.getRequestContext().getContentBean();
        List<OverviewBean> overviewBeans = null;
        
        EditableMenu menu = (EditableMenu) request.getAttribute(Constants.AttributesConstants.MENU);

        if (menu != null) {
            overviewBeans = getOverviewBeansList(request, menu);
        }

        request.setAttribute(Attributes.ITEMS, overviewBeans);
        request.setAttribute(Constants.AttributesConstants.DOCUMENT, contentBean);
    }

    private List<OverviewBean> getOverviewBeansList(HstRequest request, EditableMenu menu) {
        
        List<OverviewBean> overviewBeans = new ArrayList<OverviewBean>();

        EditableMenuItem deepestExpandedItem = menu.getDeepestExpandedItem();

        for (EditableMenuItem sitemenuItem : deepestExpandedItem.getChildMenuItems()) {
            OverviewBean ob = new OverviewBean();
            ResolvedSiteMapItem resolveToSiteMapItem = sitemenuItem.resolveToSiteMapItem(request);
            HippoBean bean = BeanUtils.getBean(resolveToSiteMapItem.getHstSiteMapItem().getRelativeContentPath(),
                    request);

            if (bean instanceof OverviewPage) {
                ob.setHighLighted(((OverviewPage) bean).getHighLightedItem());
                ob.setMenuItem(sitemenuItem);
                ob.setOverviewBean((OverviewPage) bean);
                overviewBeans.add(ob);
            } else {
                LOG.error("Document referenced by sub menu item is not an OverviewPage. Skipping it.");
            }
        }
        return overviewBeans;
    }


}
