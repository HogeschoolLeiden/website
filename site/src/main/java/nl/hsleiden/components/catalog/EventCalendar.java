package nl.hsleiden.components.catalog;

import hslbeans.ArticlePage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.ArticlePageBean;
import nl.hsleiden.beans.EventPageBean;
import nl.hsleiden.beans.mixin.EventsCalendarMixin;
import nl.hsleiden.componentsinfo.EventCalendarInfo;
import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.BeanPaths;
import nl.hsleiden.utils.Constants.FieldName;
import nl.hsleiden.utils.Constants.Values;
import nl.hsleiden.utils.Constants.WidgetConstants;
import nl.hsleiden.utils.HslDateUtils;
import nl.hsleiden.utils.HslUtils;

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
import org.hippoecm.hst.core.jcr.RuntimeRepositoryException;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.linking.HstLinkCreator;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = EventCalendarInfo.class)
public class EventCalendar extends AjaxEnabledComponent {

    private static final String DATE_FORMATE_PATTERN = "yyyy-MM-dd";

    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            EventCalendarInfo paramInfo = getConfiguration(request);
            result.put(Constants.Attributes.PARAM_INFO, paramInfo);
            addEventualErrorMessages(request, result, paramInfo);
            return result;
        } catch (RepositoryException e) {
            throw new RuntimeRepositoryException(e.getMessage(), e);
        }
    }

    private void addEventualErrorMessages(HstRequest request, Map<String, Object> result, EventCalendarInfo paramInfo)
            throws RepositoryException {
        if (getScope(request) == null) {
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.nofolder.message");
        } else {
            if (!(request.getRequestContext().getContentBean() instanceof ArticlePage)
                    && (paramInfo.getOverFilter() || paramInfo.getThemaFilter())) {
                request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.nofiltering.message");
            }
        }
    }

    @Override
    public Object getJsonAjaxModel(HstRequest request, HstResponse response) {
        try {
            List<Event> result = new ArrayList<Event>();
            EventCalendarInfo paramInfo = getConfiguration(request);
            HippoBean scope = getScope(request);
            if (scope != null) {
                HstQuery query = getQuery(request, scope, paramInfo);
                HstQueryResult queryResult = query.execute();
                result = getEvents(request, queryResult);
            }
            return result;
        } catch (RepositoryException e) {
            throw new RuntimeRepositoryException(e.getMessage(), e);
        } catch (ParseException e) {
            throw new HstComponentException(e.getMessage(), e);
        } catch (QueryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private HstQuery getQuery(HstRequest request, HippoBean scope, EventCalendarInfo info) throws ParseException,
            QueryException {

        DateFormat format = new SimpleDateFormat(DATE_FORMATE_PATTERN);
        
        Date startDate = format.parse(request.getParameter("start"));
        Date endDate = format.parse(request.getParameter("end"));
        
        if(info.getFutureFilter()){
            startDate  = HslDateUtils.getStartOfDay(new Date());
        }

        HstQuery query = request.getRequestContext().getQueryManager().createQuery(scope, EventPageBean.JCR_TYPE);
        query.setLimit(Values.DEFAULT_HST_QUERY_SIZE);
        
        Filter baseFilter = getDateFilter(startDate, endDate, query);
        addTaggingFilter(baseFilter, query, request, info);

        return query;
    }

    private List<Event> getEvents(HstRequest request, HstQueryResult queryResult) {
        DateFormat format = new SimpleDateFormat(DATE_FORMATE_PATTERN);
        List<Event> result = new ArrayList<Event>();

        HstRequestContext requestContext = request.getRequestContext();
        HstLinkCreator linkCreator = requestContext.getHstLinkCreator();
        for (HippoBeanIterator hippoBeans = queryResult.getHippoBeans(); hippoBeans.hasNext();) {

            EventPageBean event = (EventPageBean) hippoBeans.nextHippoBean();

            HippoBean facetOverviewBean = BeanUtils.getBeanViaAbsolutePath(BeanPaths.EVENTS_INDEX);
            HstLink link = linkCreator.create(facetOverviewBean, requestContext);

            if (HslDateUtils.getEventDaysDuration(event) > 0) {
                for (int i = 0; i < HslDateUtils.getEventDaysDuration(event) + 1; i++) {
                    String facetLink = link.toUrlForm(requestContext, false);
                    String dayString = format.format(HslDateUtils.incrementDate(event.getEventDate().getTime(), i));
                    facetLink = facetLink + "?qd=" + dayString;
                    result.add(new Event(event.getTitle(), facetLink, dayString));
                }
            } else {
                String facetLink = link.toUrlForm(requestContext, false);
                facetLink = facetLink + "?qd=" + format.format(event.getEventDate().getTime());
                result.add(new Event(event.getTitle(), facetLink, format.format(event.getEventDate().getTime())));
            }
        }
        return result;
    }

    private HippoBean getScope(HstRequest request) throws RepositoryException {
        EventCalendarInfo configuration = getConfiguration(request);
        return BeanUtils.getBeanViaAbsolutePath(configuration.getScope());
    }

    private void addTaggingFilter(Filter baseFilter, HstQuery query, HstRequest request, EventCalendarInfo info)
            throws FilterException {
        HippoBean contentBean = request.getRequestContext().getContentBean();
        if (contentBean instanceof ArticlePageBean) {
            if (info.getOverFilter()) {
                Filter ff = HslUtils.addFilterOnField(query, ((ArticlePage) contentBean).getSubjecttags(),
                        "hsl:subjecttags");
                baseFilter.addAndFilter(ff);
            }
            if (info.getThemaFilter()) {
                Filter ff = HslUtils.addFilterOnField(query, ((ArticlePage) contentBean).getThematags(),
                        "hsl:thematags");
                baseFilter.addAndFilter(ff);
            }
        }
        query.setFilter(baseFilter);
    }

    private Filter getDateFilter(Date startDate, Date endDate, HstQuery query) throws FilterException {
        Filter baseFilter = query.createFilter();
        baseFilter.addLessOrEqualThan(FieldName.HSL_EVENT_DATE, endDate);
        baseFilter.addGreaterOrEqualThan(FieldName.HSL_EVENT_END_DATE, startDate);
        return baseFilter;
    }

    private EventCalendarInfo getConfiguration(HstRequest request) throws RepositoryException {
        EventCalendarInfo paramInfo = this.<EventCalendarInfo> getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && paramInfo.getUseMixin()
                && request.getRequestContext().getContentBean() != null) {

            HippoBean contentBean = HslUtils.getBean(request);
            HippoBean proxy = BeanUtils.getMixinProxy(contentBean);
            if (proxy instanceof EventsCalendarMixin) {
                paramInfo = ((EventsCalendarMixin) proxy).getCalendarEventsCompoundMixin();
            }
        }
        return paramInfo;
    }

    public static class Event {
        private String title;
        private String link;
        private String start;

        public Event() {
        }

        public Event(String title, String link, String start) {
            this.title = title;
            this.link = link;
            this.start = start;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

    }

}
