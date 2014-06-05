package nl.hsleiden.components.base;

import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.mock.content.beans.standard.MockHippoBean;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.mock.core.component.MockHstResponse;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.junit.Assert;
import org.junit.Test;

public class DetailTest extends BaseHstComponent {

    @Test
    public void doBeforeRender() {
        MockHstRequest requestMock = new MockHstRequest();
        MockHstResponse responseMock = new MockHstResponse();
        MockHippoBean hippoBeanMock = new MockHippoBean(); 
        MockHstRequestContext requestContextMock = new MockHstRequestContext();
        
        requestContextMock.setContentBean(hippoBeanMock);
        requestMock.setRequestContext(requestContextMock);
       
        new Detail().doBeforeRender(requestMock, responseMock);
        Assert.assertEquals(hippoBeanMock, requestMock.getAttribute(Constants.Attributes.DOCUMENT));
    }

    @Test
    public void doBeforeRenderNullBean() {
        MockHstRequest requestMock = new MockHstRequest();
        MockHstResponse responseMock = new MockHstResponse();
        MockHstRequestContext requestContextMock = new MockHstRequestContext();
        
        requestContextMock.setContentBean(null);
        requestMock.setRequestContext(requestContextMock);
        
        new Detail().doBeforeRender(requestMock, responseMock);
        Assert.assertEquals(null, requestMock.getAttribute(Constants.Attributes.DOCUMENT));
    }
}
