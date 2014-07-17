package nl.hsleiden.components.catalog;

import hslbeans.ArticlePage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFormatException;

import nl.hsleiden.componentsinfo.RelatedEventsInfo;
import nl.hsleiden.componentsinfo.RelatedItemsInfo;
import nl.hsleiden.utils.TestUtils;
import nl.openweb.jcr.mock.MockNode;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.manager.ObjectBeanManager;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryManager;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.mock.content.beans.standard.MockHippoBeanIterator;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.utils.ParameterUtils;
import org.junit.Assert;
import org.junit.Test;

public class RelatedItemsTest {

    protected static final String HSL_NEWSPAGE_TYPE = "hsl:NewsPage";
    protected static final String HSL_EVENTPAGE_TYPE = "hsl:EventPage";

    protected static final String QUERY_EXCEPTION = "query exception";

    protected static final String OVERVIEW_LINK_LABEL = "notImportant";
    protected static final String WIDGET_TITLE = "widgetTitle";

    protected static final String SORT_FIELD_RELEASE_DATE = "hsl:releaseDate";
    protected static final String SORT_ORDER_ASCENDING = "ascending";
    protected static final String SORT_ORDER_DESCENDING = "descending";

    protected static final String CONTENT_BEAN_PATH = "/content/bean/path";
    protected static final String OVERVIEW_BEAN_PATH = "/overview/bean/path";
    protected static final int SIZE = 2;

    protected static final String FILTER_VALUE_OVER_1 = "over-1";
    protected static final String FILTER_VALUE_THEMA_1 = "thema-1";
    protected static final String FILTER_FIELD_THEMA_TAGS = "hsl:thematags";
    protected static final String FILTER_FIELD_SUBJECT_TAGS = "hsl:subjecttags";

