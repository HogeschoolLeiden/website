package nl.hsleiden.components.catalog;

import hslbeans.ArticlePage;

import javax.jcr.RepositoryException;

import net.sourceforge.hstmixinsupport.DynamicProxyFactory;
import nl.hsleiden.componentsinfo.FormComponentInfo;
import nl.hsleiden.utils.TestUtils;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.manager.ObjectBeanManager;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.utils.ParameterUtils;
import org.junit.Assert;
import org.junit.Test;
import org.onehippo.forge.easyforms.beans.FormBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormComponentTest {

    private static final String CONTENT_BEAN_PATH = "/content/bean/path";

    private static final Logger LOG = LoggerFactory.getLogger(FormComponentTest.class);

    @Test
    public void getFormBeanNoMixinNullTest() throws ObjectBeanManagerException, IllegalStateException, QueryException,
            NoSuchFieldException, IllegalAccessException {

        // create the component we want to test
        FormComponent formComponent = new FormComponent();
        FormComponentInfo myInfoMock = createFormComponentInfoMock(false, CONTENT_BEAN_PATH);

        MockHstRequest request = createHstRequestMock(myInfoMock, null, false);

        FormBean formBean = formComponent.getFormBean(request);
        Assert.assertEquals(formBean, null);
    }

    @Test
    public void getFormBeanNoMixinFormBeanTest() throws ObjectBeanManagerException, IllegalStateException, QueryException,
    NoSuchFieldException, IllegalAccessException {
        
        // create the component we want to test
        FormComponent formComponent = new FormComponent();
        FormComponentInfo myInfoMock = createFormComponentInfoMock(false, CONTENT_BEAN_PATH);
        
        FormBean formBeanMock = createFormBeanMock(CONTENT_BEAN_PATH);
        MockHstRequest request = createHstRequestMock(myInfoMock, formBeanMock, true);
        
        FormBean formBean = formComponent.getFormBean(request);
        Assert.assertEquals(formBean, null);
    }

    private FormBean createFormBeanMock(String path) {
        FormBean mock = EasyMock.createMock(FormBean.class);
        EasyMock.expect(mock.getPath()).andReturn(path).anyTimes();
        EasyMock.expect(mock.isHippoDocumentBean()).andReturn(true).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    @Test
    public void getFormBeanMixinNullTest() throws ObjectBeanManagerException, IllegalStateException, QueryException,
            NoSuchFieldException, IllegalAccessException {

        FormComponent formComponent = new FormComponent();
        FormComponentInfo myInfoMock = createFormComponentInfoMock(true, CONTENT_BEAN_PATH);

        ArticlePage displayedArticlePage = new ArticlePage();
        TestUtils.setPrivateField(formComponent, "dynamicProxyFactory", createMockProxyFactory(displayedArticlePage, true));

        MockHstRequest request = createHstRequestMock(myInfoMock, displayedArticlePage, false);

        FormBean formBean = formComponent.getFormBean(request);
        Assert.assertEquals(formBean, null);
    }

    private HstRequestContext createMockHstRequestContext(FormComponentInfo info, HippoBean hb, boolean isHippoDocumentBean)
            throws ObjectBeanManagerException, IllegalStateException, QueryException, NoSuchFieldException,
            IllegalAccessException {

        HstRequestContext mock = EasyMock.createMock(HstRequestContext.class);
        EasyMock.expect(mock.getObjectBeanManager()).andReturn(createObjectBeanManagerMock(info.getContentBeanPath(), isHippoDocumentBean))
                .anyTimes();
        EasyMock.expect(mock.getContentBean()).andReturn(hb).anyTimes();

        EasyMock.replay(mock);
        return mock;
    }

    private static FormComponentInfo createFormComponentInfoMock(final Boolean useMixin, String contentBeanPath) {
        FormComponentInfo mock = EasyMock.createMock(FormComponentInfo.class);
        EasyMock.expect(mock.getUseMixin()).andReturn(useMixin).anyTimes();
        EasyMock.expect(mock.getContentBeanPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private ObjectBeanManager createObjectBeanManagerMock(String contentBeanPath, boolean isHippoDocumentBean) throws ObjectBeanManagerException {
        ObjectBeanManager mock = EasyMock.createMock(ObjectBeanManager.class);

        HippoBean scopeBean = createMockContentBean(contentBeanPath, isHippoDocumentBean);
        EasyMock.expect(mock.getObject(contentBeanPath)).andReturn(scopeBean).anyTimes();

        EasyMock.replay(mock);
        return mock;
    }

    private HippoBean createMockContentBean(String contentBeanPath, boolean isHippoDocumentBean) {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.expect(mock.isHippoDocumentBean()).andReturn(isHippoDocumentBean).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private MockHstRequest createHstRequestMock(FormComponentInfo myInfoMock, HippoBean bean, boolean isHippoDocumentBean)
            throws ObjectBeanManagerException, QueryException, NoSuchFieldException, IllegalAccessException {

        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMock);
        HstRequestContext requestContext = createMockHstRequestContext(myInfoMock, bean, isHippoDocumentBean);
        request.setRequestContext(requestContext);
        return request;
    }

    private DynamicProxyFactory createMockProxyFactory(HippoBean mockBean, boolean returnNull) {
        DynamicProxyFactory mock = EasyMock.createMock(DynamicProxyFactory.class);
        if (returnNull) {
            try {
                EasyMock.expect(mock.getProxy(mockBean)).andReturn(null);
            } catch (RepositoryException e) {
                LOG.error("RepositoryException");
            }
        } else {
            try {
                EasyMock.expect(mock.getProxy(mockBean)).andReturn(null);
            } catch (RepositoryException e) {
                LOG.error("RepositoryException");
            }
        }
        EasyMock.replay(mock);
        return mock;
    }

}
