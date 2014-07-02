package nl.hsleiden.beans.compounds;

import hslbeans.FormWidgetParameters;
import nl.hsleiden.utils.TestUtils;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.Assert;
import org.junit.Test;


public class FormCompoundMixinBeanTest {

    @Test
    public void getContentBeanPathTest() throws NoSuchFieldException, IllegalAccessException {
        String pathToForm = "/path/to/a/form";
        String pathTothanks = "/path/to/a/thankYouPage";
        FormCompoundMixinBean formCompoundMixinBean = new FormCompoundMixinBean();
        TestUtils.setPrivateField(formCompoundMixinBean, "formWidgetParameters", createMockWidgetParameters(pathToForm, pathTothanks));
        Assert.assertEquals(pathToForm, formCompoundMixinBean.getContentBeanPath());
    }

    @Test
    public void getContentBeanPathNullTest() throws NoSuchFieldException, IllegalAccessException {
        String pathTothanks = "/path/to/a/thankYouPage";
        FormCompoundMixinBean formCompoundMixinBean = new FormCompoundMixinBean();
        TestUtils.setPrivateField(formCompoundMixinBean, "formWidgetParameters", createMockWidgetParameters(null, pathTothanks));
        Assert.assertEquals(null, formCompoundMixinBean.getContentBeanPath());
    }
    
    private HippoBean createMockContentBean(String contentBeanPath) {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }
    
    private FormWidgetParameters createMockWidgetParameters(String formBeanPath, String thanksBeanPath) {
        FormWidgetParameters mock = EasyMock.createMock(FormWidgetParameters.class);
        EasyMock.expect(mock.getThanksPicker()).andReturn(createMockContentBean(thanksBeanPath)).anyTimes();
        if(formBeanPath!=null){
            EasyMock.expect(mock.getFormPicker()).andReturn(createMockContentBean(formBeanPath)).anyTimes();           
        }else{
            EasyMock.expect(mock.getFormPicker()).andReturn(null).anyTimes();
        }
        EasyMock.replay(mock);
        return mock;
    }
}
