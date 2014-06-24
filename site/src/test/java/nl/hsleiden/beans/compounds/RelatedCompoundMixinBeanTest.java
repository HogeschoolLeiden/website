package nl.hsleiden.beans.compounds;

import hslbeans.RelatedWidgetParameters;
import nl.hsleiden.utils.TestUtils;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.Assert;
import org.junit.Test;

public class RelatedCompoundMixinBeanTest {

    @Test
    public void getWidgetTitleTest() throws NoSuchFieldException, IllegalAccessException {
        String title = "title";
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "widgetParameters",
                createMackWidgetParameters(title, null, null));
        Assert.assertEquals(title, relatedCompoundMixinBean.getWidgetTitle());
    }

    @Test
    public void getContentBeanPathTest() throws NoSuchFieldException, IllegalAccessException {
        String path = "/path/to/a/bean";
        RelatedCompoundMixinBean relatedCompoundMixinBean = new RelatedCompoundMixinBean();
        TestUtils.setPrivateField(relatedCompoundMixinBean, "widgetParameters",
                createMackWidgetParameters(null, path, null));
        Assert.assertEquals(path, relatedCompoundMixinBean.getContentBeanPath());
    }

    private RelatedWidgetParameters createMackWidgetParameters(String title, String contentBeanPath, Long size) {
        RelatedWidgetParameters mock = EasyMock.createMock(RelatedWidgetParameters.class);
        EasyMock.expect(mock.getWidgetTitle()).andReturn(title).anyTimes();
        EasyMock.expect(mock.getContentBeanPath()).andReturn(createMockContentBean(contentBeanPath)).anyTimes();
        EasyMock.expect(mock.getSize()).andReturn(size).anyTimes();
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
