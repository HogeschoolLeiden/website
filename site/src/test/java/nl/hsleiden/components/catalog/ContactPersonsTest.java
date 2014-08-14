package nl.hsleiden.components.catalog;

import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import nl.hsleiden.componentsinfo.ContactPersonsInfo;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.manager.ObjectBeanManager;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.utils.ParameterUtils;
import org.junit.Assert;
import org.junit.Test;

public class ContactPersonsTest {

    private static final String REPOSITORY_EXCEPTION_MESSAGE = "repository exception";
    private static final String WIDGET_TITLE = "widgetTitle";
    private static final String FIRST_CONTACT_PATH = "/first/contact/bean/path";
    private static final String SECOND_CONTACT_PATH = "/second/contact/bean/path";
    private static final String THIRD_CONTACT_PATH = "/third/contact/bean/path";
    private static final int SIZE = 3;
    
    @SuppressWarnings("unchecked")
    @Test
    public void getModelTestMixinFalse() throws ObjectBeanManagerException,
            IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException, RepositoryException {

        ContactPersonsInfo myInfoMock = createContactPersonsInfoMock(WIDGET_TITLE, false, FIRST_CONTACT_PATH, SECOND_CONTACT_PATH, THIRD_CONTACT_PATH, false);

        ContactPersons contactPersons = new ContactPersons();
        
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMock);
        
        HstRequestContext requestContext = createMockHstRequestContext(FIRST_CONTACT_PATH, SECOND_CONTACT_PATH, THIRD_CONTACT_PATH,
                myInfoMock, false, false, true);
        request.setRequestContext(requestContext);
        
