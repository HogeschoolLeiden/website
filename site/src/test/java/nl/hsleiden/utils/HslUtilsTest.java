package nl.hsleiden.utils;

import org.easymock.EasyMock;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.configuration.hosting.VirtualHost;
import org.hippoecm.hst.configuration.hosting.VirtualHosts;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.manager.ObjectBeanManager;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.linking.HstLinkCreator;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.request.ResolvedMount;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.junit.Assert;
import org.junit.Test;

public class HslUtilsTest {

    private static final String BEAN_PATH = "/bean/path";
    private static final String SITE = "/site";
    private static final String CTX_URL_FORM = "/site/context/path/link";
    private static final String NO_CTX_URL_FORM = "/context/path/link";

    @Test
    public void getContextPathInUrlSiteTest() {

        MockHstRequestContext requestContextMock = new MockHstRequestContext();

        requestContextMock.setVirtualHost(getVirtualHostMock(SITE, true));
        requestContextMock.setResolvedMount(getResolvedMountMock(true));

        MockHstRequest requestMock = new MockHstRequest();
        requestMock.setRequestContext(requestContextMock);
        requestMock.setContextPath(SITE);

        String actual = HslUtils.getContextPath(requestMock);
        Assert.assertEquals(SITE, actual);
    }

    @Test
    public void getContextPathNotInUrlTest() {

        MockHstRequestContext requestContextMock = new MockHstRequestContext();
        MockHstRequest requestMock = new MockHstRequest();

        requestContextMock.setVirtualHost(getVirtualHostMock(SITE, true));
        requestContextMock.setResolvedMount(getResolvedMountMock(false));

        requestMock.setRequestContext(requestContextMock);
        requestMock.setContextPath(SITE);

        String actual = HslUtils.getContextPath(requestMock);
        Assert.assertEquals("", actual);
    }

    @Test
    public void getContextPathEmptyTest() {

        MockHstRequestContext requestContextMock = new MockHstRequestContext();
        MockHstRequest requestMock = new MockHstRequest();

        requestContextMock.setVirtualHost(getVirtualHostMock("", false));
        requestContextMock.setResolvedMount(getResolvedMountMock(false));

        requestMock.setRequestContext(requestContextMock);
        requestMock.setContextPath("");

        String actual = HslUtils.getContextPath(requestMock);
        Assert.assertEquals("", actual);
    }

    @Test
    public void getNamespacedFieldNameReleaseDateTest() {
        String displayName = Constants.DisplayedFieldNames.RELEASE_DATE;
        String actual = HslUtils.getNamespacedFieldName(displayName);
        Assert.assertEquals(Constants.FieldName.RELEASE_DATE, actual);
    }

    @Test
    public void getNamespacedFieldNameTitleTest() {
        String displayName = Constants.DisplayedFieldNames.TITLE;
        String actual = HslUtils.getNamespacedFieldName(displayName);
        Assert.assertEquals(Constants.FieldName.TITLE, actual);
    }

    @Test
    public void getNamespacedFieldNameNothingTest() {
        String displayName = "nothing";
        String actual = HslUtils.getNamespacedFieldName(displayName);
        Assert.assertEquals("", actual);
    }

    @Test
    public void getSelectedBeanTest() throws IllegalStateException, ObjectBeanManagerException {

        MockHstRequest requestMock = new MockHstRequest();
        requestMock.setRequestContext(createMockHstRequestContext(BEAN_PATH, false, null, true));
        HippoBean selectedBean = HslUtils.getSelectedBean(requestMock, BEAN_PATH);
        Assert.assertEquals(BEAN_PATH, selectedBean.getPath());
    }

    @Test
    public void getSelectedBeanExceptionTest() throws IllegalStateException, ObjectBeanManagerException {
        HippoBean selectedBean = null;
        try {
            MockHstRequest requestMock = new MockHstRequest();
            requestMock.setRequestContext(createMockHstRequestContext(BEAN_PATH, true, null, false));
            selectedBean = HslUtils.getSelectedBean(requestMock, BEAN_PATH);
        } catch (HstComponentException e) {
            Assert.assertEquals(selectedBean, null);
        }
    }

    @Test
    public void getRelativeUrlPathContextPathShowTest() {
        MockHstRequest requestMock = new MockHstRequest();
        MockHstRequestContext requestContextMock = new MockHstRequestContext();
        requestContextMock.setResolvedMount(getResolvedMountMock(true));
        requestMock.setRequestContext(requestContextMock);
        String relativeUrlPath = HslUtils.getRelativeUrlPath(requestMock, CTX_URL_FORM);
        Assert.assertEquals(relativeUrlPath, NO_CTX_URL_FORM);
    }

    @Test
    public void getRelativeUrlPathNoContextPathShowTest() {
        MockHstRequest requestMock = new MockHstRequest();
        MockHstRequestContext requestContextMock = new MockHstRequestContext();
        requestContextMock.setResolvedMount(getResolvedMountMock(true));
        requestMock.setRequestContext(requestContextMock);
        String relativeUrlPath = HslUtils.getRelativeUrlPath(requestMock, NO_CTX_URL_FORM);
        Assert.assertEquals(relativeUrlPath, NO_CTX_URL_FORM);
    }

    @Test
    public void getRelativeUrlPathContextPathNotShowTest() {
        MockHstRequest requestMock = new MockHstRequest();
        MockHstRequestContext requestContextMock = new MockHstRequestContext();
        requestContextMock.setResolvedMount(getResolvedMountMock(false));
        requestMock.setRequestContext(requestContextMock);
        String relativeUrlPath = HslUtils.getRelativeUrlPath(requestMock, CTX_URL_FORM);
        Assert.assertEquals(relativeUrlPath, CTX_URL_FORM);
    }

