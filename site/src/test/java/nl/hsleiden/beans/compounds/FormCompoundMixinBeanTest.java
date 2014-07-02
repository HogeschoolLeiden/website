package nl.hsleiden.beans.compounds;

import hslbeans.FormWidgetParameters;
import nl.hsleiden.utils.TestUtils;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.Assert;
import org.junit.Test;


public class FormCompoundMixinBeanTest {

    private static final String PATH_TO_FORM = "/path/to/form";
    private static final String PATH_TO_THANK_YOU_PAGE = "/path/to/thankYouPage";

    @Test
    public void getContentBeanPathTest() throws NoSuchFieldException, IllegalAccessException {
        String pathToForm = PATH_TO_FORM;
        String pathTothanks = PATH_TO_THANK_YOU_PAGE;
        FormCompoundMixinBean formCompoundMixinBean = new FormCompoundMixinBean();
        TestUtils.setPrivateField(formCompoundMixinBean, "formWidgetParameters", createMockWidgetParameters(pathToForm, pathTothanks));
        Assert.assertEquals(pathToForm, formCompoundMixinBean.getContentBeanPath());
    }

    @Test
    public void getContentBeanPathNullTest() throws NoSuchFieldException, IllegalAccessException {
        String pathTothanks = PATH_TO_THANK_YOU_PAGE;
        FormCompoundMixinBean formCompoundMixinBean = new FormCompoundMixinBean();
        TestUtils.setPrivateField(formCompoundMixinBean, "formWidgetParameters", createMockWidgetParameters(null, pathTothanks));
        Assert.assertEquals(null, formCompoundMixinBean.getContentBeanPath());
    }
    
    @Test
    public void getThanksBeanPathTest() throws NoSuchFieldException, IllegalAccessException{
        String pathTothanks = PATH_TO_THANK_YOU_PAGE;
        FormCompoundMixinBean formCompoundMixinBean = new FormCompoundMixinBean();
        TestUtils.setPrivateField(formCompoundMixinBean, "formWidgetParameters", createMockWidgetParameters(null, pathTothanks));
        Assert.assertEquals(pathTothanks, formCompoundMixinBean.getThanksBeanPath());
    }

    @Test
    public void getThanksBeanPathNullTest() throws NoSuchFieldException, IllegalAccessException{
        FormCompoundMixinBean formCompoundMixinBean = new FormCompoundMixinBean();
        TestUtils.setPrivateField(formCompoundMixinBean, "formWidgetParameters", createMockWidgetParameters(PATH_TO_FORM, null));
        Assert.assertEquals(null, formCompoundMixinBean.getThanksBeanPath());
    }
    
    @Test
    public void getUseMixinTest(){
        FormCompoundMixinBean formCompoundMixinBean = new FormCompoundMixinBean();
        try{
            formCompoundMixinBean.getUseMixin();  
        }catch(UnsupportedOperationException e){
            Assert.assertNotEquals(e, null);
        }
    }
    
    private HippoBean createMockContentBean(String contentBeanPath) {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }
    
    private FormWidgetParameters createMockWidgetParameters(String formBeanPath, String thanksBeanPath) {
        FormWidgetParameters mock = EasyMock.createMock(FormWidgetParameters.class);
        if(thanksBeanPath!=null){
            EasyMock.expect(mock.getThanksPicker()).andReturn(createMockContentBean(thanksBeanPath)).anyTimes();
        }else{
            EasyMock.expect(mock.getThanksPicker()).andReturn(null).anyTimes();
        }
        
        if(formBeanPath!=null){
            EasyMock.expect(mock.getFormPicker()).andReturn(createMockContentBean(formBeanPath)).anyTimes();           
        }else{
            EasyMock.expect(mock.getFormPicker()).andReturn(null).anyTimes();
        }
        EasyMock.replay(mock);
        return mock;
    }
}
