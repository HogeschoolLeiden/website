package nl.hsleiden.components;

import java.util.ArrayList;
import java.util.List;

import hslbeans.OverviewPage;
import nl.hsleiden.utils.Constants.Attributes;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.components.WebDocumentDetail;
import com.tdclighthouse.prototype.componentsinfo.NavigationInfo;
import com.tdclighthouse.prototype.utils.BeanUtils;
import com.tdclighthouse.prototype.utils.Constants;

@ParametersInfo(type = NavigationInfo.class)
public class LandingPage extends WebDocumentDetail {

    private static final Logger LOG = LoggerFactory.getLogger(LandingPage.class);

    public void doBeforeRender(HstRequest request, HstResponse response) {

        HippoBean contentBean = request.getRequestContext().getContentBean();
        List<OverviewBean> overviewBeans = new ArrayList<LandingPage.OverviewBean>();
        
        final String menuName = getComponentParameters(NavigationInfo.MENU_NAME, NavigationInfo.MENU_NAME_DEFAULT,
                String.class);
        HstSiteMenu menu = request.getRequestContext().getHstSiteMenus().getSiteMenu(menuName);

        if (menu != null) {
            populateOverviewBeansList(request, overviewBeans, menu);
        }

        request.setAttribute(Attributes.ITEMS, overviewBeans);
        request.setAttribute(Constants.AttributesConstants.DOCUMENT, contentBean);
    }

    private void populateOverviewBeansList(HstRequest request, List<OverviewBean> overviewBeans, HstSiteMenu menu) {
        OverviewBean ob = new OverviewBean();

        HstSiteMenuItem deepestExpandedItem = menu.getDeepestExpandedItem();

        for (HstSiteMenuItem sitemenuItem : deepestExpandedItem.getChildMenuItems()) {
            ResolvedSiteMapItem resolveToSiteMapItem = sitemenuItem.resolveToSiteMapItem(request);
            HippoBean bean = BeanUtils.getBean(resolveToSiteMapItem.getHstSiteMapItem().getRelativeContentPath(),
                    request);

            // TODO: instance of overview or of the interface the overview beans will implement ??
            if (bean instanceof OverviewPage) {
                ob.setHighLighted(((OverviewPage) bean).getHighLightedItem());
                ob.setMenuItem(sitemenuItem);
                overviewBeans.add(ob);
            } else {
                LOG.error("what to do in this case ?? ");
            }
        }
    }

    public static class OverviewBean {

        private HippoBean highLighted;
        private HstSiteMenuItem menuItem;

        public HippoBean getHighLighted() {
            return highLighted;
        }

        public void setHighLighted(HippoBean highLighted) {
            this.highLighted = highLighted;
        }

        public HstSiteMenuItem getMenuItem() {
            return menuItem;
        }

        public void setMenuItem(HstSiteMenuItem menuItem) {
            this.menuItem = menuItem;
        }
    }
}
