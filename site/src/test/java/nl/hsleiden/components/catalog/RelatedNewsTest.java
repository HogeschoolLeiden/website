package nl.hsleiden.components.catalog;

import hslbeans.ArticlePage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nl.hsleiden.componentsinfo.RelatedNewsInfo;
import nl.hsleiden.utils.TestUtils;

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
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.mock.content.beans.standard.MockHippoBeanIterator;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.utils.ParameterUtils;
import org.junit.Assert;
import org.junit.Test;

public class RelatedNewsTest {

    private static final String OVERVIEW_LINK_LABEL = "notImportant";
    private static final String WIDGET_TITLE = "widgetTitle";

    private static final String SORT_FIELD_RELEASE_DATE = "hsl:releaseDate";
    private static final String SORT_ORDER_ASCENDING = "ascending";

    private static final String CONTENT_BEAN_PATH = "/content/bean/path";
    private static final String OVERVIEW_BEAN_PATH = "/overview/bean/path";
    private static final String HSL_NEWSPAGE_TYPE = "hsl:NewsPage";
    private static final int SIZE = 2;

    // filtering values
    private static final String FILTER_VALUE_OVER_1 = "over-1";
    private static final String FILTER_VALUE_THEMA_1 = "thema-1";
    private static final String FILTER_FIELD_THEMA_TAGS = "thematags";
    private static final String FILTER_FIELD_SUBJECT_TAGS = "subjecttags";

    @SuppressWarnings("unchecked")
    @Test
    public void getModelTestUseMixinFalseNoFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedNewsInfo myInfoMockNoMixinNoFilteringNoSorting = createRelatedNewsInfoMock(false, false, false,
                WIDGET_TITLE, SORT_ORDER_ASCENDING, SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL,
                OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedNews relatedNews = new RelatedNews();
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMockNoMixinNoFilteringNoSorting);
        HstRequestContext requestContext = createMockHstRequestContext(CONTENT_BEAN_PATH, OVERVIEW_BEAN_PATH,
                myInfoMockNoMixinNoFilteringNoSorting);

        request.setRequestContext(requestContext);

        Map<String, Object> model = relatedNews.getModel(request, null);

