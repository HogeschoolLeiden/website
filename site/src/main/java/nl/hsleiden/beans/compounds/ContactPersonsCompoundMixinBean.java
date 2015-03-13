package nl.hsleiden.beans.compounds;

import nl.hsleiden.componentsinfo.ContactPersonsInfo;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@Node(jcrType = "hsl:ContactPersonsCompoundMixin")
public class ContactPersonsCompoundMixinBean extends hslbeans.ContactPersonsCompoundMixin implements ContactPersonsInfo {

    @Override
    public Boolean getUseMixin() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getFirstContact() {
        String result = null;
        if (!getContacts().isEmpty()) {
            HippoBean contactBean = getContacts().get(0);
            if (contactBean != null) {
                result = contactBean.getPath();
            }
        }
        return result;
    }

    @Override
    public String getSecondContact() {
        String result = null;
        if (getContacts().size() > 1) {
            HippoBean contactBean = getContacts().get(1);
            if (contactBean != null) {
                result = contactBean.getPath();
            }
        }
        return result;
    }

    @Override
    public String getThirdContact() {
        String result = null;
        if (getContacts().size() > 2) {
            HippoBean contactBean = getContacts().get(2);
            if (contactBean != null) {
                result = contactBean.getPath();
            }
        }
        return result;
    }

}
