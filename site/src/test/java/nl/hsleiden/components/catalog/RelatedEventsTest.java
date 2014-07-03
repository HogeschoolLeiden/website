package nl.hsleiden.components.catalog;

import nl.hsleiden.componentsinfo.RelatedEventsInfo;
import nl.hsleiden.componentsinfo.RelatedItemsInfo;

import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.core.component.HstComponentException;
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
        doTestVariantOne(myInfoMock, relatedItems);
    }
    
    @Test
    public void getModelTestUseMixinFalseOverFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, false, true, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedEvents();
        doTestVariantOne(myInfoMock, relatedItems);
    }

    @Test
    public void getModelTestUseMixinFalseThemaFilteringNoSortingNoOverview() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException {

        RelatedItemsInfo myInfoMock = createRelatedinfoInfoMock(false, true, false, WIDGET_TITLE, SORT_ORDER_ASCENDING,
                SORT_FIELD_RELEASE_DATE, SIZE, false, OVERVIEW_LINK_LABEL, OVERVIEW_BEAN_PATH, CONTENT_BEAN_PATH);

        RelatedItems relatedItems = new RelatedEvents();
        doTestVariantOne(myInfoMock, relatedItems);
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
}