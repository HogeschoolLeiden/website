package nl.hsleiden.beans;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.content.beans.Node;

import hslbeans.ContactPerson;

@Node(jcrType = "hsl:ContactPerson")
public class ContactPersonBean extends ContactPerson {

    private static final String AT_SIGN = "@";
    private String mailName;
    private String domain;

    public String getMailName() {
        if (this.mailName == null) {
            String completeMail = getMail();
            int indexOfAt = completeMail.indexOf(AT_SIGN);
            if (StringUtils.isNotBlank(completeMail) && indexOfAt > 0) {
                this.mailName = getMail().substring(0, indexOfAt);
            }
        }
        return this.mailName;
    }

    public String getDomain() {
        if (this.domain == null) {
            String completeMail = getMail();
            int indexOfAt = completeMail.indexOf(AT_SIGN);
            if (StringUtils.isNotBlank(completeMail) && indexOfAt > 0) {
                this.domain = getMail().substring(indexOfAt + 1);
            }
        }
        return this.domain;
    }
}
