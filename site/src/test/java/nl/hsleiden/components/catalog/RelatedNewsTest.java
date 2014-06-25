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
    
    private static final String CONTENT_BEAN_PATH = "/content/bean/path";
    private static final String OVERVIEW_BEAN_PATH = "/overview/bean/path";
    private static final String HSL_NEWSPAGE_TYPE = "hsl:NewsPage";
    private static final int SIZE = 2;
    
    //filtering values
    private static final String FILTER_VALUE_OVER_1 = "over-1";
    private static final String FILTER_VALUE_OVER_2 = "over-2";
    private static final String FILTER_VALUE_THEMA_1 = "thema-1";
    private static final String FILTER_VALUE_THEMA_2 = "thema-2";
    private static final String FILTER_FIELD_THEMA_TAGS = "thematags";
    private static final String FILTER_FIELD_SUBJECT_TAGS = "subjecttags";

    // no overview link for these info
    private static final RelatedNewsInfo myInfoMockNoMixinNoFilteringNoSorting = createRelatedNewsInfoMockNoFilteringNoSorting(false);
    private static final RelatedNewsInfo myInfoMockNoMixinThemaFilteringNoSorting = createRelatedNewsInfoMockThemaFilteringNoSorting(false);
    private static final RelatedNewsInfo myInfoMockNoMixinOverFilteringNoSorting = createRelatedNewsInfoMockOverFilteringNoSorting(false);
    private static final RelatedNewsInfo myInfoMockNoMixinDoubleFilteringNoSorting = createRelatedNewsInfoMockDoubleFilteringNoSorting(false);

    @SuppressWarnings("unchecked")
    @Test
    public void getModelTestUseMixinFalseNoFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {
        RelatedNews relatedNews = new RelatedNews();
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMockNoMixinNoFilteringNoSorting);
        HstRequestContext requestContext = createMockHstRequestContext(CONTENT_BEAN_PATH, OVERVIEW_BEAN_PATH, myInfoMockNoMixinNoFilteringNoSorting);

        request.setRequestContext(requestContext);

        Map<String, Object> model = relatedNews.getModel(request, null);

        // TODO: assert more on results and on info,
        Assert.assertEquals(((List<HippoBean>) model.get("items")).size(), SIZE);
        Assert.assertEquals(model.get("info"), myInfoMockNoMixinNoFilteringNoSorting);
        Assert.assertEquals(model.get("overviewLink"), null);
    }

    @SuppressWarnings("unchecked")
