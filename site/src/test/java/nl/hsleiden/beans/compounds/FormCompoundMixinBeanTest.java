package nl.hsleiden.beans.compounds;

import nl.hsleiden.utils.TestUtils;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.manager.ObjectConverter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.Assert;
import org.junit.Test;


public class FormCompoundMixinBeanTest {

    @Test
    public void getContentBeanPathTest() throws NoSuchFieldException, IllegalAccessException {
        String path = "/path/to/a/bean";
        FormCompoundMixinBean formCompoundMixinBean = new FormCompoundMixinBean();
        TestUtils.setPrivateField(formCompoundMixinBean, "formPicker", createMockContentBean(path));
        Assert.assertEquals(path, formCompoundMixinBean.getContentBeanPath());
    }

    @Test
    public void getContentBeanPathNullTest() throws NoSuchFieldException, IllegalAccessException {
        FormCompoundMixinBean formCompoundMixinBean = new FormCompoundMixinBean();
        ObjectConverter objectConverter = EasyMock.createNiceMock(ObjectConverter.class);
        TestUtils.setPrivateField(formCompoundMixinBean, "objectConverter", objectConverter);
        Assert.assertEquals(null, formCompoundMixinBean.getContentBeanPath());
    }
    
    private HippoBean createMockContentBean(String contentBeanPath) {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }
}
