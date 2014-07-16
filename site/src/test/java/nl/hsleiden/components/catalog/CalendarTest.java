package nl.hsleiden.components.catalog;

import hslbeans.EventPage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import nl.hsleiden.components.catalog.Calendar.Event;
import nl.hsleiden.componentsinfo.CalendarInfo;
import nl.hsleiden.utils.Constants;
import nl.openweb.jcr.mock.MockNode;
import nl.openweb.jcr.mock.MockNodeIterator;
import nl.openweb.jcr.mock.MockProperty;

import org.apache.jackrabbit.value.StringValue;
import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.manager.ObjectConverterImpl;
import org.hippoecm.hst.content.beans.query.HstQueryManagerImpl;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.jcr.RuntimeRepositoryException;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.linking.HstLinkCreator;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.mock.core.component.MockHstResponse;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.hippoecm.hst.utils.ParameterUtils;
import org.junit.Assert;
import org.junit.Test;

public class CalendarTest {

    private static final String LINK_TO_EVENT = "/link/to/event";
    private static final String EVENT_PAGE_TYPE = "hsl:EventPage";
    private static final String EXPECTED_QUERY = "//*[(@hippo:paths='62b8dd7a-ccf9-463a-a770-3f445f2edcef') and not(@jcr:primaryType='nt:frozenNode') and (@hsl:eventDate >= xs:dateTime('2014-06-29T00:00:00.000+02:00') and @hsl:eventDate <= xs:dateTime('2014-08-10T00:00:00.000+02:00')) and ((@jcr:primaryType='hsl:EventPage'))] order by @jcr:score descending ";
    private static final String END_DATE = "2014-08-10";
    private static final String START_DATE = "2014-06-29";
    private static final String SCOPE = "scope";

    @Test
    public void getModel() {
        Calendar calendar = new Calendar();
        CalendarInfo calendarInfo = createMockCalendarInfo(false, null);
        MockHstRequest request = getMockRequest(calendarInfo);
        Map<String, Object> model = calendar.getModel(request, new MockHstResponse());
        Assert.assertEquals(1, model.size());
        Assert.assertSame(calendarInfo, model.get(Constants.Attributes.PARAM_INFO));
    }

    @Test
    public void getJsonAjaxModel() {
        Calendar calendar = new Calendar();
        CalendarInfo calendarInfo = createMockCalendarInfo(false, SCOPE);
        MockHstRequest request = getMockRequest(calendarInfo);
        Session session = createMockSession();
        HippoBean siteContentBean = mockSiteContentBean(createMockScope(session), SCOPE);
        MockHstRequestContext requestContext = (MockHstRequestContext) request.getRequestContext();
        requestContext.setSiteContentBaseBean(siteContentBean);
        Map<String, Class<? extends HippoBean>> jcrPrimaryNodeTypeBeanPairs = new HashMap<String, Class<? extends HippoBean>>();
        jcrPrimaryNodeTypeBeanPairs.put(EVENT_PAGE_TYPE, EventPage.class);
        requestContext.setDefaultHstQueryManager(new HstQueryManagerImpl(session, new ObjectConverterImpl(
                jcrPrimaryNodeTypeBeanPairs, null), null));
        @SuppressWarnings("unchecked")
        List<Event> model = (List<Event>) calendar.getJsonAjaxModel(request, new MockHstResponse());
        Assert.assertEquals(2, model.size());
        Event event = model.get(0);
        Assert.assertEquals("event-1", event.getTitle());
        Assert.assertEquals("2014-07-16", event.getStart());
        Assert.assertEquals(LINK_TO_EVENT, event.getLink());

        event = model.get(1);
        Assert.assertEquals("event-2", event.getTitle());
        Assert.assertEquals("2014-07-17", event.getStart());
        Assert.assertEquals(LINK_TO_EVENT, event.getLink());
    }

