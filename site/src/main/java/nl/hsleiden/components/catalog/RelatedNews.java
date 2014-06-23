package nl.hsleiden.components.catalog;

import hslbeans.NewsPage;
import hslbeans.RelatedOverviewParameters;
import hslbeans.RelatedSortParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.compounds.RelatedWidgetParametersBean;
import nl.hsleiden.beans.mixin.RelatedItemsMixin;
import nl.hsleiden.componentsinfo.RelatedItemsInfo;
import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.HslUtils;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;
import com.tdclighthouse.prototype.utils.ComponentUtils;

@ParametersInfo(type = RelatedItemsInfo.class)
public class RelatedNews extends AjaxEnabledComponent<Map<String, Object>> {

    private static final String OVERVIEW_LINK = "overviewLink";
    private static final Logger LOG = LoggerFactory.getLogger(RelatedNews.class);

    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        RelatedItemsInfo parametersInfo = this.<RelatedItemsInfo> getComponentParametersInfo(request);
        if (parametersInfo.getUseMixin()) {
            populateModelFromMixin(request, model);
        } else {
            populateModelFromParametersOrMixin(request, model);
        }
        return model;
    }

    private void populateModelFromParametersOrMixin(HstRequest request, Map<String, Object> model) {
        try {
            HippoBean proxy = getMixinProxy(request.getRequestContext().getContentBean());
            if (proxy instanceof RelatedItemsMixin) {
                populateModelFromMixin(request, model);
            } else {
                populateModelFromParametrs(request, model);
            }
        } catch (RepositoryException e) {
            LOG.error("mixin not available", e);
        }
    }

    private void populateModelFromParametrs(HstRequest request, Map<String, Object> model) {
        try {
            addGenericInfoToModel(request, model);
            addItemsToModel(request, model);
            addOverviewLinkToModel(request, model);
        } catch (RepositoryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private void populateModelFromMixin(HstRequest request, Map<String, Object> model) {
        try {
            HippoBean proxy = getMixinProxy(request.getRequestContext().getContentBean());
            if (proxy instanceof RelatedItemsMixin) {
                model.put("info", ((RelatedItemsMixin) proxy).getRelatedCompoundMixin().getConfigObject());
                addItemsFromMixin(request, model, proxy);
                addOverviewLinkFromMixin(request, model, proxy);
            } else {
                model.put(Attributes.WEBMASTER_ERROR_MESSAGE, "why was it a constant ??");
            }
        } catch (RepositoryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private void addGenericInfoToModel(HstRequest request, Map<String, Object> model) throws RepositoryException {
        RelatedItemsInfo parametersInfo = this.<RelatedItemsInfo> getComponentParametersInfo(request);
        model.put("info", parametersInfo);
    }

    private void addItemsToModel(HstRequest request, Map<String, Object> model) {
        try {
            HstQuery query = getQuery(request);
            HstQueryResult queryResult = query.execute();
            List<HippoBean> items = getItems(queryResult);
            model.put(Attributes.ITEMS, items);
        } catch (QueryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    protected HstQuery getQuery(HstRequest request) throws QueryException {
        RelatedItemsInfo parametersInfo = getComponentParametersInfo(request);
        HippoBean scope = getBean(parametersInfo.getContentBeanPath(), request);
        HstQuery query = request.getRequestContext().getQueryManager().createQuery(scope, NewsPage.JCR_TYPE);

        addSorting(request, query);
        query.setLimit(parametersInfo.getSize());
        addFilter(query);
        return query;
    }

    @Override
    public int getPageSize(HstRequest request) {
        RelatedItemsInfo parametersInfo = getComponentParametersInfo(request);
        int result = parametersInfo.getSize();
        result = retrieveComponentSpecificParameter(request, result);
        return result;
    }

    private int retrieveComponentSpecificParameter(HstRequest request, int result) {
        String pageSzieString = getComponentSpecificParameter(request, Constants.Parameters.PAGE_SIZE);
        if (StringUtils.isNotBlank(pageSzieString) && StringUtils.isNumeric(pageSzieString)) {
            result = Integer.parseInt(pageSzieString);
        }
        return result;
    }

    protected String getComponentSpecificParameter(HstRequest request, String name) {
        return getPublicRequestParameter(request, ComponentUtils.getComponentSpecificParameterName(request, name));
    }

    private void addOverviewLinkToModel(HstRequest request, Map<String, Object> model) {
        RelatedItemsInfo parametersInfo = getComponentParametersInfo(request);
        HippoBean overviewLink = getOverviewPageBean(request);
        if (parametersInfo.getShowOverviewLink() && overviewLink != null) {
            model.put(OVERVIEW_LINK, overviewLink);
        }
    }

    private HippoBean getOverviewPageBean(HstRequest request) {
        HippoBean overviewLink = null;
        RelatedItemsInfo parametersInfo = getComponentParametersInfo(request);
        if (parametersInfo.getOverviewBeanPath() != null && !parametersInfo.getOverviewBeanPath().isEmpty()) {
            overviewLink = getBean(parametersInfo.getOverviewBeanPath(), request);
        }
        return overviewLink;
    }

    private List<HippoBean> getItems(HstQueryResult queryResult) {
        List<HippoBean> items = new ArrayList<HippoBean>();
        for (HippoBeanIterator hippoBeans = queryResult.getHippoBeans(); hippoBeans.hasNext();) {
            items.add(hippoBeans.nextHippoBean());
        }
        return items;
    }

    private void addSorting(HstRequest request, HstQuery query) {
        RelatedItemsInfo parametersInfo = getComponentParametersInfo(request);

        String sortBy = HslUtils.getNamespacedFieldName(parametersInfo.getSortBy());

        if (StringUtils.isNotBlank(sortBy)) {
            if (Constants.Values.DESCENDING.equals(parametersInfo.getSortOrder())) {
                query.addOrderByDescending(sortBy);
            } else {
                query.addOrderByAscending(sortBy);
            }
        }
    }

    @Override
    public int getPageNumber(HstRequest request) {
        int result = 1;
        String pageString = getComponentSpecificParameter(request, Constants.Parameters.PAGE);
        if (StringUtils.isNotBlank(pageString) && StringUtils.isNumeric(pageString)) {
            result = Integer.parseInt(pageString);
        }
        return result;
    }

    // TODO: override if want to filter the selected catalogue items on some
    // field value
    protected void addFilter(HstQuery query) {
    }

    private void addItemsFromMixin(HstRequest request, Map<String, Object> model, HippoBean proxy)
            throws RepositoryException {
        try {
            HstQuery query = getQueryFromMixin(request, model, proxy);
            HstQueryResult queryResult = query.execute();
            List<HippoBean> items = getItems(queryResult);
            model.put(Attributes.ITEMS, items);
        } catch (QueryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private HstQuery getQueryFromMixin(HstRequest request, Map<String, Object> model, HippoBean proxy)
            throws QueryException, RepositoryException {
        RelatedWidgetParametersBean params = ((RelatedItemsMixin) proxy).getRelatedCompoundMixin()
                .getWidgetParameters();
        HippoBean scope = params.getContentBeanPath();
        HstQuery query = request.getRequestContext().getQueryManager().createQuery(scope, NewsPage.JCR_TYPE);
        addSortingFromMixin(request, query, proxy);
        query.setLimit(getPageSizeFromMixin(request, proxy));
        addFilter(query);
        return query;
    }

    private void addSortingFromMixin(HstRequest request, HstQuery query, HippoBean proxy) {
        RelatedSortParameters params = ((RelatedItemsMixin) proxy).getRelatedCompoundMixin().getSortParameters();
        String sortBy = HslUtils.getNamespacedFieldName(params.getSortBy());
        if (StringUtils.isNotBlank(sortBy)) {
            if (Constants.Values.DESCENDING.equals(params.getSortOrder())) {
                query.addOrderByDescending(sortBy);
            } else {
                query.addOrderByAscending(sortBy);
            }
        }
    }

    private int getPageSizeFromMixin(HstRequest request, HippoBean proxy) {
        RelatedWidgetParametersBean params = ((RelatedItemsMixin) proxy).getRelatedCompoundMixin()
                .getWidgetParameters();
        int result = params.getSize().intValue();
        result = retrieveComponentSpecificParameter(request, result);
        return result;
    }

    private void addOverviewLinkFromMixin(HstRequest request, Map<String, Object> model, HippoBean proxy) {
        HippoBean overviewLink = getOverviewPageBeanFromMixin(request, proxy);
        RelatedOverviewParameters params = ((RelatedItemsMixin) proxy).getRelatedCompoundMixin()
                .getOverviewParameters();
        if (params.getShowOverview() && overviewLink != null) {
            model.put(OVERVIEW_LINK, overviewLink);
        }
    }

    private HippoBean getOverviewPageBeanFromMixin(HstRequest request, HippoBean proxy) {
        HippoBean overviewLink = null;
        RelatedOverviewParameters params = ((RelatedItemsMixin) proxy).getRelatedCompoundMixin()
                .getOverviewParameters();
        if (params.getOverviewBeanPath() != null) {
            overviewLink = params.getOverviewBeanPath();
        }
        return overviewLink;
    }
}
