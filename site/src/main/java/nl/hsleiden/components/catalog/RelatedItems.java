package nl.hsleiden.components.catalog;

import hslbeans.ArticlePage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.RelatedItemsMixin;
import nl.hsleiden.componentsinfo.RelatedItemsInfo;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.Constants.Values;
import nl.hsleiden.utils.Constants.WidgetConstants;
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
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = RelatedItemsInfo.class)
public abstract class RelatedItems extends AjaxEnabledComponent {

    private static final String OVERVIEW_LINK = "overviewLink";
    private static final Logger LOG = LoggerFactory.getLogger(RelatedItems.class);

    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            RelatedItemsInfo parametersInfo = getConfiguration(request);
            return populateModel(request, parametersInfo);
        } catch (RepositoryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    protected RelatedItemsInfo getConfiguration(HstRequest request) throws RepositoryException {
        RelatedItemsInfo parametersInfo = this.<RelatedItemsInfo> getComponentParametersInfo(request);
        if (parametersInfo.getUseMixin() != null && parametersInfo.getUseMixin()
                && request.getRequestContext().getContentBean() != null) {

            HippoBean contentBean = HslUtils.getBean(request);
            HippoBean proxy = BeanUtils.getMixinProxy(contentBean);
            parametersInfo = getParametersFromMixin(parametersInfo, proxy);
        }
        return parametersInfo;
    }

    protected RelatedItemsInfo getParametersFromMixin(RelatedItemsInfo parametersInfo, HippoBean proxy) {
        RelatedItemsInfo result = parametersInfo;
        if (proxy instanceof RelatedItemsMixin) {
            result = ((RelatedItemsMixin) proxy).getRelatedCompoundMixin();
        }
        return result;
    }

    protected Map<String, Object> populateModel(HstRequest request, RelatedItemsInfo parametersInfo) {
        Map<String, Object> model = new HashMap<String, Object>();

        model.put("info", parametersInfo);
        addItemsToModel(request, model, parametersInfo);
        addOverviewLinkToModel(model, parametersInfo);

        return model;
    }

    protected void addItemsToModel(HstRequest request, Map<String, Object> model, RelatedItemsInfo parametersInfo) {
        try {
            HstQuery query = getQuery(request, parametersInfo);
            if (query != null && request.getAttribute(WidgetConstants.WEB_MASTER_MESSAGE) == null) {
                LOG.debug("EXECUTING QUERY: " + query.getQueryAsString(false));
                HstQueryResult queryResult = query.execute();
                LOG.debug("QUERY RESULT SIZE: " + queryResult.getTotalSize());
                List<HippoBean> items = getItems(queryResult);
                if (!items.isEmpty()) {
                    model.put(Attributes.ITEMS, items);
                } else {
                    request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.noitems.message");
                }
            }
        } catch (QueryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    protected HstQuery getQuery(HstRequest request, RelatedItemsInfo parametersInfo) throws QueryException {

        HstQuery query = createQuery(request, parametersInfo);
        if (query != null) {
            addSorting(query, parametersInfo);
            query.setLimit(parametersInfo.getSize());
            addFilter(query, parametersInfo, request);
        }
        return query;

    }

    protected abstract HstQuery createQuery(HstRequest request, RelatedItemsInfo parametersInfo) throws QueryException;

    protected void addOverviewLinkToModel(Map<String, Object> model, RelatedItemsInfo parametersInfo) {
        HippoBean overviewLink = BeanUtils.getBeanViaAbsolutePath(parametersInfo.getOverviewBeanPath());
        if (parametersInfo.getShowOverview() && overviewLink != null) {
            model.put(OVERVIEW_LINK, overviewLink);
        }
    }

    protected List<HippoBean> getItems(HstQueryResult queryResult) {
        List<HippoBean> items = new ArrayList<HippoBean>();
        for (HippoBeanIterator hippoBeans = queryResult.getHippoBeans(); hippoBeans.hasNext();) {
            items.add(hippoBeans.nextHippoBean());
        }
        return items;
    }

    protected void addSorting(HstQuery query, RelatedItemsInfo parametersInfo) {

        String sortBy = HslUtils.getNamespacedFieldName(parametersInfo.getSortBy());

        if (StringUtils.isNotBlank(sortBy)) {
            if (Values.DESCENDING.equals(parametersInfo.getSortOrder())) {
                query.addOrderByDescending(sortBy);
            } else {
                query.addOrderByAscending(sortBy);
            }
        }
    }

    protected void addFilter(HstQuery query, RelatedItemsInfo info, HstRequest request) throws FilterException {
        HippoBean contentBean = request.getRequestContext().getContentBean();
        if (info.getOverFilter() || info.getThemaFilter()) {
            if (contentBean instanceof ArticlePage) {
                addFiltering(query, info, (ArticlePage) contentBean);
            } else {
                request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.nofiltering.message");
            }
        }
    }

    protected void addFiltering(HstQuery query, RelatedItemsInfo info, ArticlePage contentBean) throws FilterException {

        Filter globalFilter = query.createFilter();
        if (info.getOverFilter()) {
            Filter ff = HslUtils.addFilterOnField(query, contentBean.getSubjecttags(), "hsl:subjecttags");
            globalFilter.addAndFilter(ff);
        }
        if (info.getThemaFilter()) {
            Filter ff = HslUtils.addFilterOnField(query, contentBean.getThematags(), "hsl:thematags");
            globalFilter.addAndFilter(ff);
        }
        query.setFilter(globalFilter);
    }

}
