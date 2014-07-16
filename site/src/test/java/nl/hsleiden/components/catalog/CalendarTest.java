package nl.hsleiden.components.catalog;

import hslbeans.EventPage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import nl.hsleiden.componentsinfo.CalendarEventsInfo;
import nl.hsleiden.utils.Constants;

import org.apache.jackrabbit.value.StringValue;
import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.manager.ObjectConverterImpl;
import org.hippoecm.hst.content.beans.query.HstQueryManagerImpl;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.mock.core.component.MockHstResponse;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.hippoecm.hst.utils.ParameterUtils;
import org.junit.Assert;
import org.junit.Test;

public class CalendarTest {

    private static final String EXPECTED_QUERY = "//*[(@hippo:paths='62b8dd7a-ccf9-463a-a770-3f445f2edcef') and not(@jcr:primaryType='nt:frozenNode') and (@hsl:eventDate >= xs:dateTime('2014-06-29T00:00:00.000+02:00') and @hsl:eventDate <= xs:dateTime('2014-08-10T00:00:00.000+02:00')) and ((@jcr:primaryType='hsl:EventPage'))] order by @jcr:score descending ";
    private static final String END_DATE = "2014-08-10";
    private static final String START_DATE = "2014-06-29";
    private static final String SCOPE = "scope";

//    @Test
    public void getModel() {
        Calendar calendar = new Calendar();
        CalendarEventsInfo calendarInfo = createMockCalendarInfo(false, null);
        MockHstRequest request = getMockRequest(calendarInfo);
        Map<String, Object> model = calendar.getModel(request, new MockHstResponse());
        Assert.assertEquals(1, model.size());
        Assert.assertSame(calendarInfo, model.get(Constants.Attributes.PARAM_INFO));
    }

//    @Test
    public void getJsonAjaxModel() {
        Calendar calendar = new Calendar();
        CalendarEventsInfo calendarInfo = createMockCalendarInfo(false, SCOPE);
        MockHstRequest request = getMockRequest(calendarInfo);
        Session session = createMockSession();
        HippoBean siteContentBean = mockSiteContentBean(createMockScope(session), SCOPE);
        MockHstRequestContext requestContext = (MockHstRequestContext) request.getRequestContext();
        requestContext.setSiteContentBaseBean(siteContentBean);
        Map<String, Class<? extends HippoBean>> jcrPrimaryNodeTypeBeanPairs = new HashMap<String, Class<? extends HippoBean>>();
        jcrPrimaryNodeTypeBeanPairs.put("hsl:EventPage", EventPage.class);
        requestContext.setDefaultHstQueryManager(new HstQueryManagerImpl(session, new ObjectConverterImpl(
                jcrPrimaryNodeTypeBeanPairs, null), null));
        Object model = calendar.getJsonAjaxModel(request, new MockHstResponse());
        Assert.assertTrue(model instanceof List);
    }

    private Session createMockSession() {
        Session session = EasyMock.createMock(Session.class);
        ValueFactory valueFactory = EasyMock.createMock(ValueFactory.class);
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(format.parse(START_DATE));
            EasyMock.expect(valueFactory.createValue(calendar)).andReturn(new StringValue("2014-06-29T00:00:00.000+02:00"));
            calendar = java.util.Calendar.getInstance();
            calendar.setTime(format.parse(END_DATE));
            EasyMock.expect(valueFactory.createValue(calendar)).andReturn(new StringValue("2014-08-10T00:00:00.000+02:00"));
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
        QueryResult queryResult = EasyMock.createMock(QueryResult.class);
        NodeIterator nodeIterator = EasyMock.createNiceMock(NodeIterator.class);
        try {
            EasyMock.expect(queryResult.getNodes()).andReturn(nodeIterator);
        } catch (RepositoryException e) {
            // never going to happen
        }
        EasyMock.replay(queryResult);
        return queryResult;
    }

    private HippoBean createMockScope(Session session) {
        HippoBean scope = EasyMock.createMock(HippoBean.class);
        Node node = EasyMock.createNiceMock(Node.class);
        try {
            EasyMock.expect(node.getIdentifier()).andReturn("62b8dd7a-ccf9-463a-a770-3f445f2edcef").anyTimes();
            EasyMock.expect(node.getSession()).andReturn(session).anyTimes();
        } catch (RepositoryException e) {
            // Never going to happen
        }
        EasyMock.expect(scope.getNode()).andReturn(node).anyTimes();
        EasyMock.replay(scope, node);
        return scope;
    }

    private HippoBean mockSiteContentBean(HippoBean scope, String scopePath) {
        HippoBean siteContentBean = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(siteContentBean.getBean(scopePath)).andReturn(scope);
        EasyMock.replay(siteContentBean);
        return siteContentBean;
    }

    private MockHstRequest getMockRequest(CalendarEventsInfo calendarInfo) {
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, calendarInfo);
        MockHstRequestContext requestContext = new MockHstRequestContext();
        request.setRequestContext(requestContext);
        request.addParameter("start", START_DATE);
        request.addParameter("end", END_DATE);
        return request;
    }

    private CalendarEventsInfo createMockCalendarInfo(Boolean useMixin, String scope) {
        CalendarEventsInfo result = EasyMock.createMock(CalendarEventsInfo.class);
        EasyMock.expect(result.getUseMixin()).andReturn(useMixin).times(2);
        if (scope != null) {
            EasyMock.expect(result.getScope()).andReturn(scope);
        }
        EasyMock.replay(result);
        return result;
    }
}
