package nl.hsleiden.components;

import hslbeans.OverviewPage;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.components.GenericOverviewPage;
import com.tdclighthouse.prototype.componentsinfo.GenericOverviewPageInfo;

@ParametersInfo(type = GenericOverviewPageInfo.class)
public class GenericOverview extends GenericOverviewPage {

    @Override
    protected void addFilter(HstQuery query, HstRequest request) {
        Filter globalFilter = query.createFilter();
        try {
            globalFilter.addEqualTo("hsl:hideFromSearch", false);
            globalFilter.addNotEqualTo("jcr:uuid", getHighlightedItemUuid(request));
        } catch (FilterException e) {
            throw new HstComponentException(e.getMessage(), e); 
        }
        query.setFilter(globalFilter);
    }
    
    private String getHighlightedItemUuid(HstRequest request){
        String result = "";
        HippoBean contentBean = request.getRequestContext().getContentBean();
        if(contentBean instanceof OverviewPage){
            OverviewPage oveviewContentBean = (OverviewPage) contentBean;
            if(oveviewContentBean.getHighLightedItem() != null){
                result = oveviewContentBean.getHighLightedItem().getIdentifier();
            }
        }
        return result;
    }
}
