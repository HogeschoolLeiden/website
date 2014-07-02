package nl.hsleiden.tags;

import hslbeans.Basedocument;
import hslbeans.WebPage;
import nl.hsleiden.channels.WebsiteInfo;

import org.easymock.EasyMock;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoResource;
import org.hippoecm.hst.core.request.ResolvedMount;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.junit.Assert;
import org.junit.Test;


public class FunctionsTest {

    private static final String BEAN_PATH_TO_FILE_TXT = "/bean/path/to/file.txt";

    @Test
    public void isSubclassOfWebPageSuccess() {
        boolean actual = Functions.isSubclassOfWebPage(new WebPage());
        Assert.assertEquals(true, actual);
    }
    
    @Test
    public void isSubclassOfWebPageFailure() {
        boolean actual = Functions.isSubclassOfWebPage(new Basedocument());
        Assert.assertEquals(false, actual);
    }

    @Test
    public void getDefaultBrowserTitle() {
        String parameterInfoValue = "My browser title";
        ResolvedMount resolvedMountMock = mockResolvedMount(parameterInfoValue);
        
        MockHstRequest request = new MockHstRequest();
        MockHstRequestContext requestContext = new MockHstRequestContext();
        
        requestContext.setResolvedMount(resolvedMountMock);
        request.setRequestContext(requestContext);
        
        String actual = Functions.getDefaultBrowserTitle(request);
        Assert.assertEquals(parameterInfoValue, actual);
    }
    
    @Test
    public void getAssetTitle(){
        String assetTitle = Functions.getAssetTitle(createHippoResourseMock(BEAN_PATH_TO_FILE_TXT));
        Assert.assertEquals(assetTitle, "file.txt");
    }

    private HippoResource createHippoResourseMock(String beanPath) {
        HippoResource mock = EasyMock.createMock(HippoResource.class);
        EasyMock.expect(mock.getParentBean()).andReturn(getMockContentBean(beanPath));
        EasyMock.replay(mock);
        return mock;
    }

    private ResolvedMount mockResolvedMount(String parameterInfoValue) {
        ResolvedMount resolvedMountMock = EasyMock.createMock(ResolvedMount.class);
        Mount mountMock = EasyMock.createMock(Mount.class);
        WebsiteInfo infoMock = EasyMock.createMock(WebsiteInfo.class);
        EasyMock.expect(infoMock.getDefaultBrowserTitle()).andReturn(parameterInfoValue);
        EasyMock.expect(mountMock.getChannelInfo()).andReturn(infoMock);
        EasyMock.expect(resolvedMountMock.getMount()).andReturn(mountMock);
        EasyMock.replay(resolvedMountMock, mountMock, infoMock);
        return resolvedMountMock;
    }
    
    private HippoBean getMockContentBean(String contentBeanPath) {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }
}
