package nl.hsleiden.beans.compounds;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import nl.hsleiden.utils.TestUtils;
import nl.openweb.jcr.mock.MockNode;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.Assert;
import org.junit.Test;

public class CalendarEventsCompoundMixinBeanTest {

    private static final String SCOPE_BEAN = "/path/to/scope/bean";
    private static final String SCOPE_UUID = "12345678990";

    @Test
    public void getScopeNullTest() throws NoSuchFieldException, IllegalAccessException, RepositoryException {
        CalendarEventsCompoundMixinBean calendaEventsCompoundMixinBean = new CalendarEventsCompoundMixinBean();
        
        Session session = TestUtils.getSessionForMirrorNode(SCOPE_UUID, "hippostd:folder", "");
        
        MockNode node = getMyDocumenNodeMock(session, SCOPE_UUID);
        
        calendaEventsCompoundMixinBean.setNode(node);
        calendaEventsCompoundMixinBean.setObjectConverter(TestUtils.getObjectConverter());
        
        Assert.assertEquals(null, calendaEventsCompoundMixinBean.getScope());
    }

    private MockNode getMyDocumenNodeMock(Session session, String referencedNodeUuid) throws RepositoryException {
        MockNode node = new MockNode(session);
        MockNode mirror = (MockNode) node.addNode("hsl:contentBeanPath", "hippo:mirror");
        mirror.setProperty("hippo:docbase", referencedNodeUuid);
        return node;
    }
    
    @Test
    public void getScopeTest() throws NoSuchFieldException, IllegalAccessException {
        CalendarEventsCompoundMixinBean calendaEventsCompoundMixinBean = new CalendarEventsCompoundMixinBean();
        TestUtils.setPrivateField(calendaEventsCompoundMixinBean, "contentBeanPath", createMockContentBean(SCOPE_BEAN));
        Assert.assertEquals(SCOPE_BEAN, calendaEventsCompoundMixinBean.getScope());
    }
    
    @Test
    public void getUseMixinTest() {
        CalendarEventsCompoundMixinBean calendaEventsCompoundMixinBean = new CalendarEventsCompoundMixinBean();
        try {
            calendaEventsCompoundMixinBean.getUseMixin();
        } catch (UnsupportedOperationException e) {
            Assert.assertNotEquals(e, null);
        }
    }

    private HippoBean createMockContentBean(String contentBeanPath) {
        HippoBean mock = null;
        if(contentBeanPath!=null){
            mock = EasyMock.createMock(HippoBean.class);
            EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
            EasyMock.replay(mock);
        }else{
            mock = EasyMock.createMock(HippoBean.class);
        }
        return mock;
    }
}
