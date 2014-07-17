package nl.hsleiden.components.catalog;

import hslbeans.ArticlePage;

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
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.junit.Assert;
import org.junit.Test;

public class RelatedEventsTest extends RelatedItemsTest {
    @Test
    public void getModelTestUseMixinFalseNoFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedEventsInfo myInfoMock = createRelatedinfoInfoMock(false, false, false, WIDGET_TITLE,
                SORT_ORDER_ASCENDING, SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH,
                CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedEvents();
        doTestVariantOne(myInfoMock, relatedItems, false);
    }
    
    @Test
    public void getModelTestUseMixinTrueWithNoMixinNoFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
    IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {
        
        RelatedEventsInfo myInfoMock = createRelatedinfoInfoMock(true, false, false, WIDGET_TITLE,
                SORT_ORDER_ASCENDING, SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH,
                CONTENT_BEAN_PATH);
        
        RelatedItems relatedItems = new RelatedEvents();
        doTestVariantOne(myInfoMock, relatedItems, false);
    }

    @Test
    public void getModelTestUseMixinTrueWithMixinNoFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
    IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {
        
        RelatedEventsInfo myInfoMock = createRelatedinfoInfoMock(true, false, false, WIDGET_TITLE,
                SORT_ORDER_ASCENDING, SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH,
                CONTENT_BEAN_PATH);
        
        RelatedItems relatedItems = new RelatedEvents();
        doTestVariantOne(myInfoMock, relatedItems, true);
    }
    
    @Test
    public void getModelTestUseMixinFalseOverFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, false, true, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedEvents();
        doTestVariantOne(myInfoMock, relatedItems, false);
    }

    @Test
    public void getModelTestUseMixinFalseThemaFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedEvents();
        doTestVariantOne(myInfoMock, relatedItems, true);
    }
    
    @Test
    public void getModelTestUseMixinFalseThemaFilteringNoSortingWithOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                SORT_FIELD_RELEASE_DATE, SIZE, true, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedEvents();
        doTestVariantTwo(myInfoMock, relatedItems);
    }
    
    @Test
    public void getModelTestUseMixinFalseThemaFilteringNoSortingNullOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                SORT_FIELD_RELEASE_DATE, SIZE, true, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedEvents();
        doTestVarinatThree(myInfoMock, relatedItems);
    }
    
    @Test
    public void getModelTestUseMixinFalseThemaFilteringWithSortingAscendingNullOverview()
            throws ObjectBeanManagerException, IllegalStateException, QueryException, NoSuchFieldException,
            IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                "Publicatiedatum", SIZE, true, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedEvents();
        doTestVarinatThree(myInfoMock, relatedItems);
    }

    @Test
    public void getModelTestUseMixinFalseThemaFilteringWithSortingDescendingNullOverview()
            throws ObjectBeanManagerException, IllegalStateException, QueryException, NoSuchFieldException,
            IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE,
                SORT_ORDER_DESCENDING, "Publicatiedatum", SIZE, true, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH,
                CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedEvents();
        doTestVarinatThree(myInfoMock, relatedItems);
    }
    
    @Test
    public void getModelTestQueryScopeNull() throws ObjectBeanManagerException, IllegalStateException, QueryException,
            NoSuchFieldException, IllegalAccessException {
        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE,
                SORT_ORDER_DESCENDING, "Publicatiedatum", SIZE, true, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, null);

        RelatedItems relatedItems = new RelatedEvents();
        doTestViariantFour(myInfoMock, relatedItems);
    }
    
    @Test
    public void getModelTestQueryException() throws ObjectBeanManagerException, IllegalStateException, QueryException,
            NoSuchFieldException, IllegalAccessException {
        try {
            RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE,
                    SORT_ORDER_DESCENDING, "Publicatiedatum", SIZE, true, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH,
                    CONTENT_BEAN_PATH);

            RelatedItems relatedItems = new RelatedEvents();
            doTestVariantFive(myInfoMock, relatedItems);

        } catch (HstComponentException e) {
            Assert.assertEquals(e.getMessage(), QUERY_EXCEPTION);
        }

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
                articlePageNode.addMixin("hsl:RelatedEventsMixin");
                Node mixinNode = articlePageNode.addNode("hsl:relatedEventsCompoundMixin", "hsl:RelatedCompoundMixin");
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
    
}