package nl.hsleiden.components.catalog;

import hslbeans.ArticlePage;
import hslbeans.NewsPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.RelatedItemsMixin;
import nl.hsleiden.componentsinfo.RelatedINewsInfo;
import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.HslUtils;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
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

@ParametersInfo(type = RelatedINewsInfo.class)
public class RelatedNews extends AjaxEnabledComponent<Map<String, Object>> {

    private static final String OVERVIEW_LINK = "overviewLink";
    private static final Logger LOG = LoggerFactory.getLogger(RelatedNews.class);

    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            Map<String, Object> model = new HashMap<String, Object>();
            RelatedINewsInfo parametersInfo = this.<RelatedINewsInfo> getComponentParametersInfo(request);
            if (parametersInfo.getUseMixin()) {
                HippoBean proxy = getMixinProxy(request.getRequestContext().getContentBean());
                if (proxy instanceof RelatedItemsMixin) {
                    parametersInfo = ((RelatedItemsMixin) proxy).getRelatedCompoundMixin();
                }
            }
            model = populateModel(request, parametersInfo);
            return model;
        } catch (RepositoryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private Map<String, Object> populateModel(HstRequest request, RelatedINewsInfo parametersInfo) {
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
            RelatedINewsInfo parametersInfo) {
        try {
            HstQuery query = getQuery(request, contentBean, parametersInfo);
            HstQueryResult queryResult = query.execute();
            List<HippoBean> items = getItems(queryResult);
            model.put(Attributes.ITEMS, items);
        } catch (QueryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private HstQuery getQuery(HstRequest request, ArticlePage contentBean, RelatedINewsInfo parametersInfo)
            throws QueryException {

        HstQuery query = createQuery(request, parametersInfo);
        addSorting(request, query, parametersInfo);
        query.setLimit(parametersInfo.getSize());
        addFilter(query, parametersInfo, contentBean);
        return query;

    }

    private HstQuery createQuery(HstRequest request, RelatedINewsInfo parametersInfo) throws QueryException {
        try {
            String contentBeanPath = parametersInfo.getContentBeanPath();
            LOG.warn("content bean path = " + contentBeanPath);
            HippoBean scope = (HippoBean) request.getRequestContext().getObjectBeanManager().getObject(contentBeanPath);
            HstQuery query = request.getRequestContext().getQueryManager().createQuery(scope, NewsPage.JCR_TYPE);
            return query;
        } catch (ObjectBeanManagerException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private void addOverviewLinkToModel(HstRequest request, Map<String, Object> model, RelatedINewsInfo parametersInfo) {
        HippoBean overviewLink = getOverviewPageBean(request, parametersInfo);
        if (parametersInfo.getShowOverview() && overviewLink != null) {
            model.put(OVERVIEW_LINK, overviewLink);
        }
    }

    private HippoBean getOverviewPageBean(HstRequest request, RelatedINewsInfo parametersInfo) {
        try {
            String contentBeanPath = parametersInfo.getOverviewBeanPath();
            LOG.warn("content bean path = " + contentBeanPath);
            HippoBean overviewLink = (HippoBean) request.getRequestContext().getObjectBeanManager().getObject(contentBeanPath);
            return overviewLink;
        } catch (ObjectBeanManagerException e) {
            LOG.error("Object Manager Exception");
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private List<HippoBean> getItems(HstQueryResult queryResult) {
        List<HippoBean> items = new ArrayList<HippoBean>();
        for (HippoBeanIterator hippoBeans = queryResult.getHippoBeans(); hippoBeans.hasNext();) {
            items.add(hippoBeans.nextHippoBean());
        }
        return items;
    }

    private void addSorting(HstRequest request, HstQuery query, RelatedINewsInfo parametersInfo) {

        String sortBy = HslUtils.getNamespacedFieldName(parametersInfo.getSortBy());

        if (StringUtils.isNotBlank(sortBy)) {
            if (Constants.Values.DESCENDING.equals(parametersInfo.getSortOrder())) {
                query.addOrderByDescending(sortBy);
            } else {
                query.addOrderByAscending(sortBy);
            }
        }
    }

    private void addFilter(HstQuery query, RelatedINewsInfo info, ArticlePage contentBean) throws FilterException {
        Filter globalFilter = query.createFilter();
        if (info.getOverFilter()) {
            addOverFilter(query, globalFilter, contentBean.getSubjecttags());
        }
        if (info.getThemaFilter()) {
            addThemaFilter(query, globalFilter, contentBean.getThematags());
        }
        query.setFilter(globalFilter);
    }

    private void addThemaFilter(HstQuery query, Filter globalFilter, String[] themaTags) throws FilterException {
        Filter f = query.createFilter();
        for (String themaTag : themaTags) {
            Filter themafilter = query.createFilter();
            themafilter.addEqualTo("hsl:thematags", themaTag);
            f.addOrFilter(themafilter);
        }
        globalFilter.addAndFilter(f);
    }

    private void addOverFilter(HstQuery query, Filter globalFilter, String[] subjectTags) throws FilterException {
        Filter f = query.createFilter();
        for (String subjectTag : subjectTags) {
            Filter tagfilter = query.createFilter();
            tagfilter.addEqualTo("hsl:subjecttags", subjectTag);
            f.addOrFilter(tagfilter);
        }
        globalFilter.addAndFilter(f);
    }
}
