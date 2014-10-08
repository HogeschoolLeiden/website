package nl.hsleiden.components;

import static org.easymock.EasyMock.*;

import nl.hsleiden.channels.WebsiteInfo;
import nl.hsleiden.utils.Constants.Attributes;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.beans.ObjectBeanPersistenceException;
import org.hippoecm.hst.core.request.ResolvedMount;
import org.hippoecm.hst.mock.content.beans.manager.MockObjectBeanPersistenceManager;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.mock.core.component.MockHstResponse;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.junit.Assert;
import org.junit.Test;

public class HeaderTest {
    
    @Test
    public void doBeforeRenderLogoNull() throws ObjectBeanPersistenceException{
        
        String logoPathValue = "/content/gallery/hsl/logos/logo.png";
        String headerNameValue = "HeaderName";
        String headerIntroTitle = "HeaderIntroTitle";
        
        MockHstRequest requestMock = new MockHstRequest();
        MockHstResponse responseMock = new MockHstResponse();
        MockHstRequestContext requestContextMock = new MockHstRequestContext();
        MockObjectBeanPersistenceManager obpmMock = new MockObjectBeanPersistenceManager();
        obpmMock.setObject(logoPathValue, null);
        
        requestContextMock.setResolvedMount(getResolvedMountMock(headerNameValue, logoPathValue, headerIntroTitle));
        requestContextMock.setDefaultObjectBeanManager(obpmMock);
        requestMock.setRequestContext(requestContextMock);
                
        new Header().doBeforeRender(requestMock, responseMock);
        Assert.assertEquals(headerNameValue, requestMock.getAttribute(Attributes.HEADER_NAME));
        Assert.assertEquals(null, requestMock.getAttribute(Attributes.LOGO));
    }

    @Test
    public void doBeforeRenderInfoNull() throws ObjectBeanPersistenceException{
          
        MockHstRequest requestMock = new MockHstRequest();
        MockHstResponse responseMock = new MockHstResponse();
        MockHstRequestContext requestContextMock = new MockHstRequestContext();
        MockObjectBeanPersistenceManager obpmMock = new MockObjectBeanPersistenceManager();
        obpmMock.setObject("", null);
        
        requestContextMock.setResolvedMount(getResolvedMountMockInfoNull());
        requestContextMock.setDefaultObjectBeanManager(obpmMock);
        requestMock.setRequestContext(requestContextMock);
        
        new Header().doBeforeRender(requestMock, responseMock);
        Assert.assertEquals(null, requestMock.getAttribute(Attributes.HEADER_NAME));
        Assert.assertEquals(null, requestMock.getAttribute(Attributes.LOGO));
    }
    
   
    private ResolvedMount getResolvedMountMock(String headerNameValue, String logoPathValue, String headerIntroTitle) {
        ResolvedMount resolvedMountMock = createMock(ResolvedMount.class);
        Mount mountMock = createMock(Mount.class);
        WebsiteInfo infoMock = createMock(WebsiteInfo.class);
       
        expect(infoMock.getHeaderName()).andReturn(headerNameValue);
        expect(infoMock.getHeaderIntroTitle()).andReturn(headerIntroTitle);
        expect(infoMock.getLogoPath()).andReturn(logoPathValue);
        expect(mountMock.getChannelInfo()).andReturn(infoMock);
        expect(resolvedMountMock.getMount()).andReturn(mountMock);
        replay(resolvedMountMock, mountMock, infoMock);
        return resolvedMountMock;
    }

    private ResolvedMount getResolvedMountMockInfoNull() {
        ResolvedMount resolvedMountMock = createMock(ResolvedMount.class);
        Mount mountMock = createMock(Mount.class);
        expect(mountMock.getMountPath()).andReturn("test");
        expect(mountMock.getChannelInfo()).andReturn(null);
        expect(resolvedMountMock.getMount()).andReturn(mountMock);
        replay(resolvedMountMock, mountMock);
        return resolvedMountMock;
    }    
}