        // TODO: assert more on results and on info,
        Assert.assertEquals(((List<HippoBean>) model.get("items")).size(), SIZE);
        Assert.assertEquals(model.get("info"), myInfoMockNoMixinNoFilteringNoSorting);
        Assert.assertEquals(model.get("overviewLink"), null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getModelTestUseMixinFalseOverFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedNewsInfo myInfoMockNoMixinOverFilteringNoSorting = createRelatedNewsInfoMock(false, false, true,
                WIDGET_TITLE, SORT_ORDER_ASCENDING, SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL,
                OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedNews relatedNews = new RelatedNews();
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMockNoMixinOverFilteringNoSorting);
        HstRequestContext requestContext = createMockHstRequestContext(CONTENT_BEAN_PATH, OVERVIEW_BEAN_PATH,
                myInfoMockNoMixinOverFilteringNoSorting);

        request.setRequestContext(requestContext);

        Map<String, Object> model = relatedNews.getModel(request, null);

        Assert.assertEquals(((List<HippoBean>) model.get("items")).size(), SIZE);
        Assert.assertEquals(model.get("info"), myInfoMockNoMixinOverFilteringNoSorting);
        Assert.assertEquals(model.get("overviewLink"), null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getModelTestUseMixinFalseThemaFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
    IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {
        
        RelatedNewsInfo myInfoMockNoMixinOverFilteringNoSorting = createRelatedNewsInfoMock(false, true, false,
                WIDGET_TITLE, SORT_ORDER_ASCENDING, SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL,
                OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);
        
        RelatedNews relatedNews = new RelatedNews();
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMockNoMixinOverFilteringNoSorting);
        HstRequestContext requestContext = createMockHstRequestContext(CONTENT_BEAN_PATH, OVERVIEW_BEAN_PATH,
                myInfoMockNoMixinOverFilteringNoSorting);
        
        request.setRequestContext(requestContext);
        
        Map<String, Object> model = relatedNews.getModel(request, null);
        
        Assert.assertEquals(((List<HippoBean>) model.get("items")).size(), SIZE);
        Assert.assertEquals(model.get("info"), myInfoMockNoMixinOverFilteringNoSorting);
        Assert.assertEquals(model.get("overviewLink"), null);
    }

    private HstRequestContext createMockHstRequestContext(String contentBeanPath, String overviewBeanPath,
            RelatedNewsInfo info) throws ObjectBeanManagerException, IllegalStateException, QueryException,
            NoSuchFieldException, IllegalAccessException {
        HstRequestContext mock = EasyMock.createMock(HstRequestContext.class);
        HippoBean scopeBean = createMockContentBean(contentBeanPath);
        HippoBean overviewBean = createMockContentBean(overviewBeanPath);
        ArticlePage displayedArticlePage = new ArticlePage();
        TestUtils
                .setPrivateField(displayedArticlePage, FILTER_FIELD_SUBJECT_TAGS, new String[] { FILTER_VALUE_OVER_1 });
        TestUtils.setPrivateField(displayedArticlePage, FILTER_FIELD_THEMA_TAGS, new String[] { FILTER_VALUE_THEMA_1 });

        EasyMock.expect(mock.getContentBean()).andReturn(displayedArticlePage).anyTimes();
        EasyMock.expect(mock.getObjectBeanManager())
                .andReturn(createObjectBeanManagerMock(contentBeanPath, scopeBean, overviewBeanPath, overviewBean))
                .anyTimes();

        EasyMock.expect(mock.getQueryManager())
                .andReturn(createMockQueryManager(scopeBean, info, displayedArticlePage));
        EasyMock.replay(mock);
        return mock;
    }

    private ObjectBeanManager createObjectBeanManagerMock(String contentBeanPath, HippoBean scopeContentBeanMock,
            String overviewBeanPath, HippoBean overviewContentBeanMock) throws ObjectBeanManagerException {
        ObjectBeanManager mock = EasyMock.createMock(ObjectBeanManager.class);
        EasyMock.expect(mock.getObject(contentBeanPath)).andReturn(scopeContentBeanMock).anyTimes();
        EasyMock.expect(mock.getObject(overviewBeanPath)).andReturn(overviewContentBeanMock).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private HstQueryManager createMockQueryManager(HippoBean scopeBean, RelatedNewsInfo info, ArticlePage contentBean)
            throws ObjectBeanManagerException, QueryException {
        HstQueryManager mock = EasyMock.createMock(HstQueryManager.class);
        EasyMock.expect(mock.createQuery(scopeBean, HSL_NEWSPAGE_TYPE)).andReturn(createMockQuery(info, contentBean));
        EasyMock.replay(mock);
        return mock;
    }

    private HstQuery createMockQuery(RelatedNewsInfo info, ArticlePage contentBean) throws QueryException {
        HstQuery mock = EasyMock.createMock(HstQuery.class);
        Filter filterMock = createFilterMock(mock, info, contentBean);
        EasyMock.expect(mock.createFilter()).andReturn(filterMock);
        mock.setLimit(SIZE);
        mock.setFilter(filterMock);
        EasyMock.expect(mock.execute()).andReturn(createHstQueryResultMock());
        EasyMock.replay(mock);
        return mock;
    }

    private HstQueryResult createHstQueryResultMock() {
        HstQueryResult mock = EasyMock.createMock(HstQueryResult.class);
        EasyMock.expect(mock.getHippoBeans()).andReturn(createHippoBeanIteratorMock());

        EasyMock.replay(mock);
        return mock;
    }

    private HippoBeanIterator createHippoBeanIteratorMock() {
        MockHippoBeanIterator mockHippoBeanIterator = new MockHippoBeanIterator(Arrays.asList(new HippoBean[] {
                createMockContentBean("/result/one"), createMockContentBean("/result/two") }));
        return mockHippoBeanIterator;
    }

    private Filter createFilterMock(HstQuery query, RelatedNewsInfo info, ArticlePage contentBean)
            throws FilterException {
        Filter globalFilter = EasyMock.createMock(Filter.class);
        EasyMock.expect(query.createFilter()).andReturn(globalFilter);
        
        if (info.getOverFilter()) {
            Filter ff = addFilterOnField(query, contentBean.getSubjecttags(), "hsl:subjecttags");
            EasyMock.expect(globalFilter.addAndFilter(ff)).andReturn(globalFilter);
        }
        if (info.getThemaFilter()) {
            Filter ff = addFilterOnField(query, contentBean.getSubjecttags(), "hsl:thematags");
            EasyMock.expect(globalFilter.addAndFilter(ff)).andReturn(globalFilter);
        }

        EasyMock.replay(globalFilter);
        return globalFilter;
    }

    private Filter addFilterOnField(HstQuery query, String[] filterValues, String fieldToFilter) throws FilterException {
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

    private HippoBean createMockContentBean(String contentBeanPath) {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private static RelatedNewsInfo createRelatedNewsInfoMock(final Boolean useMixin, Boolean theamFilter,
            Boolean overFilter, String widgetTitle, String sortOrder, String sortBy, Integer size,
            Boolean showOverview, String overviewLinkLabel, String overviewBeanPath, String contentBeanPath) {
        RelatedNewsInfo mock = EasyMock.createMock(RelatedNewsInfo.class);
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
