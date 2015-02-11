package nl.hsleiden.components;

import hslbeans.OverviewPage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import nl.hsleiden.utils.Constants.WidgetConstants;
import nl.hsleiden.utils.FacetsUtils;
import nl.hsleiden.utils.HslDateUtils;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.content.beans.standard.HippoResultSetBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.repository.util.DateTools.Resolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.components.MonolithicFacetedOverview;
import com.tdclighthouse.prototype.componentsinfo.FacetedOverviewPageInfo;
import com.tdclighthouse.prototype.utils.BeanUtils;
import com.tdclighthouse.prototype.utils.Constants;
import com.tdclighthouse.prototype.utils.Constants.ParametersConstants;
import com.tdclighthouse.prototype.utils.OverviewUtils;
import com.tdclighthouse.prototype.utils.PaginatorWidget;
import com.tdclighthouse.prototype.utils.SearchQueryUtils;

@ParametersInfo(type = FacetedOverviewPageInfo.class)
public class FacetedOverview extends MonolithicFacetedOverview {

    private static final String EVENTS_FACETNAV_FUTURE_PATH = "/events-facetnav-future";
    private static final String FACET_NO_FUTURE_EVENTS = "facet.no.future.events";
    private static final String FACET_SEARCH_NORESULTS = "facet.search.noresults";
    
    private static final String DATE_FORMATE_PATTERN = "yyyy-MM-dd";
    private static final Logger LOG = LoggerFactory.getLogger(FacetedOverview.class);

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
    protected void setItems(HstRequest request, Map<String, Object> model) {
        HippoFacetNavigationBean facetedNavBean = getFacetNavigationBean(request);
        if (facetedNavBean != null) {
            facetedNavBean = applyQueryToFacetBean(request, facetedNavBean);
            addItemsToModel(request, model, facetedNavBean);
        } else {
            throw new HstComponentException("Content Bean is not of the type HippoFactNavigation");
        }
    }

    private void addItemsToModel(HstRequest request, Map<String, Object> model, HippoFacetNavigationBean facetedNavBean) {
        if (facetedNavBean != null) {
            HippoResultSetBean resultSet = facetedNavBean.getResultSet();
            if(resultSet!=null){
                if(resultSet.getCount()>0){
                    populateModel(request, model, facetedNavBean, resultSet);                                        
                }else{
                    request.setAttribute(WidgetConstants.FRONT_END_MESSAGE, FACET_SEARCH_NORESULTS);
                }
            } else if(facetedNavBean.getPath().contains(EVENTS_FACETNAV_FUTURE_PATH)){
                request.setAttribute(WidgetConstants.FRONT_END_MESSAGE, FACET_NO_FUTURE_EVENTS);
            }
        } else {
            request.setAttribute(WidgetConstants.FRONT_END_MESSAGE, FACET_SEARCH_NORESULTS);
        }
    }

    private void populateModel(HstRequest request, Map<String, Object> model, HippoFacetNavigationBean facetedNavBean,
            HippoResultSetBean resultSet) {
        FacetedOverviewPageInfo parametersInfo = getComponentParametersInfo(request);
        PaginatorWidget paginatorWidget = OverviewUtils.getPaginator(request,
                OverviewUtils.getPageSize(request, parametersInfo), resultSet.getCount().intValue());
        model.put(Constants.AttributesConstants.FACET_BEAN, facetedNavBean);
        model.put(Constants.AttributesConstants.ITEMS,
                OverviewUtils.getItemsFromResultSet(resultSet, paginatorWidget));
        if (parametersInfo.getShowPaginator()) {
            request.setAttribute(Constants.AttributesConstants.PAGINATOR, paginatorWidget);
        }
    }
       
    @Override
    protected HstQuery getHstQuery(HstRequest request) throws QueryException {

        HstQuery query = null;
        HippoBean scope = null;
        HippoBean contentBean = request.getRequestContext().getContentBean();

        if (contentBean instanceof OverviewPage) {
            scope = getScopeFromParameters(request);
        }else if(contentBean instanceof HippoFacetNavigationBean){
            scope = contentBean;
        }
            
        if (scope instanceof HippoFacetNavigationBean) {
            
            String doctype = FacetsUtils.getFacetDocumentType((HippoFacetNavigationBean) scope);
            HippoBean docbase = FacetsUtils.getFacetScope((HippoFacetNavigationBean) scope);

            query = request.getRequestContext().getQueryManager().createQuery(docbase, doctype);

            Filter globalFilter = query.createFilter();

            String dayStringQuery = getPublicRequestParameter(request, "qd");

            LOG.debug("DAY String Query = " + dayStringQuery); 
            if (StringUtils.isNotBlank(dayStringQuery)) {
                applyDateFilter(globalFilter, dayStringQuery);
                query.setFilter(globalFilter);
            } else {
                applyUserQuery(request, globalFilter);
                query.setFilter(globalFilter);
            }

        }
        
        if (query != null) {
            LOG.debug("----- QUERY = " + query.getQueryAsString(true));
        }
        return query;
    }

    private HippoBean getScopeFromParameters(HstRequest request) {
        FacetedOverviewPageInfo parametersInfo = getComponentParametersInfo(request);
        return BeanUtils.getBean(parametersInfo.getContentBeanPath());
    }

    private void applyDateFilter(Filter globalFilter, String dayToFilter) throws FilterException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMATE_PATTERN);
            globalFilter.addLessOrEqualThan("hsl:eventDate", 
                    HslDateUtils.dateToCalendar(HslDateUtils.getStartOfDay(simpleDateFormat.parse(dayToFilter))), 
                                                Resolution.DAY);
            globalFilter.addGreaterOrEqualThan("hsl:eventEndDate", 
                    HslDateUtils.dateToCalendar(HslDateUtils.getStartOfDay(simpleDateFormat.parse(dayToFilter))), 
                                            Resolution.DAY);
        } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void applyUserQuery(HstRequest request, Filter globalFilter) throws FilterException {
        String queryString = getPublicRequestParameter(request, ParametersConstants.QUERY);
        if (StringUtils.isNotBlank(queryString)) {
            queryString = SearchQueryUtils.parseAndEscapeBadCharacters(enhanceQuery(queryString));
            if (StringUtils.isNotBlank(queryString)) {
                globalFilter.addContains(".", queryString);
            }
        }
    }
}