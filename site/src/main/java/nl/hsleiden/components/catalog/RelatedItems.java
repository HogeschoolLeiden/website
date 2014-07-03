package nl.hsleiden.components.catalog;

import hslbeans.ArticlePage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.RelatedItemsMixin;
import nl.hsleiden.componentsinfo.RelatedItemsInfo;
import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.HslUtils;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;

@ParametersInfo(type = RelatedItemsInfo.class)
public abstract class RelatedItems extends AjaxEnabledComponent {

    private static final String OVERVIEW_LINK = "overviewLink";
    private static final Logger LOG = LoggerFactory.getLogger(RelatedItems.class);

    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            RelatedItemsInfo parametersInfo = this.<RelatedItemsInfo> getComponentParametersInfo(request);
            if (parametersInfo.getUseMixin()) {
                HippoBean proxy = getMixinProxy(request.getRequestContext().getContentBean());
                if (proxy instanceof RelatedItemsMixin) {
                    parametersInfo = ((RelatedItemsMixin) proxy).getRelatedCompoundMixin();
                }
            }
            return populateModel(request, parametersInfo);
        } catch (RepositoryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    protected Map<String, Object> populateModel(HstRequest request, RelatedItemsInfo parametersInfo) {
        Map<String, Object> model = new HashMap<String, Object>();
        HippoBean contentBean = request.getRequestContext().getContentBean();
        if (contentBean instanceof ArticlePage) {
            model.put("info", parametersInfo);
            addItemsToModel(request, model, (ArticlePage) contentBean, parametersInfo);
            addOverviewLinkToModel(request, model, parametersInfo);
        }
        return model;
    }

    private void addItemsToModel(HstRequest request, Map<String, Object> model, ArticlePage contentBean,
            RelatedItemsInfo parametersInfo) {
        try {
            HstQuery query = getQuery(request, contentBean, parametersInfo);
            if (query != null) {
                LOG.debug("EXECUTING QUERY: " + query.getQueryAsString(false));
                HstQueryResult queryResult = query.execute();
                LOG.debug("QUERY RESULT SIZE: " + queryResult.getSize());
                List<HippoBean> items = getItems(queryResult);
                model.put(Attributes.ITEMS, items);
            }
        } catch (QueryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private HstQuery getQuery(HstRequest request, ArticlePage contentBean, RelatedItemsInfo parametersInfo)
            throws QueryException {

        HstQuery query = createQuery(request, parametersInfo);
        if (query != null) {
            addSorting(request, query, parametersInfo);
            query.setLimit(parametersInfo.getSize());
            addFilter(query, parametersInfo, contentBean);
        }
        return query;

    }

    protected abstract HstQuery createQuery(HstRequest request, RelatedItemsInfo parametersInfo) throws QueryException;

    private void addOverviewLinkToModel(HstRequest request, Map<String, Object> model, RelatedItemsInfo parametersInfo) {
        HippoBean overviewLink = HslUtils.getSelectedBean(request, parametersInfo.getOverviewBeanPath());
        if (parametersInfo.getShowOverview() && overviewLink != null) {
            model.put(OVERVIEW_LINK, overviewLink);
        }
    }

    private List<HippoBean> getItems(HstQueryResult queryResult) {
        List<HippoBean> items = new ArrayList<HippoBean>();
        for (HippoBeanIterator hippoBeans = queryResult.getHippoBeans(); hippoBeans.hasNext();) {
            items.add(hippoBeans.nextHippoBean());
        }
        return items;
    }

    private void addSorting(HstRequest request, HstQuery query, RelatedItemsInfo parametersInfo) {

        String sortBy = HslUtils.getNamespacedFieldName(parametersInfo.getSortBy());

        if (StringUtils.isNotBlank(sortBy)) {
            if (Constants.Values.DESCENDING.equals(parametersInfo.getSortOrder())) {
                query.addOrderByDescending(sortBy);
            } else {
                query.addOrderByAscending(sortBy);
            }
        }
    }

    private void addFilter(HstQuery query, RelatedItemsInfo info, ArticlePage contentBean) throws FilterException {
        Filter globalFilter = query.createFilter();
        if (info.getOverFilter()) {
            Filter ff = addFilterOnField(query, contentBean.getSubjecttags(), "hsl:subjecttags");
            globalFilter.addAndFilter(ff);
        }
        if (info.getThemaFilter()) {
            Filter ff = addFilterOnField(query, contentBean.getThematags(), "hsl:thematags");
            globalFilter.addAndFilter(ff);
        }
        query.setFilter(globalFilter);
    }

    private Filter addFilterOnField(HstQuery query, String[] filterValues, String fieldToFilter) throws FilterException {
        Filter f = query.createFilter();
        for (String value : filterValues) {
            Filter tagfilter = query.createFilter();
            tagfilter.addEqualTo(fieldToFilter, value);
            f.addOrFilter(tagfilter);
        }
        return f;
    }
}
