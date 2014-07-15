package nl.hsleiden.components.catalog;

import hslbeans.EventPage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.CalendarMixin;
import nl.hsleiden.componentsinfo.CalendarInfo;
import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
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

@ParametersInfo(type = CalendarInfo.class)
public class Calendar extends AjaxEnabledComponent {

    private static final String DATE_FORMATE_PATTERN = "yyyy-MM-dd";

    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            CalendarInfo paramInfo = getConfiguration(request);
            result.put(Constants.Attributes.PARAM_INFO, paramInfo);
            return result;
        } catch (RepositoryException e) {
            throw new RuntimeRepositoryException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getJsonAjaxModel(HstRequest request, HstResponse response) {
        try {
            List<Event> result = new ArrayList<Event>();
            HstRequestContext requestContext = request.getRequestContext();
            CalendarInfo configuration = getConfiguration(request);
            HippoBean scope = BeanUtils.getBean(configuration.getScope(), request);
            DateFormat format = new SimpleDateFormat(DATE_FORMATE_PATTERN);
            Date startDate = format.parse(request.getParameter("start"));
            Date endDate = format.parse(request.getParameter("end"));
            HstQuery query = requestContext.getQueryManager().createQuery(scope, EventPage.class);
            Filter baseFilter = query.createFilter();
            baseFilter.addGreaterOrEqualThan("hsl:eventDate", startDate);
            baseFilter.addLessOrEqualThan("hsl:eventDate", endDate);
            query.setFilter(baseFilter);
            HstQueryResult queryResult = query.execute();

            HstLinkCreator linkCreator = requestContext.getHstLinkCreator();
            for (HippoBeanIterator hippoBeans = queryResult.getHippoBeans(); hippoBeans.hasNext();) {
                EventPage event = (EventPage) hippoBeans.next();
                HstLink link = linkCreator.create(event, requestContext);
                result.add(new Event(event.getTitle(), link.toUrlForm(requestContext, false), format.format(event.getEventDate().getTime())));
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

    private CalendarInfo getConfiguration(HstRequest request) throws RepositoryException {
        CalendarInfo paramInfo = getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && paramInfo.getUseMixin()) {
            HippoBean proxy;
            proxy = BeanUtils.getMixinProxy(request.getRequestContext().getContentBean());
            if (proxy instanceof CalendarMixin) {
                paramInfo = (CalendarInfo) proxy;
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