        Map<String, Object> model = contactPersons.getModel(request, null);
        Assert.assertEquals(((List<HippoBean>) model.get("items")).size(), SIZE);
        Assert.assertEquals(model.get("info"), myInfoMock);
    }

    @Test
    public void getModelTestMixinFalseNoContacts() throws ObjectBeanManagerException,
    IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException, RepositoryException {
        
        ContactPersonsInfo myInfoMock = createContactPersonsInfoMock(WIDGET_TITLE, false, null, null, null, false);
        
        ContactPersons contactPersons = new ContactPersons();
        
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMock);
        
        HstRequestContext requestContext = createMockHstRequestContext(FIRST_CONTACT_PATH, SECOND_CONTACT_PATH, THIRD_CONTACT_PATH,
                myInfoMock, false, false, false);
        request.setRequestContext(requestContext);
        
        Map<String, Object> model = contactPersons.getModel(request, null);
        Assert.assertEquals(model.get("items"), null);
        Assert.assertEquals(model.get("info"), myInfoMock);
        Assert.assertEquals(request.getAttribute("webMasterMessage"), "webmaster.nocontacts.message");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getModelTestMixinNull() throws ObjectBeanManagerException,
    IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException, RepositoryException {
        
        ContactPersonsInfo myInfoMock = createContactPersonsInfoMock(WIDGET_TITLE, false, FIRST_CONTACT_PATH, SECOND_CONTACT_PATH, THIRD_CONTACT_PATH, true);
        
        ContactPersons contactPersons = new ContactPersons();
        
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMock);
        
        HstRequestContext requestContext = createMockHstRequestContext(FIRST_CONTACT_PATH, SECOND_CONTACT_PATH, THIRD_CONTACT_PATH,
                myInfoMock, false, false, false);
        request.setRequestContext(requestContext);
        
        Map<String, Object> model = contactPersons.getModel(request, null);
        Assert.assertEquals(((List<HippoBean>) model.get("items")).size(), SIZE);
    }

    @Test
    public void getModelTestMixinTrue() throws ObjectBeanManagerException,
    IllegalStateException, QueryException, NoSuchFieldException, IllegalAccessException, RepositoryException {
        
        ContactPersonsInfo myInfoMock = createContactPersonsInfoMock(WIDGET_TITLE, true, FIRST_CONTACT_PATH, SECOND_CONTACT_PATH, THIRD_CONTACT_PATH, false);
        
        ContactPersons contactPersons = new ContactPersons();
        
        MockHstRequest request = new MockHstRequest();
        request.setAttribute(ParameterUtils.MY_MOCK_PARAMETER_INFO, myInfoMock);
        
        HstRequestContext requestContext;
        try {
            requestContext = createMockHstRequestContext(FIRST_CONTACT_PATH, SECOND_CONTACT_PATH, THIRD_CONTACT_PATH,
                    myInfoMock, false, false, false);
            request.setRequestContext(requestContext);            
            contactPersons.getModel(request, null);

        } catch (HstComponentException e) {
            Assert.assertEquals(e.getMessage(), REPOSITORY_EXCEPTION_MESSAGE);
        }
    }
    
    private HstRequestContext createMockHstRequestContext(String firstContactBeanPath, String secondContactBeanPath, String thirdContactBeanPath,
            ContactPersonsInfo info, boolean throwRepositoryException, boolean addMixin, boolean contentBeanNull)
            throws ObjectBeanManagerException, IllegalStateException, QueryException, NoSuchFieldException,
            IllegalAccessException, RepositoryException {
            HstRequestContext mock = EasyMock.createMock(HstRequestContext.class);
            HippoBean firstContact = null;
            HippoBean secondContact = null;
            HippoBean thirdContact = null;
            if (firstContactBeanPath != null) {
                firstContact = createMockContentBean(firstContactBeanPath, false);
            }
            if (secondContactBeanPath != null) {
                secondContact = createMockContentBean(secondContactBeanPath, false);
            }
            if (thirdContactBeanPath != null) {
                thirdContact = createMockContentBean(thirdContactBeanPath, false);
            }

            if(contentBeanNull){                
                EasyMock.expect(mock.getContentBean()).andReturn(null).anyTimes();
            }else{
                EasyMock.expect(mock.getContentBean()).andReturn(createMockContentBean("/content/bean/path", true)).anyTimes();
            }
            EasyMock.expect(mock.getObjectBeanManager())
                    .andReturn(createObjectBeanManagerMock(firstContactBeanPath, firstContact, secondContactBeanPath, secondContact, thirdContactBeanPath, thirdContact))
                    .anyTimes();
            EasyMock.replay(mock);
            return mock;
    }
    
    private HippoBean createMockContentBean(String contentBeanPath, boolean getMixinThrowException) throws RepositoryException {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        if(getMixinThrowException){
            EasyMock.expect(mock.getNode()).andReturn(createMockNodeBean(contentBeanPath)).anyTimes();
        }
        EasyMock.replay(mock);
        return mock;
    }

    private Node createMockNodeBean(String contentBeanPath) throws RepositoryException {
        Node mock = EasyMock.createMock(Node.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.expect(mock.getMixinNodeTypes()).andThrow(new RepositoryException(REPOSITORY_EXCEPTION_MESSAGE)).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }
    
    private ObjectBeanManager createObjectBeanManagerMock(String firstContactBeanPath, HippoBean firstContact,
            String secondContactBeanPath, HippoBean secondContact, String thirdContactBeanPath, HippoBean thirdContact) throws ObjectBeanManagerException {
        ObjectBeanManager mock = EasyMock.createMock(ObjectBeanManager.class);
        EasyMock.expect(mock.getObject(firstContactBeanPath)).andReturn(firstContact).anyTimes();
        EasyMock.expect(mock.getObject(secondContactBeanPath)).andReturn(secondContact).anyTimes();
        EasyMock.expect(mock.getObject(thirdContactBeanPath)).andReturn(thirdContact).anyTimes();
        EasyMock.expect(mock.getObject(null)).andReturn(null).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }
    
    
    private static ContactPersonsInfo createContactPersonsInfoMock(String widgetTitle, Boolean useMixin, String firstContactPath,
             String secondContactPath, String thirdContactPath, boolean mixinNull) {

        ContactPersonsInfo mock = EasyMock.createMock(ContactPersonsInfo.class);

        EasyMock.expect(mock.getWidgetTitle()).andReturn(widgetTitle).anyTimes();
        
        if(!mixinNull){            
            EasyMock.expect(mock.getUseMixin()).andReturn(useMixin).anyTimes();
        }else{
            EasyMock.expect(mock.getUseMixin()).andReturn(null).anyTimes();            
        }
        
        if(firstContactPath!=null){            
            EasyMock.expect(mock.getFirstContact()).andReturn(firstContactPath).anyTimes();
        }else{
            EasyMock.expect(mock.getFirstContact()).andReturn(null).anyTimes();
        }
        
        if(secondContactPath!=null){ 
            EasyMock.expect(mock.getSecondContact()).andReturn(secondContactPath).anyTimes();
        }else{
            EasyMock.expect(mock.getSecondContact()).andReturn(null).anyTimes();
        }
        
        if(thirdContactPath!=null){ 
            EasyMock.expect(mock.getThirdContact()).andReturn(thirdContactPath).anyTimes();
        }else{
            EasyMock.expect(mock.getThirdContact()).andReturn(null).anyTimes();
        }

        EasyMock.replay(mock);
        return mock;
    }
    
}
