package nl.hsleiden.components.catalog;

import java.util.Date;

import nl.hsleiden.beans.EventPageBean;
import nl.hsleiden.beans.mixin.RelatedEventsMixin;
import nl.hsleiden.componentsinfo.RelatedEventsInfo;
import nl.hsleiden.componentsinfo.RelatedItemsInfo;
import nl.hsleiden.utils.Constants.FieldName;
import nl.hsleiden.utils.Constants.WidgetConstants;
import nl.hsleiden.utils.HslDateUtils;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = RelatedEventsInfo.class)
public class RelatedEvents extends RelatedItems {

    @Override
    protected HstQuery createQuery(HstRequest request, RelatedItemsInfo parametersInfo) throws QueryException {

        HstQuery result = null;
        HippoBean scope = BeanUtils.getBeanViaAbsolutePath(parametersInfo.getContentBeanPath());
        if (scope != null) {
            result = request.getRequestContext().getQueryManager().createQuery(scope, EventPageBean.JCR_TYPE);
        } else {
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.nofolder.message");
        }
        return result;
    }

    @Override
    protected RelatedItemsInfo getParametersFromMixin(RelatedItemsInfo parametersInfo, HippoBean proxy) {
        RelatedItemsInfo result = parametersInfo;
        if (proxy instanceof RelatedEventsMixin) {
            result = ((RelatedEventsMixin) proxy).getRelatedEventsCompoundMixin();
        }
        return result;
    }

    @Override
    protected void addFilter(HstQuery query, RelatedItemsInfo info, HstRequest request) throws FilterException {
        super.addFilter(query, info, request);
        addDateFiltering(query, info);
    }

    private void addDateFiltering(HstQuery query, RelatedItemsInfo info) throws FilterException {
        if (info.getFutureFilter()) {
            if (query.getFilter() != null) {
                addFutureFilter(query, (Filter) query.getFilter());
            } else {
                addFutureFilter(query);
            }
        }
    }

    private void addFutureFilter(HstQuery query) throws FilterException {

        Filter baseFilter = query.createFilter();
        baseFilter.addGreaterOrEqualThan(FieldName.HSL_EVENT_END_DATE, HslDateUtils.getStartOfDay(new Date()));
        query.setFilter(baseFilter);
    }

    private void addFutureFilter(HstQuery query, Filter baseFilter) throws FilterException {

        baseFilter.addGreaterOrEqualThan(FieldName.HSL_EVENT_END_DATE, HslDateUtils.getStartOfDay(new Date()));
        query.setFilter(baseFilter);
    }
}
