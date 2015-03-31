package nl.hsleiden.beans;

import org.hippoecm.hst.content.beans.Node;

import hslbeans.ContactPerson;

@Node(jcrType = "hsl:ContactPerson")
public class ContactPersonBean extends ContactPerson{

    private String mailName;
    private String domain;
    
    public String getMailName() {
        if (this.mailName == null) {
            String completeMail = getMail();
            if(!completeMail.isEmpty()){
                this.mailName = getMail().substring(0, completeMail.indexOf("@"));
            }
        }
        return this.mailName;
    }

    public String getDomain() {
        if (this.domain == null) {
            String completeMail = getMail();
            if(!completeMail.isEmpty()){
                this.domain = getMail().substring(completeMail.indexOf("@")+1);
            }
        }
        return this.domain;
    }
}
