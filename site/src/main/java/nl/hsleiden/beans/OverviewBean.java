package nl.hsleiden.beans;

import hslbeans.OverviewPage;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;

public class OverviewBean {

    private HippoBean highLighted;
    private HstSiteMenuItem menuItem;
    private OverviewPage overviewBean;

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

    public OverviewPage getOverviewBean() {
        return overviewBean;
    }

    public void setOverviewBean(OverviewPage overviewBean) {
        this.overviewBean = overviewBean;
    }
}
