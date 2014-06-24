package nl.hsleiden.utils;

import static org.easymock.EasyMock.*;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.configuration.hosting.VirtualHost;
import org.hippoecm.hst.configuration.hosting.VirtualHosts;
import org.hippoecm.hst.core.request.ResolvedMount;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.junit.Assert;
import org.junit.Test;

public class HslUtilsTest {

    private static final String SITE = "site";

    @Test
    public void getContextPathInUrlSiteTest() {

        MockHstRequestContext requestContextMock = new MockHstRequestContext();

        requestContextMock.setVirtualHost(getVirtualHostMock());
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

        requestContextMock.setVirtualHost(getVirtualHostMock());
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

        requestContextMock.setVirtualHost(getVirtualHostMockEmptyDefaaultContextPath());
        requestContextMock.setResolvedMount(getResolvedMountMock(false));

        requestMock.setRequestContext(requestContextMock);
        requestMock.setContextPath("");

        String actual = HslUtils.getContextPath(requestMock);
        Assert.assertEquals("", actual);
    }
    
    @Test
    public void getNamespacedFieldNameReleaseDateTest(){
        String displayName = Constants.DisplayedFieldNames.RELEASE_DATE;
        String actual = HslUtils.getNamespacedFieldName(displayName);
        Assert.assertEquals(Constants.FieldName.RELEASE_DATE, actual);
    }

    @Test
    public void getNamespacedFieldNameTitleTest(){
        String displayName = Constants.DisplayedFieldNames.TITLE;
        String actual = HslUtils.getNamespacedFieldName(displayName);
        Assert.assertEquals(Constants.FieldName.TITLE, actual);
    }

    @Test
    public void getNamespacedFieldNameNothingTest(){
        String displayName = "nothing";
        String actual = HslUtils.getNamespacedFieldName(displayName);
        Assert.assertEquals("", actual);
    }

    private VirtualHost getVirtualHostMock() {
        VirtualHost virtualHostMock = createMock(VirtualHost.class);
        VirtualHosts virtualHostsMock = createMock(VirtualHosts.class);

        expect(virtualHostsMock.getDefaultContextPath()).andReturn(SITE);
        expect(virtualHostMock.getVirtualHosts()).andReturn(virtualHostsMock);

        replay(virtualHostMock, virtualHostsMock);
        return virtualHostMock;
    }

    private VirtualHost getVirtualHostMockEmptyDefaaultContextPath() {
        VirtualHost virtualHostMock = createMock(VirtualHost.class);
        VirtualHosts virtualHostsMock = createMock(VirtualHosts.class);

        expect(virtualHostsMock.getDefaultContextPath()).andReturn("");
        expect(virtualHostMock.getVirtualHosts()).andReturn(virtualHostsMock);

        replay(virtualHostMock, virtualHostsMock);
        return virtualHostMock;
    }

    private ResolvedMount getResolvedMountMock(Boolean contextPathInUrl) {
        ResolvedMount resolvedMountMock = createMock(ResolvedMount.class);
        Mount mountMock = createMock(Mount.class);

        expect(mountMock.isContextPathInUrl()).andReturn(contextPathInUrl);
        expect(resolvedMountMock.getMount()).andReturn(mountMock);

        replay(resolvedMountMock, mountMock);
        return resolvedMountMock;
    }

}
