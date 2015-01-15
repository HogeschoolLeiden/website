package nl.hsleiden.components.catalog;

import hslbeans.LectoratNewsCompoundMixin;
import hslbeans.NewsPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.LectoratNewsMixin;
import nl.hsleiden.componentsinfo.LectoratNewsInfo;
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

@ParametersInfo(type = LectoratNewsInfo.class)
public class LectoratNews extends AjaxEnabledComponent {

    private static final String DOCUMENTS_FOLDER = "/content/documents";
    private static final Logger LOG = LoggerFactory.getLogger(LectoratNews.class);

    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            LectoratNewsCompoundMixin mixinInfo = null;
            LectoratNewsInfo paramInfo = this.<LectoratNewsInfo> getComponentParametersInfo(request);
            if (paramInfo.getUseMixin() != null && request.getRequestContext().getContentBean() != null
                    && paramInfo.getUseMixin()) {
                mixinInfo = getMixinConfiguration(request);
            }
            return populateModel(request, mixinInfo);
        } catch (RepositoryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private LectoratNewsCompoundMixin getMixinConfiguration(HstRequest request) throws RepositoryException {

        LectoratNewsCompoundMixin result = null;

        HippoBean contentBean = HslUtils.getBean(request);
        HippoBean proxy = BeanUtils.getMixinProxy(contentBean);
        if (proxy instanceof LectoratNewsMixin) {
            result = ((LectoratNewsMixin) proxy).getLectoratNewsCompoundMixin();
        }

        return result;
    }

    private Map<String, Object> populateModel(HstRequest request, LectoratNewsCompoundMixin mixinInfo) {
        Map<String, Object> model = new HashMap<String, Object>();
        if (mixinInfo == null) {
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.lectoratnews.nomixin");
        }else{            
            model.put("info", mixinInfo);
            addItemsToModel(request, model, mixinInfo);
        }
        return model;
    }

    private void addItemsToModel(HstRequest request, Map<String, Object> model, LectoratNewsCompoundMixin mixinInfo) {
        try {
            HstQuery query = getQuery(request, mixinInfo);
            if (query != null) {
                LOG.debug("EXECUTING QUERY: " + query.getQueryAsString(false));
                HstQueryResult queryResult = query.execute();
                LOG.debug("QUERY RESULT SIZE: " + queryResult.getSize());
                List<HippoBean> items = getItems(queryResult);
                if (!items.isEmpty()) {
                    model.put(Attributes.ITEMS, items);
                } else {
                    request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.lectoratnews.nonews");
                }
            } else {
                request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.nolectoraten.selected");
            }
        } catch (QueryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private HstQuery getQuery(HstRequest request, LectoratNewsCompoundMixin mixinInfo) throws QueryException {

        HstQuery query = createQuery(request, mixinInfo);
        if (query != null) {
            addLectoratFiltering(request, mixinInfo, query);
            addSorting(request, query, mixinInfo);
            query.setLimit(mixinInfo.getWidgetParameters().getSize().intValue());
        }
        return query;
    }

    private HstQuery createQuery(HstRequest request, LectoratNewsCompoundMixin mixinInfo) throws QueryException {
        HstQuery result = null;
        if (!mixinInfo.getContentBeanPath().isEmpty()) {
            HippoBean scope = BeanUtils.getBeanViaAbsolutePath(DOCUMENTS_FOLDER);
            result = request.getRequestContext().getQueryManager().createQuery(scope, NewsPage.JCR_TYPE);
        }
        return result;
    }

    private void addLectoratFiltering(HstRequest request, LectoratNewsCompoundMixin mixinInfo, HstQuery query)
            throws FilterException {
        Filter baseFilter = query.createFilter();
        for (HippoBean lectoratFolder : mixinInfo.getContentBeanPath()) {
            Filter lectoratFilter = query.createFilter();
            lectoratFilter.addJCRExpression("(@hippo:paths='" + lectoratFolder.getIdentifier() + "')");
            baseFilter.addOrFilter(lectoratFilter);
        }
        query.setFilter(baseFilter);
    }

    private List<HippoBean> getItems(HstQueryResult queryResult) {
        List<HippoBean> items = new ArrayList<HippoBean>();
        for (HippoBeanIterator hippoBeans = queryResult.getHippoBeans(); hippoBeans.hasNext();) {
            items.add(hippoBeans.nextHippoBean());
        }
        return items;
    }

    private void addSorting(HstRequest request, HstQuery query, LectoratNewsCompoundMixin parametersInfo) {
        String sortBy = HslUtils.getNamespacedFieldName(parametersInfo.getWidgetParameters().getSortBy());
        if (StringUtils.isNotBlank(sortBy)) {
            if (Values.DESCENDING.equals(parametersInfo.getWidgetParameters().getSortOrder())) {
                query.addOrderByDescending(sortBy);
            } else {
                query.addOrderByAscending(sortBy);
            }
        }
    }
}