//    @Test
    public void getModelTestUseMixinFalseOverFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {
        RelatedNews relatedNews = new RelatedNews();
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMockNoMixinOverFilteringNoSorting);
        HstRequestContext requestContext = createMockHstRequestContext(CONTENT_BEAN_PATH, OVERVIEW_BEAN_PATH, myInfoMockNoMixinDoubleFilteringNoSorting);

        request.setRequestContext(requestContext);

        Map<String, Object> model = relatedNews.getModel(request, null);

        Assert.assertEquals(((List<HippoBean>) model.get("items")).size(), SIZE);
        Assert.assertEquals(model.get("info"), myInfoMockNoMixinNoFilteringNoSorting);
        Assert.assertEquals(model.get("overviewLink"), null);
    }

    private HstRequestContext createMockHstRequestContext(String contentBeanPath, String overviewBeanPath, RelatedNewsInfo info)
            throws ObjectBeanManagerException, IllegalStateException, QueryException, NoSuchFieldException,
            IllegalAccessException {
        HstRequestContext mock = EasyMock.createMock(HstRequestContext.class);
        HippoBean scopeBean = createMockContentBean(contentBeanPath);
        HippoBean overviewBean = createMockContentBean(overviewBeanPath);
        ArticlePage displayedArticlePage = new ArticlePage();
        TestUtils.setPrivateField(displayedArticlePage, FILTER_FIELD_SUBJECT_TAGS, new String[] { FILTER_VALUE_OVER_1, FILTER_VALUE_OVER_2 });
        TestUtils.setPrivateField(displayedArticlePage, FILTER_FIELD_THEMA_TAGS, new String[] { FILTER_VALUE_THEMA_1, FILTER_VALUE_THEMA_2 });

        EasyMock.expect(mock.getContentBean()).andReturn(displayedArticlePage).anyTimes();
        EasyMock.expect(mock.getObjectBeanManager())
                .andReturn(createObjectBeanManagerMock(contentBeanPath, scopeBean, overviewBeanPath, overviewBean))
                .anyTimes();
        if(info.getThemaFilter()){            
            EasyMock.expect(mock.getQueryManager()).andReturn(createMockQueryManager(scopeBean, true, FILTER_FIELD_THEMA_TAGS, FILTER_VALUE_THEMA_1)).anyTimes();
        }else if(info.getOverFilter()){
            EasyMock.expect(mock.getQueryManager()).andReturn(createMockQueryManager(scopeBean, true, FILTER_FIELD_SUBJECT_TAGS, FILTER_VALUE_OVER_1)).anyTimes();
        }else{
            EasyMock.expect(mock.getQueryManager()).andReturn(createMockQueryManager(scopeBean, false, null, null)).anyTimes();
        }
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

    private HstQueryManager createMockQueryManager(HippoBean scopeBean, boolean useSubfilters, String filterFieldName, String value) throws ObjectBeanManagerException,
            QueryException {
        HstQueryManager mock = EasyMock.createMock(HstQueryManager.class);
        EasyMock.expect(mock.createQuery(scopeBean, HSL_NEWSPAGE_TYPE)).andReturn(createQueryMockNoFilters(useSubfilters, filterFieldName, value))
                .anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private HstQuery createQueryMockNoFilters(boolean subfilters, String fieldAttributeName, String value) throws QueryException {
        HstQuery mock = EasyMock.createMock(HstQuery.class);
        Filter createFilterMock = null;
        createFilterMock(subfilters, fieldAttributeName, value);
        EasyMock.expect(mock.createFilter()).andReturn(createFilterMock).anyTimes();
        mock.setLimit(SIZE);
        mock.setFilter(createFilterMock);
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

    private Filter createFilterMock(boolean useSubfilters, String fieldAttributeName, String value) throws FilterException {
        Filter parentMockFilter = EasyMock.createMock(Filter.class);

        if(useSubfilters){
            Filter mock = EasyMock.createMock(Filter.class);
            mock.addEqualTo(fieldAttributeName, value);
            parentMockFilter.addOrFilter(mock);
        }
        
        EasyMock.replay(parentMockFilter);
        return parentMockFilter;
    }

    private HippoBean createMockContentBean(String contentBeanPath) {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private static RelatedNewsInfo createRelatedNewsInfoMockNoFilteringNoSorting(final Boolean useMixin) {
        return new RelatedNewsInfo() {

            public String getWidgetTitle() {
                return "widget title";
            }

            public Boolean getUseMixin() {
                return useMixin;
            }

            public Boolean getThemaFilter() {
                return false;
            }

            public String getSortOrder() {
                return null;
            }

            public String getSortBy() {
                return null;
            }

            public int getSize() {
                return SIZE;
            }

            public Boolean getShowOverview() {
                return false;
            }

            public String getOverviewLinkLabel() {
                return "";
            }

            public String getOverviewBeanPath() {
                return OVERVIEW_BEAN_PATH;
            }

            public Boolean getOverFilter() {
                return false;
            }

            public String getContentBeanPath() {
                return CONTENT_BEAN_PATH;
            }
        };
    }

    private static RelatedNewsInfo createRelatedNewsInfoMockThemaFilteringNoSorting(final Boolean useMixin) {
        return new RelatedNewsInfo() {

            public String getWidgetTitle() {
                return "widget title";
            }

            public Boolean getUseMixin() {
                return useMixin;
            }

            public Boolean getThemaFilter() {
                return true;
            }

            public String getSortOrder() {
                return null;
            }

            public String getSortBy() {
                return null;
            }

            public int getSize() {
                return SIZE;
            }

            public Boolean getShowOverview() {
                return false;
            }

            public String getOverviewLinkLabel() {
                return "";
            }

            public String getOverviewBeanPath() {
                return OVERVIEW_BEAN_PATH;
            }

            public Boolean getOverFilter() {
                return false;
            }

            public String getContentBeanPath() {
                return CONTENT_BEAN_PATH;
            }
        };
    }

    private static RelatedNewsInfo createRelatedNewsInfoMockOverFilteringNoSorting(final Boolean useMixin) {
        return new RelatedNewsInfo() {

            public String getWidgetTitle() {
                return "widget title";
            }

            public Boolean getUseMixin() {
                return useMixin;
            }

            public Boolean getThemaFilter() {
                return false;
            }

            public String getSortOrder() {
                return null;
            }

            public String getSortBy() {
                return null;
            }

            public int getSize() {
                return SIZE;
            }

            public Boolean getShowOverview() {
                return false;
            }

            public String getOverviewLinkLabel() {
                return "";
            }

            public String getOverviewBeanPath() {
                return OVERVIEW_BEAN_PATH;
            }

            public Boolean getOverFilter() {
                return true;
            }

            public String getContentBeanPath() {
                return CONTENT_BEAN_PATH;
            }
        };
    }

    private static RelatedNewsInfo createRelatedNewsInfoMockDoubleFilteringNoSorting(final Boolean useMixin) {
        return new RelatedNewsInfo() {

            public String getWidgetTitle() {
                return "widget title";
            }

            public Boolean getUseMixin() {
                return useMixin;
            }

            public Boolean getThemaFilter() {
                return true;
            }

            public String getSortOrder() {
                return null;
            }

            public String getSortBy() {
                return null;
            }

            public int getSize() {
                return SIZE;
            }

            public Boolean getShowOverview() {
                return false;
            }

            public String getOverviewLinkLabel() {
                return "";
            }

            public String getOverviewBeanPath() {
                return OVERVIEW_BEAN_PATH;
            }

            public Boolean getOverFilter() {
                return true;
            }

            public String getContentBeanPath() {
                return CONTENT_BEAN_PATH;
            }
        };
    }
}
