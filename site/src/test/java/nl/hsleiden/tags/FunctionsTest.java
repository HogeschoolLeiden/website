package nl.hsleiden.tags;

import static org.easymock.EasyMock.*;

import hslbeans.WebPage;
import hslbeans.Basedocument;
import nl.hsleiden.channels.WebsiteInfo;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.core.request.ResolvedMount;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.junit.Assert;
import org.junit.Test;


public class FunctionsTest {

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

    private ResolvedMount mockResolvedMount(String parameterInfoValue) {
        ResolvedMount resolvedMountMock = createMock(ResolvedMount.class);
        Mount mountMock = createMock(Mount.class);
        WebsiteInfo infoMock = createMock(WebsiteInfo.class);
        expect(infoMock.getDefaultBrowserTitle()).andReturn(parameterInfoValue);
        expect(mountMock.getChannelInfo()).andReturn(infoMock);
        expect(resolvedMountMock.getMount()).andReturn(mountMock);
        replay(resolvedMountMock, mountMock, infoMock);
        return resolvedMountMock;
    }
}