    @Test
    public void getModelTestUseMixinFalseNoFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, false, false, WIDGET_TITLE,
                SORT_ORDER_ASCENDING, SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH,
                CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedNews();
        doTestVariantOne(myInfoMock, relatedItems, false);
    }

    @Test
    public void getModelTestUseMixinTrueWithNotMixinNoFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(true, false, false, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedNews();
        doTestVariantOne(myInfoMock, relatedItems, false);
    }

    @Test
    public void getModelTestUseMixinTrueWithMixinNoFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(true, false, false, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedNews();
        doTestVariantOne(myInfoMock, relatedItems, true);
    }

    @Test
    public void getModelTestUseMixinFalseOverFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, false, true, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedNews();
        doTestVariantOne(myInfoMock, relatedItems, false);
    }

    @Test
    public void getModelTestUseMixinFalseThemaFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedNews();
        doTestVariantOne(myInfoMock, relatedItems, false);
    }

    @SuppressWarnings("unchecked")
    protected void doTestVariantOne(RelatedItemsInfo myInfoMock, RelatedItems relatedItems, boolean addMixin)
            throws ObjectBeanManagerException, QueryException, NoSuchFieldException, IllegalAccessException {
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMock);
        HstRequestContext requestContext = createMockHstRequestContext(CONTENT_BEAN_PATH, OVERVIEW_BEAN_PATH,
                myInfoMock, null, false, addMixin);
        request.setRequestContext(requestContext);

        Map<String, Object> model = relatedItems.getModel(request, null);

        Assert.assertEquals(((List<HippoBean>) model.get("items")).size(), SIZE);
        if (!addMixin) {
            Assert.assertEquals(model.get("info"), myInfoMock);
        }
        Assert.assertEquals(model.get("overviewLink"), null);
    }

    @Test
    public void getModelTestUseMixinFalseThemaFilteringNoSortingWithOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                SORT_FIELD_RELEASE_DATE, SIZE, true, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedNews();
        doTestVariantTwo(myInfoMock, relatedItems);
    }

    @SuppressWarnings("unchecked")
    protected void doTestVariantTwo(RelatedItemsInfo myInfoMock, RelatedItems relatedItems)
            throws ObjectBeanManagerException, QueryException, NoSuchFieldException, IllegalAccessException {
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMock);
        HippoBean overviewBean = createMockContentBean(OVERVIEW_BEAN_PATH);
        HstRequestContext requestContext = createMockHstRequestContext(CONTENT_BEAN_PATH, OVERVIEW_BEAN_PATH,
                myInfoMock, overviewBean, false, false);

        request.setRequestContext(requestContext);

        Map<String, Object> model = relatedItems.getModel(request, null);

        Assert.assertEquals(((List<HippoBean>) model.get("items")).size(), SIZE);
        Assert.assertEquals(model.get("info"), myInfoMock);
        Assert.assertEquals(model.get("overviewLink"), overviewBean);
    }

    @Test
    public void getModelTestUseMixinFalseThemaFilteringNoSortingNullOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                SORT_FIELD_RELEASE_DATE, SIZE, true, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedNews();
        doTestVarinatThree(myInfoMock, relatedItems);
    }

    @Test
    public void getModelTestUseMixinFalseThemaFilteringWithSortingAscendingNullOverview()
            throws ObjectBeanManagerException, IllegalStateException, QueryException, NoSuchFieldException,
            IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                "Publicatiedatum", SIZE, true, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedNews();
        doTestVarinatThree(myInfoMock, relatedItems);
    }

    @Test
    public void getModelTestUseMixinFalseThemaFilteringWithSortingDescendingNullOverview()
            throws ObjectBeanManagerException, IllegalStateException, QueryException, NoSuchFieldException,
            IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE,
                SORT_ORDER_DESCENDING, "Publicatiedatum", SIZE, true, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH,
                CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedNews();
        doTestVarinatThree(myInfoMock, relatedItems);
    }

    @SuppressWarnings("unchecked")
    protected void doTestVarinatThree(RelatedItemsInfo myInfoMock, RelatedItems relatedItems)
            throws ObjectBeanManagerException, QueryException, NoSuchFieldException, IllegalAccessException {
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMock);
        HippoBean overviewBean = null;
        HstRequestContext requestContext = createMockHstRequestContext(CONTENT_BEAN_PATH, OVERVIEW_BEAN_PATH,
                myInfoMock, overviewBean, false, false);

        request.setRequestContext(requestContext);

        Map<String, Object> model = relatedItems.getModel(request, null);

        Assert.assertEquals(((List<HippoBean>) model.get("items")).size(), SIZE);
        Assert.assertEquals(model.get("info"), myInfoMock);
        Assert.assertEquals(model.get("overviewLink"), overviewBean);
    }

    @Test
    public void getModelTestQueryScopeNull() throws ObjectBeanManagerException, IllegalStateException, QueryException,
            NoSuchFieldException, IllegalAccessException {
        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE,
                SORT_ORDER_DESCENDING, "Publicatiedatum", SIZE, true, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, null);

        RelatedItems relatedItems = new RelatedNews();
        doTestViariantFour(myInfoMock, relatedItems);
    }

    protected void doTestViariantFour(RelatedItemsInfo myInfoMock, RelatedItems relatedItems)
            throws ObjectBeanManagerException, QueryException, NoSuchFieldException, IllegalAccessException {
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMock);
        HippoBean overviewBean = null;
        HstRequestContext requestContext = createMockHstRequestContext(null, OVERVIEW_BEAN_PATH, myInfoMock,
                overviewBean, false, false);

        request.setRequestContext(requestContext);

        Map<String, Object> model = relatedItems.getModel(request, null);

        Assert.assertEquals(model.get("items"), null);
        Assert.assertEquals(model.get("info"), myInfoMock);
        Assert.assertEquals(model.get("overviewLink"), null);
    }

    @Test
    public void getModelTestQueryException() throws ObjectBeanManagerException, IllegalStateException, QueryException,
            NoSuchFieldException, IllegalAccessException {
        try {
            RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE,
                    SORT_ORDER_DESCENDING, "Publicatiedatum", SIZE, true, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH,
                    CONTENT_BEAN_PATH);

            RelatedItems relatedItems = new RelatedNews();
            doTestVariantFive(myInfoMock, relatedItems);

        } catch (HstComponentException e) {
            Assert.assertEquals(e.getMessage(), QUERY_EXCEPTION);
        }

    }

    protected void doTestVariantFive(RelatedItemsInfo myInfoMock, RelatedItems relatedItems)
            throws ObjectBeanManagerException, QueryException, NoSuchFieldException, IllegalAccessException {
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMock);

        HippoBean overviewBean = null;
        HstRequestContext requestContext = createMockHstRequestContext(CONTENT_BEAN_PATH, OVERVIEW_BEAN_PATH,
                myInfoMock, overviewBean, true, false);

        request.setRequestContext(requestContext);

        relatedItems.getModel(request, null);
    }

    protected HstRequestContext createMockHstRequestContext(String contentBeanPath, String overviewBeanPath,
            RelatedItemsInfo info, HippoBean overviewBean, boolean throwQueryException, boolean addMixin)
            throws ObjectBeanManagerException, IllegalStateException, QueryException, NoSuchFieldException,
            IllegalAccessException {
        try {
            HstRequestContext mock = EasyMock.createMock(HstRequestContext.class);
            HippoBean scopeBean = null;
            if (contentBeanPath != null) {
                scopeBean = createMockContentBean(contentBeanPath);
            }

            ArticlePage displayedArticlePage = new ArticlePage();
            Session session = EasyMock.createMock(Session.class);
            MockNode node = new MockNode(session);
            node.setPrimaryType("hippostd:folder");
            node.setPath(CONTENT_BEAN_PATH);
            EasyMock.expect(session.getRootNode()).andReturn(new MockNode()).anyTimes();
            EasyMock.expect(session.getNodeByIdentifier("cafebabe-cafe-babe-cafe-babecafebab2")).andReturn(node)
                    .anyTimes();
            EasyMock.replay(session);
            MockNode articlePageNode = new MockNode(session);
            articlePageNode.setProperty(FILTER_FIELD_SUBJECT_TAGS, new String[] { FILTER_VALUE_OVER_1 });
            articlePageNode.setProperty(FILTER_FIELD_THEMA_TAGS, new String[] { FILTER_VALUE_THEMA_1 });
            if (addMixin) {
                articlePageNode.addMixin("hsl:RelatedItemsMixin");
                Node mixinNode = articlePageNode.addNode("hsl:relatedCompoundMixin", "hsl:RelatedCompoundMixin");
                Node filterParameters = mixinNode.addNode("hsl:filterParameters", "hsl:RelatedFilterParameters");
                filterParameters.setProperty("hsl:overFilter", false);
                filterParameters.setProperty("hsl:themaFilter", false);
                filterParameters.setProperty("hsl:typeFilter", false);
                Node widgetParameters = mixinNode.addNode("hsl:widgetParameters", "hsl:RelatedWidgetParameters");
                widgetParameters.setProperty("hsl:size", SIZE);
                widgetParameters.setProperty("hsl:widgetTitle", "Title");
                Node sortParameters = mixinNode.addNode("hsl:sortParameters", "hsl:RelatedSortParameters");
                sortParameters.setProperty("hsl:sortBy", "Publicatiedatum");
                sortParameters.setProperty("hsl:sortOrder", "ascending");
                Node overviewParameters = mixinNode.addNode("hsl:overviewParameters", "hsl:RelatedOverviewParameters");
                overviewParameters.setProperty("hsl:overviewLinkLabel", "");
                Node mirror = overviewParameters.addNode("hsl:overviewBeanPath", "hippo:mirror");
                mirror.setProperty("hippo:docbase", "cafebabe-cafe-babe-cafe-babecafebab2");
                overviewParameters.setProperty("hsl:showOverview", false);
                mirror = widgetParameters.addNode("hsl:contentBeanPath", "hippo:mirror");
                mirror.setProperty("hippo:docbase", "cafebabe-cafe-babe-cafe-babecafebab2");
            }
            displayedArticlePage.setNode(articlePageNode);
            displayedArticlePage.setObjectConverter(TestUtils.getObjectConverter());

            EasyMock.expect(mock.getContentBean()).andReturn(displayedArticlePage).anyTimes();
            EasyMock.expect(mock.getObjectBeanManager())
                    .andReturn(createObjectBeanManagerMock(contentBeanPath, scopeBean, overviewBeanPath, overviewBean))
                    .anyTimes();

            EasyMock.expect(mock.getQueryManager()).andReturn(
                    createMockQueryManager(scopeBean, info, displayedArticlePage, throwQueryException));
            EasyMock.replay(mock);
            return mock;
        } catch (ValueFormatException e) {
            // never going to happen
            throw new RuntimeException(e.getMessage(), e);
        } catch (RepositoryException e) {
            // never going to happen
            throw new RuntimeException(e.getMessage(), e);
        }

    }

 

    protected ObjectBeanManager createObjectBeanManagerMock(String contentBeanPath, HippoBean scopeContentBeanMock,
            String overviewBeanPath, HippoBean overviewContentBeanMock) throws ObjectBeanManagerException {
        ObjectBeanManager mock = EasyMock.createMock(ObjectBeanManager.class);
        EasyMock.expect(mock.getObject(contentBeanPath)).andReturn(scopeContentBeanMock).anyTimes();
        EasyMock.expect(mock.getObject(overviewBeanPath)).andReturn(overviewContentBeanMock).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    protected HstQueryManager createMockQueryManager(HippoBean scopeBean, RelatedItemsInfo info,
            ArticlePage contentBean, boolean throwException) throws ObjectBeanManagerException, QueryException {
        HstQueryManager mock = EasyMock.createMock(HstQueryManager.class);
        EasyMock.expect(mock.createQuery(scopeBean, HSL_NEWSPAGE_TYPE)).andReturn(
                createMockQuery(info, contentBean, throwException));
        EasyMock.expect(mock.createQuery(scopeBean, HSL_EVENTPAGE_TYPE)).andReturn(
                createMockQuery(info, contentBean, throwException));
        EasyMock.replay(mock);
        return mock;
    }

    protected HstQuery createMockQuery(RelatedItemsInfo info, ArticlePage contentBean, boolean throwException)
            throws QueryException {
        HstQuery mock = EasyMock.createMock(HstQuery.class);
        Filter filterMock = createFilterMock(mock, info, contentBean);
        EasyMock.expect(mock.createFilter()).andReturn(filterMock);
        mock.setLimit(SIZE);
        mock.setFilter(filterMock);
        EasyMock.expect(mock.getQueryAsString(false)).andReturn("print sorted query version");
        EasyMock.expect(mock.getQueryAsString(true)).andReturn("print unsorted query version");
        if (info.getSortOrder().equals(SORT_ORDER_ASCENDING)) {
            mock.addOrderByAscending(SORT_FIELD_RELEASE_DATE);
        }
        if (info.getSortOrder().equals(SORT_ORDER_DESCENDING)) {
            mock.addOrderByDescending(SORT_FIELD_RELEASE_DATE);
        }

        if (!throwException) {
            EasyMock.expect(mock.execute()).andReturn(createHstQueryResultMock());
        } else {
            EasyMock.expect(mock.execute()).andThrow(new QueryException(QUERY_EXCEPTION));
        }

        EasyMock.replay(mock);
        return mock;
    }

    protected HstQueryResult createHstQueryResultMock() {
        HstQueryResult mock = EasyMock.createMock(HstQueryResult.class);
        EasyMock.expect(mock.getHippoBeans()).andReturn(createHippoBeanIteratorMock());
        EasyMock.expect(mock.getSize()).andReturn(SIZE);

        EasyMock.replay(mock);
        return mock;
    }

    protected HippoBeanIterator createHippoBeanIteratorMock() {
        MockHippoBeanIterator mockHippoBeanIterator = new MockHippoBeanIterator(Arrays.asList(new HippoBean[] {
                createMockContentBean("/result/one"), createMockContentBean("/result/two") }));
        return mockHippoBeanIterator;
    }

    protected Filter createFilterMock(HstQuery query, RelatedItemsInfo info, ArticlePage contentBean)
            throws FilterException {
        Filter globalFilter = EasyMock.createMock(Filter.class);
        EasyMock.expect(query.createFilter()).andReturn(globalFilter);

        if (info.getOverFilter()) {
            Filter ff = addFilterOnField(query, contentBean.getSubjecttags(), "hsl:subjecttags");
            EasyMock.expect(globalFilter.addAndFilter(ff)).andReturn(globalFilter);
        }
        if (info.getThemaFilter()) {
            Filter ff = addFilterOnField(query, contentBean.getThematags(), "hsl:thematags");
            EasyMock.expect(globalFilter.addAndFilter(ff)).andReturn(globalFilter);
        }

        EasyMock.replay(globalFilter);
        return globalFilter;
    }

    protected Filter addFilterOnField(HstQuery query, String[] filterValues, String fieldToFilter)
            throws FilterException {
        Filter f = EasyMock.createMock(Filter.class);
        EasyMock.expect(query.createFilter()).andReturn(f);
        for (String value : filterValues) {
            Filter tagfilter = EasyMock.createMock(Filter.class);
            EasyMock.expect(query.createFilter()).andReturn(tagfilter);
            EasyMock.expect(f.addOrFilter(tagfilter)).andReturn(f);
            tagfilter.addEqualTo(fieldToFilter, value);
            EasyMock.replay(tagfilter);

        }
        EasyMock.replay(f);
        return f;
    }

    protected HippoBean createMockContentBean(String contentBeanPath) {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    protected static RelatedEventsInfo createRelatedinfoInfoMock(final Boolean useMixin, Boolean theamFilter,
            Boolean overFilter, String widgetTitle, String sortOrder, String sortBy, Integer size,
            Boolean showOverview, String overviewLinkLabel, String overviewBeanPath, String contentBeanPath) {

        RelatedEventsInfo mock = EasyMock.createMock(RelatedEventsInfo.class);

        EasyMock.expect(mock.getWidgetTitle()).andReturn(widgetTitle).anyTimes();
        EasyMock.expect(mock.getUseMixin()).andReturn(useMixin).anyTimes();
        EasyMock.expect(mock.getThemaFilter()).andReturn(theamFilter).anyTimes();
        EasyMock.expect(mock.getSortOrder()).andReturn(sortOrder).anyTimes();
        EasyMock.expect(mock.getSortBy()).andReturn(sortBy).anyTimes();
        EasyMock.expect(mock.getOverFilter()).andReturn(overFilter).anyTimes();
        EasyMock.expect(mock.getSize()).andReturn(size).anyTimes();
        EasyMock.expect(mock.getShowOverview()).andReturn(showOverview).anyTimes();
        EasyMock.expect(mock.getOverviewLinkLabel()).andReturn(overviewLinkLabel).anyTimes();
        EasyMock.expect(mock.getOverviewBeanPath()).andReturn(overviewBeanPath).anyTimes();
        EasyMock.expect(mock.getContentBeanPath()).andReturn(contentBeanPath).anyTimes();

        EasyMock.replay(mock);
        return mock;
    }

}