    @Test
    public void getRelativeUrlPathNoContextPathNoShowTest() {
        MockHstRequest requestMock = new MockHstRequest();
        MockHstRequestContext requestContextMock = new MockHstRequestContext();
        requestContextMock.setResolvedMount(getResolvedMountMock(false));
        requestMock.setRequestContext(requestContextMock);
        String relativeUrlPath = HslUtils.getRelativeUrlPath(requestMock, NO_CTX_URL_FORM);
        Assert.assertEquals(relativeUrlPath, NO_CTX_URL_FORM);
    }

    @Test
    public void getMatchingSitemapTest() throws IllegalStateException, ObjectBeanManagerException {
        MockHstRequest requestMock = new MockHstRequest();
        requestMock.setRequestContext(createMockHstRequestContext(BEAN_PATH, false, CTX_URL_FORM, false));
        String matchingSitemap = HslUtils.getMatchingSitemap(requestMock, BEAN_PATH);
        Assert.assertEquals(matchingSitemap, CTX_URL_FORM);
    }
    
    @Test
    public void getMatchingSitemapExceptionTest() throws IllegalStateException, ObjectBeanManagerException {
        String matchingSitemap = null;
        try {
            MockHstRequest requestMock = new MockHstRequest();
            requestMock.setRequestContext(createMockHstRequestContext(BEAN_PATH, true, CTX_URL_FORM, false));
            matchingSitemap = HslUtils.getMatchingSitemap(requestMock, BEAN_PATH);
        } catch (ObjectBeanManagerException e) {
            Assert.assertEquals(matchingSitemap, null);
        }
    }

    private ObjectBeanManager createObjectBeanManagerMock(String contentBeanPath, boolean throwException)
            throws ObjectBeanManagerException {
        ObjectBeanManager mock = EasyMock.createMock(ObjectBeanManager.class);

        if (!throwException) {
            HippoBean scopeBean = getMockContentBean(contentBeanPath);
            EasyMock.expect(mock.getObject(contentBeanPath)).andReturn(scopeBean).anyTimes();
        } else {
            EasyMock.expect(mock.getObject(contentBeanPath)).andThrow(new ObjectBeanManagerException());
        }

        EasyMock.replay(mock);
        return mock;
    }

    private HstRequestContext createMockHstRequestContext(String beanPath, boolean throwException, String urlForm,
            Boolean contextPathInUrl) throws IllegalStateException, ObjectBeanManagerException {

        HstRequestContext mock = EasyMock.createMock(HstRequestContext.class);

        EasyMock.expect(mock.getObjectBeanManager()).andReturn(createObjectBeanManagerMock(beanPath, throwException))
                .anyTimes();

        if (urlForm != null) {
            HstLinkCreator hstLinkCreatorMock = getHstLinkCreatorMock(mock, urlForm);
            EasyMock.expect(mock.getHstLinkCreator()).andReturn(hstLinkCreatorMock).anyTimes();
            EasyMock.expect(mock.getResolvedMount()).andReturn(getResolvedMountMock(contextPathInUrl));

        }

        EasyMock.replay(mock);
        return mock;
    }

    private HstLinkCreator getHstLinkCreatorMock(HstRequestContext reqContext, String urlForm) {
        HstLinkCreator mock = EasyMock.createMock(HstLinkCreator.class);

        HstLink hstLinkMock = createHstLinkMock(reqContext, urlForm);

        EasyMock.expect(mock.create(EasyMock.anyObject(HippoBean.class), EasyMock.anyObject(HstRequestContext.class)))
                .andReturn(hstLinkMock).anyTimes();

        EasyMock.replay(mock);
        return mock;
    }

    private HstLink createHstLinkMock(HstRequestContext reqContext, String urlForm) {
        HstLink mock = EasyMock.createMock(HstLink.class);
        EasyMock.expect(mock.toUrlForm(reqContext, false)).andReturn(urlForm).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private HippoBean getMockContentBean(String contentBeanPath) {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private ResolvedMount getResolvedMountMock(Boolean contextPathInUrl) {
        ResolvedMount resolvedMountMock = EasyMock.createMock(ResolvedMount.class);
        Mount mountMock = EasyMock.createMock(Mount.class);

        EasyMock.expect(mountMock.isContextPathInUrl()).andReturn(contextPathInUrl);
        EasyMock.expect(mountMock.getVirtualHost()).andReturn(getVirtualHostMock(SITE, true));
        EasyMock.expect(resolvedMountMock.getMount()).andReturn(mountMock);

        EasyMock.replay(resolvedMountMock, mountMock);
        return resolvedMountMock;
    }

    private VirtualHost getVirtualHostMock(String defaultCtxPath, boolean ctxPathInUrl) {
        VirtualHost virtualHostMock = EasyMock.createMock(VirtualHost.class);
        VirtualHosts virtualHostsMock = EasyMock.createMock(VirtualHosts.class);

        EasyMock.expect(virtualHostsMock.getDefaultContextPath()).andReturn(defaultCtxPath);
        EasyMock.expect(virtualHostsMock.isContextPathInUrl()).andReturn(ctxPathInUrl);
        EasyMock.expect(virtualHostMock.getVirtualHosts()).andReturn(virtualHostsMock);

        EasyMock.replay(virtualHostMock, virtualHostsMock);
        return virtualHostMock;
    }

}
