package nl.hsleiden.beans.compounds;

import hslbeans.RelatedFilterParameters;
import hslbeans.RelatedOverviewParameters;
import hslbeans.RelatedSortParameters;
import hslbeans.RelatedWidgetParameters;
import nl.hsleiden.utils.TestUtils;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.Assert;
import org.junit.Test;

public class RelatedCompoundMixinBeanTest {

    @Test
    public void getUseMixinTest(){
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        try{
            relatedCompoundMixinBean.getUseMixin();  
        }catch(UnsupportedOperationException e){
            Assert.assertNotEquals(e, null);
        }
    }
    
    @Test
    public void getWidgetTitleTest() throws NoSuchFieldException, IllegalAccessException {
        String title = "title";
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "widgetParameters",
                createMockWidgetParameters(title, null, null));
        Assert.assertEquals(title, relatedCompoundMixinBean.getWidgetTitle());
    }

    @Test
    public void getContentBeanPathTest() throws NoSuchFieldException, IllegalAccessException {
        String path = "/path/to/a/bean";
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "widgetParameters",
                createMockWidgetParameters(null, path, null));
        Assert.assertEquals(path, relatedCompoundMixinBean.getContentBeanPath());
    }

    @Test
    public void getContentBeanPathNullTest() throws NoSuchFieldException, IllegalAccessException {
        String path = null;
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "widgetParameters",
                createMockWidgetParametersContentBeanPathNull(null, path, null));
        Assert.assertEquals(null, relatedCompoundMixinBean.getContentBeanPath());
    }
    
    @Test
    public void getSizeTest() throws NoSuchFieldException, IllegalAccessException{
        long size =5l;
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "widgetParameters",
                createMockWidgetParameters(null, null, size));
        Assert.assertEquals(size, relatedCompoundMixinBean.getSize());
        
    }
    
    @Test
    public void getThemaFilterTest() throws NoSuchFieldException, IllegalAccessException{
        boolean themaFilter = false;
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "filterParameters",
                createMockFilterParameters(themaFilter, true));
        Assert.assertEquals(themaFilter, relatedCompoundMixinBean.getThemaFilter());
    }

    @Test
    public void getOverFilterTest() throws NoSuchFieldException, IllegalAccessException{
        boolean overFilter = false;
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "filterParameters",
                createMockFilterParameters(true, overFilter));
        Assert.assertEquals(overFilter, relatedCompoundMixinBean.getOverFilter());
    }
    
    @Test
    public void getSortByTest() throws NoSuchFieldException, IllegalAccessException{
        String sortBy = "sortByTest";
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "sortParameters",
                createMockSortParameters(sortBy, null));
        Assert.assertEquals(sortBy, relatedCompoundMixinBean.getSortBy());
    }

    @Test
    public void getSortOrderTest() throws NoSuchFieldException, IllegalAccessException{
        String sortOrder = "sortOrderTest";
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "sortParameters",
                createMockSortParameters(null, sortOrder));
        Assert.assertEquals(sortOrder, relatedCompoundMixinBean.getSortOrder());
    }
    
    @Test
    public void getShowOverviewTest() throws NoSuchFieldException, IllegalAccessException {
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "overviewParameters",
                createMockOverviewParameters(false, null, null));
        Assert.assertEquals(false, relatedCompoundMixinBean.getShowOverview());
    
    }

    @Test
    public void getOverviewBeanPathTest() throws NoSuchFieldException, IllegalAccessException {
        String path = "/path/to/my/bean";
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "overviewParameters",
                createMockOverviewParameters(false, path, null));
        Assert.assertEquals(path, relatedCompoundMixinBean.getOverviewBeanPath());
    }

    @Test
    public void getOverviewBeanPathNullTest() throws NoSuchFieldException, IllegalAccessException {
        String path = null;
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "overviewParameters",
                createMockOverviewParametersContentBeanPathNull(false, path, null));
        Assert.assertEquals(null, relatedCompoundMixinBean.getOverviewBeanPath());
    }

    @Test
    public void getOverviewLinkLabelTest() throws NoSuchFieldException, IllegalAccessException {
        String result = "overviewLinkLabelTest";
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "overviewParameters",
                createMockOverviewParameters(false, null, result));
        Assert.assertEquals(result, relatedCompoundMixinBean.getOverviewLinkLabel());
    }

    private RelatedWidgetParameters createMockWidgetParameters(String title, String contentBeanPath, Long size) {
        RelatedWidgetParameters mock = EasyMock.createMock(RelatedWidgetParameters.class);
        EasyMock.expect(mock.getWidgetTitle()).andReturn(title).anyTimes();
        EasyMock.expect(mock.getContentBeanPath()).andReturn(createMockContentBean(contentBeanPath)).anyTimes();
        EasyMock.expect(mock.getSize()).andReturn(size).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private RelatedWidgetParameters createMockWidgetParametersContentBeanPathNull(String title, String contentBeanPath, Long size) {
        RelatedWidgetParameters mock = EasyMock.createMock(RelatedWidgetParameters.class);
        EasyMock.expect(mock.getWidgetTitle()).andReturn(title).anyTimes();
        EasyMock.expect(mock.getContentBeanPath()).andReturn(null).anyTimes();
        EasyMock.expect(mock.getSize()).andReturn(size).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private RelatedOverviewParameters createMockOverviewParameters(boolean showOverview, String contentBeanPath, String linkLabel) {
        RelatedOverviewParameters mock = EasyMock.createMock(RelatedOverviewParameters.class);
        EasyMock.expect(mock.getShowOverview()).andReturn(showOverview).anyTimes();
        EasyMock.expect(mock.getOverviewBeanPath()).andReturn(createMockContentBean(contentBeanPath)).anyTimes();
        EasyMock.expect(mock.getOverviewLinkLabel()).andReturn(linkLabel).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private RelatedOverviewParameters createMockOverviewParametersContentBeanPathNull(boolean showOverview, String contentBeanPath, String linkLabel) {
        RelatedOverviewParameters mock = EasyMock.createMock(RelatedOverviewParameters.class);
        EasyMock.expect(mock.getShowOverview()).andReturn(showOverview).anyTimes();
        EasyMock.expect(mock.getOverviewBeanPath()).andReturn(null).anyTimes();
        EasyMock.expect(mock.getOverviewLinkLabel()).andReturn(linkLabel).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private RelatedFilterParameters createMockFilterParameters(boolean themaFilter, boolean overFilter) {
        RelatedFilterParameters mock = EasyMock.createMock(RelatedFilterParameters.class);
        EasyMock.expect(mock.getOverFilter()).andReturn(overFilter).anyTimes();
        EasyMock.expect(mock.getThemaFilter()).andReturn(themaFilter).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private RelatedSortParameters createMockSortParameters(String sortBy, String sortOrder) {
        RelatedSortParameters mock = EasyMock.createMock(RelatedSortParameters.class);
        EasyMock.expect(mock.getSortBy()).andReturn(sortBy).anyTimes();
        EasyMock.expect(mock.getSortOrder()).andReturn(sortOrder).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private HippoBean createMockContentBean(String contentBeanPath) {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

}
