package nl.hsleiden.beans.compounds;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.utils.TestUtils;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.Assert;
import org.junit.Test;

public class ContactPersonsCompoundMixinBeanTest {

    private static final String FIRST_CONTACT_BEAN = "/path/to/first/contact/bean";
    private static final String SECOND_CONTACT_BEAN = "/path/to/second/contact/bean";
    private static final String THIRD_CONTACT_BEAN = "/path/to/third/contact/bean";

    @Test
    public void getFirstContactTest() throws NoSuchFieldException, IllegalAccessException {
        ContactPersonsCompoundMixinBean cpCompoundMixinBean = new ContactPersonsCompoundMixinBean();
        TestUtils.setPrivateField(cpCompoundMixinBean, "contacts", createMockContacts(FIRST_CONTACT_BEAN, null, null, false));
        Assert.assertEquals(FIRST_CONTACT_BEAN, cpCompoundMixinBean.getFirstContact());
    }

    @Test
    public void getFirstContactNoItemTest() throws NoSuchFieldException, IllegalAccessException {
        ContactPersonsCompoundMixinBean cpCompoundMixinBean = new ContactPersonsCompoundMixinBean();
        TestUtils.setPrivateField(cpCompoundMixinBean, "contacts", createMockContacts(null, null, null, false));
        Assert.assertEquals(null, cpCompoundMixinBean.getFirstContact());
    }

    @Test
    public void getFirstContactPickerEmptyTest() throws NoSuchFieldException, IllegalAccessException {
        ContactPersonsCompoundMixinBean cpCompoundMixinBean = new ContactPersonsCompoundMixinBean();
        TestUtils.setPrivateField(cpCompoundMixinBean, "contacts", createMockContacts(FIRST_CONTACT_BEAN, null, null, true));
        Assert.assertEquals(null, cpCompoundMixinBean.getFirstContact());
    }

    @Test
    public void getSecondContactTest() throws NoSuchFieldException, IllegalAccessException {
        ContactPersonsCompoundMixinBean cpCompoundMixinBean = new ContactPersonsCompoundMixinBean();
        TestUtils.setPrivateField(cpCompoundMixinBean, "contacts", createMockContacts(FIRST_CONTACT_BEAN, SECOND_CONTACT_BEAN, null, false));
        Assert.assertEquals(SECOND_CONTACT_BEAN, cpCompoundMixinBean.getSecondContact());
    }

    @Test
    public void getSecondContactNoItemTest() throws NoSuchFieldException, IllegalAccessException {
        ContactPersonsCompoundMixinBean cpCompoundMixinBean = new ContactPersonsCompoundMixinBean();
        TestUtils.setPrivateField(cpCompoundMixinBean, "contacts", createMockContacts(FIRST_CONTACT_BEAN, null, null, false));
        Assert.assertEquals(null, cpCompoundMixinBean.getSecondContact());
    }

    @Test
    public void getSecondContactPickerEmptyTest() throws NoSuchFieldException, IllegalAccessException {
        ContactPersonsCompoundMixinBean cpCompoundMixinBean = new ContactPersonsCompoundMixinBean();
        TestUtils.setPrivateField(cpCompoundMixinBean, "contacts", createMockContacts(FIRST_CONTACT_BEAN, SECOND_CONTACT_BEAN, null, true));
        Assert.assertEquals(null, cpCompoundMixinBean.getSecondContact());
    }

    @Test
    public void getThirdContactTest() throws NoSuchFieldException, IllegalAccessException {
        ContactPersonsCompoundMixinBean cpCompoundMixinBean = new ContactPersonsCompoundMixinBean();
        TestUtils.setPrivateField(cpCompoundMixinBean, "contacts", createMockContacts(FIRST_CONTACT_BEAN, SECOND_CONTACT_BEAN, THIRD_CONTACT_BEAN, false));
        Assert.assertEquals(THIRD_CONTACT_BEAN, cpCompoundMixinBean.getThirdContact());
    }

    @Test
    public void getThirdContactNoItemTest() throws NoSuchFieldException, IllegalAccessException {
        ContactPersonsCompoundMixinBean cpCompoundMixinBean = new ContactPersonsCompoundMixinBean();
        TestUtils.setPrivateField(cpCompoundMixinBean, "contacts", createMockContacts(FIRST_CONTACT_BEAN, SECOND_CONTACT_BEAN, null, false));
        Assert.assertEquals(null, cpCompoundMixinBean.getThirdContact());
    }

    @Test
    public void getThirdContactEmptyPickerTest() throws NoSuchFieldException, IllegalAccessException {
        ContactPersonsCompoundMixinBean cpCompoundMixinBean = new ContactPersonsCompoundMixinBean();
        TestUtils.setPrivateField(cpCompoundMixinBean, "contacts", createMockContacts(FIRST_CONTACT_BEAN, SECOND_CONTACT_BEAN, THIRD_CONTACT_BEAN, true));
        Assert.assertEquals(null, cpCompoundMixinBean.getThirdContact());
    }
    
    @Test
    public void getUseMixinTest() {
        ContactPersonsCompoundMixinBean cpCompoundMixinBean = new ContactPersonsCompoundMixinBean();
        try {
            cpCompoundMixinBean.getUseMixin();
        } catch (UnsupportedOperationException e) {
            Assert.assertNotEquals(e, null);
        }
    }

    private HippoBean createMockContentBean(String contentBeanPath, boolean isPickerEmpty) {
        HippoBean mock = null;
        if(!isPickerEmpty){            
            mock = EasyMock.createMock(HippoBean.class);
            EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
            EasyMock.replay(mock);
        }
        return mock;
    }

    private List<HippoBean> createMockContacts(String firstContactBean, String secondContactBean,
            String thirdContactBean, boolean isPickerEmpty) {

        List<HippoBean> contactsList = new ArrayList<HippoBean>();

        if (firstContactBean != null) {
            contactsList.add(createMockContentBean(firstContactBean, isPickerEmpty));
        }
        if (secondContactBean != null) {
            contactsList.add(createMockContentBean(secondContactBean, isPickerEmpty));
        }
        if (thirdContactBean != null) {
            contactsList.add(createMockContentBean(thirdContactBean, isPickerEmpty));
        }
        return contactsList;
    }
}
