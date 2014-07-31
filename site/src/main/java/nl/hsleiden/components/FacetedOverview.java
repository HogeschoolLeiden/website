package nl.hsleiden.components;

import hslbeans.OverviewPage;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.components.MonolithicFacetedOverview;
import com.tdclighthouse.prototype.componentsinfo.FacetedOverviewPageInfo;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = FacetedOverviewPageInfo.class)
public class FacetedOverview extends MonolithicFacetedOverview {

    @Override
    protected String enhanceQuery(String query) {
        String result;
        if ((query != null) && !query.endsWith("*")) {
            result = query + "*";
        } else {
            result = query;
        }
        return result;
    }
    
    @Override
    protected HstQuery getHstQuery(HstRequest request) throws QueryException {
        
        HstQuery query = null;
        HippoBean contentBean = request.getRequestContext().getContentBean();
        
        if(contentBean instanceof OverviewPage){
            FacetedOverviewPageInfo parametersInfo = getComponentParametersInfo(request);
            HippoBean scope = BeanUtils.getBean(parametersInfo.getContentBeanPath(), request);
            
            if(scope instanceof HippoFacetNavigationBean){
                scope = scope.getParentBean();
                query = request.getRequestContext().getQueryManager().createQuery(scope);
                
                String highlightedUuid = ((OverviewPage )contentBean).getHighLightedItem().getIdentifier();
                
                Filter globalFilter = query.createFilter();
                globalFilter.addNotEqualTo("jcr:uuid", highlightedUuid);
                query.setFilter(globalFilter);
            }
        }
        return query;
    }
}