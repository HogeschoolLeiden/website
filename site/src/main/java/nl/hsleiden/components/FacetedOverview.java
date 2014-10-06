package nl.hsleiden.components;

import java.util.Map;

import hslbeans.OverviewPage;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoResultSetBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.components.MonolithicFacetedOverview;
import com.tdclighthouse.prototype.componentsinfo.FacetedOverviewPageInfo;
import com.tdclighthouse.prototype.utils.BeanUtils;
import com.tdclighthouse.prototype.utils.Constants;
import com.tdclighthouse.prototype.utils.OverviewUtils;
import com.tdclighthouse.prototype.utils.PaginatorWidget;
import com.tdclighthouse.prototype.utils.Constants.ParametersConstants;
import com.tdclighthouse.prototype.utils.SearchQueryUtils;

@ParametersInfo(type = FacetedOverviewPageInfo.class)
public class FacetedOverview extends MonolithicFacetedOverview {

    //the * has been temporarily disabled
    @Override
    protected String enhanceQuery(String query) {
        return query;
    }
    
    @Override
    protected void setItems(HstRequest request, Map<String, Object> model) {
        HippoFacetNavigationBean facetedNavBean = getFacetNavigationBean(request);
        if (facetedNavBean != null) {
            facetedNavBean = applyQueryToFacetBean(request, facetedNavBean);
            if(facetedNavBean != null){                
                HippoResultSetBean resultSet = facetedNavBean.getResultSet();
                FacetedOverviewPageInfo parametersInfo = getComponentParametersInfo(request);
                PaginatorWidget paginatorWidget = OverviewUtils.getPaginator(request, OverviewUtils.getPageSize(request, parametersInfo), resultSet.getCount()
                        .intValue());
                model.put(Constants.AttributesConstants.FACET_BEAN, facetedNavBean);
                model.put(Constants.AttributesConstants.ITEMS, OverviewUtils.getItemsFromResultSet(resultSet, paginatorWidget));
                if (parametersInfo.getShowPaginator()) {
                    request.setAttribute(Constants.AttributesConstants.PAGINATOR, paginatorWidget);
                }
            }else{
                request.setAttribute(WidgetConstants.FRONT_END_MESSAGE, "facet.search.noresults");
            }
        } else {
            throw new HstComponentException("content Bean is not of the type HippoFactNavigation");
        }
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
                
                Filter globalFilter = query.createFilter();

                if(((OverviewPage )contentBean).getHighLightedItem()!=null){
                    String highlightedUuid = ((OverviewPage )contentBean).getHighLightedItem().getIdentifier();
                    globalFilter.addNotEqualTo("jcr:uuid", highlightedUuid);
                }
                
                String queryString = getPublicRequestParameter(request, ParametersConstants.QUERY);
                if (StringUtils.isNotBlank(queryString)) {
                    queryString = SearchQueryUtils.parseAndEscapeBadCharacters(enhanceQuery(queryString));
                    if (StringUtils.isNotBlank(queryString)) {
                        globalFilter.addContains(".", queryString);
                    }
                }
                query.setFilter(globalFilter);
            }
        }
        
        return query;
    }    
}