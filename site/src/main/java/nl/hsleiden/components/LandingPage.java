package nl.hsleiden.components;

import hslbeans.OverviewPage;

import java.util.ArrayList;
import java.util.List;

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
        
        List<OverviewBean> overviewBeans = new ArrayList<LandingPage.OverviewBean>();

        EditableMenuItem deepestExpandedItem = menu.getDeepestExpandedItem();

        for (EditableMenuItem sitemenuItem : deepestExpandedItem.getChildMenuItems()) {
            OverviewBean ob = new OverviewBean();
            ResolvedSiteMapItem resolveToSiteMapItem = sitemenuItem.resolveToSiteMapItem(request);
            HippoBean bean = BeanUtils.getBean(resolveToSiteMapItem.getHstSiteMapItem().getRelativeContentPath(),
                    request);

            // TODO: instance of overview or of the interface the overview beans will implement ??
            if (bean instanceof OverviewPage) {
                ob.setHighLighted(((OverviewPage) bean).getHighLightedItem());
                ob.setMenuItem(sitemenuItem);
                ob.setOverviewBean((OverviewPage) bean);
                overviewBeans.add(ob);
            } else {
                LOG.error("what to do in this case ?? ");
            }
        }
        return overviewBeans;
    }

    public static class OverviewBean {

        private HippoBean highLighted;
        private EditableMenuItem menuItem;
        private OverviewPage overviewBean;

        public HippoBean getHighLighted() {
            return highLighted;
        }

        public void setHighLighted(HippoBean highLighted) {
            this.highLighted = highLighted;
        }

        public EditableMenuItem getMenuItem() {
            return menuItem;
        }

        public void setMenuItem(EditableMenuItem menuItem) {
            this.menuItem = menuItem;
        }

        public OverviewPage getOverviewBean() {
            return overviewBean;
        }

        public void setOverviewBean(OverviewPage overviewBean) {
            this.overviewBean = overviewBean;
        }
    }
}
