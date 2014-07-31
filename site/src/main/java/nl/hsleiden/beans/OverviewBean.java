package nl.hsleiden.beans;

import hslbeans.OverviewPage;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.sitemenu.EditableMenuItem;

public class OverviewBean {

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
