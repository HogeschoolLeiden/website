package nl.hsleiden.beans.compounds;


import nl.hsleiden.componentsinfo.RelatedEventsInfo;

import org.hippoecm.hst.content.beans.Node;

@Node(jcrType = "hsl:RelatedEventsCompoundMixin")
public class RelatedEventsCompoundMixinBean extends hslbeans.RelatedEventsCompoundMixin implements RelatedEventsInfo {
    
    @Override
    public Boolean getUseMixin() {
       throw new UnsupportedOperationException();
    }

    @Override
    public String getWidgetTitle() {
       return getWidgetParameters().getWidgetTitle();
    }

    @Override
    public String getContentBeanPath() {
        String result = null;
        if(getWidgetParameters().getContentBeanPath()!=null){
            result = getWidgetParameters().getContentBeanPath().getPath();
        }
        return result;
    }

    @Override
    public int getSize() {
        return getWidgetParameters().getSize().intValue();
    }

    @Override
    public Boolean getShowOverview() {
       return getOverviewParameters().getShowOverview();
    }

    @Override
    public String getOverviewBeanPath() {
        String result = null;
        if(getOverviewParameters().getOverviewBeanPath()!=null){
            result = getOverviewParameters().getOverviewBeanPath().getPath();
        }
        return result;
    }

    @Override
    public String getOverviewLinkLabel() {
        return getOverviewParameters().getOverviewLinkLabel();
    }


    @Override
    public String getSortOrder() {
        return getSortParameters().getSortOrder();
    }

    @Override
    public String getSortBy() {
        return getSortParameters().getSortBy();
    }


    @Override
    public Boolean getThemaFilter() {
        return getFilterParameters().getThemaFilter();
    }

    @Override
    public Boolean getOverFilter() {
        return getFilterParameters().getOverFilter();
    }
}