    private Session createMockSession() {
        Session session = EasyMock.createMock(Session.class);
        ValueFactory valueFactory = EasyMock.createMock(ValueFactory.class);
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(format.parse(START_DATE));
            EasyMock.expect(valueFactory.createValue(calendar)).andReturn(
                    new StringValue("2014-06-29T00:00:00.000+02:00"));
            calendar = java.util.Calendar.getInstance();
            calendar.setTime(format.parse(END_DATE));
            EasyMock.expect(valueFactory.createValue(calendar)).andReturn(
                    new StringValue("2014-08-10T00:00:00.000+02:00"));
            EasyMock.expect(session.getValueFactory()).andReturn(valueFactory).times(2);
            Workspace workspace = EasyMock.createNiceMock(Workspace.class);
            QueryManager queryManager = EasyMock.createMock(QueryManager.class);
            Query query = EasyMock.createNiceMock(Query.class);
            EasyMock.expect(query.execute()).andReturn(getMockQueryResult());
            EasyMock.expect(workspace.getQueryManager()).andReturn(queryManager);
            EasyMock.expect(session.getWorkspace()).andReturn(workspace).times(2);
            EasyMock.expect(queryManager.createQuery(EXPECTED_QUERY, "xpath")).andReturn(query);
            EasyMock.replay(workspace, queryManager, query);

        } catch (RepositoryException e) {
            // never going to happen
        } catch (ParseException e) {
            // never going to happen
        }
        EasyMock.replay(session, valueFactory);
        return session;
    }

    private QueryResult getMockQueryResult() {
        try {
            QueryResult queryResult = EasyMock.createMock(QueryResult.class);
            List<Node> nodes = new ArrayList<Node>();
            nodes.add(createMockEventPageNode("event-1", new GregorianCalendar(2014, 6, 16)));
            nodes.add(createMockEventPageNode("event-2", new GregorianCalendar(2014, 6, 17)));
            EasyMock.expect(queryResult.getNodes()).andReturn(new MockNodeIterator(nodes));
            EasyMock.replay(queryResult);
            return queryResult;
        } catch (RepositoryException e) {
            // never going to happen
            throw new RuntimeRepositoryException(e.getMessage(), e);
        }
    }

    private MockNode createMockEventPageNode(String name, java.util.Calendar date) {
        MockNode result = new MockNode(name, EVENT_PAGE_TYPE);
        result.addProperty(new MockProperty("hsl:title", name, result));
        result.addProperty(new MockProperty("hsl:eventDate", date, result));
        return result;
    }

    private HippoBean createMockScope(Session session) {
        HippoBean scope = EasyMock.createMock(HippoBean.class);
        MockNode node = new MockNode(session);
        node.setIdentifier("62b8dd7a-ccf9-463a-a770-3f445f2edcef");
        EasyMock.expect(scope.getNode()).andReturn(node).anyTimes();
        EasyMock.replay(scope);
        return scope;
    }

    private HippoBean mockSiteContentBean(HippoBean scope, String scopePath) {
        HippoBean siteContentBean = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(siteContentBean.getBean(scopePath)).andReturn(scope);
        EasyMock.replay(siteContentBean);
        return siteContentBean;
    }

    private MockHstRequest getMockRequest(CalendarInfo calendarInfo) {
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, calendarInfo);
        MockHstRequestContext requestContext = new MockHstRequestContext();

        requestContext.setLinkCreator(createMockLinkCreator());
        request.setRequestContext(requestContext);
        request.addParameter("start", START_DATE);
        request.addParameter("end", END_DATE);
        return request;
    }

    private HstLinkCreator createMockLinkCreator() {
        HstLinkCreator linkCreator = EasyMock.createMock(HstLinkCreator.class);
        EasyMock.expect(
                linkCreator.create(EasyMock.anyObject(EventPage.class), EasyMock.anyObject(HstRequestContext.class)))
                .andReturn(createMockHstLink("/my/link/")).anyTimes();
        EasyMock.replay(linkCreator);
        return linkCreator;
    }

    private HstLink createMockHstLink(String string) {
        HstLink createMock = EasyMock.createMock(HstLink.class);
        EasyMock.expect(createMock.toUrlForm(EasyMock.anyObject(HstRequestContext.class), EasyMock.anyBoolean()))
                .andReturn(LINK_TO_EVENT).anyTimes();
        EasyMock.replay(createMock);
        return createMock;
    }

    private CalendarInfo createMockCalendarInfo(Boolean useMixin, String scope) {
        CalendarInfo result = EasyMock.createMock(CalendarInfo.class);
        EasyMock.expect(result.getUseMixin()).andReturn(useMixin).times(2);
        if (scope != null) {
            EasyMock.expect(result.getScope()).andReturn(scope);
        }
        EasyMock.replay(result);
        return result;
    }
}
