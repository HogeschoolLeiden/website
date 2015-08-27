package nl.hinttech.hsleiden.pi.saml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

public class HslSamlUserDetailsService implements SAMLUserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(HslSamlUserDetailsService.class);

    public static final String USERNAME_FIELD_2_0 = "urn:mace:dir:attribute-def:cn";
    private static final String USERNAME_FIELD_1_1 = "urn:oid:2.5.4.3";

    @Override
    public Object loadUserBySAML(SAMLCredential credential) {
        String userId = getUserName(credential);
        return new User(userId, userId, getAuthorities(credential));
    }

    public String getUserName(SAMLCredential credential) {
        final List<String> userIdValueList = getAttributeValue(USERNAME_FIELD_2_0, USERNAME_FIELD_1_1, credential);
        String userId = null;
        if (!userIdValueList.isEmpty()) {
            userId = userIdValueList.get(0);
        } else {
           userId = "unknown";
        }
        return userId;
    }

    public List<String> getAttributeValue(String attributeName, String fallbackAttributeName,
            final SAMLCredential credential) {
        List<String> attrValList;
        Attribute attribute = credential.getAttribute(attributeName);
        LOG.info("Parsing SAML attribute name:  {}", attributeName);
        if (attribute != null) {
            attrValList = getAttributeValues(attribute);
        } else {
            attribute = credential.getAttribute(fallbackAttributeName);
            attrValList = attribute != null ? getAttributeValues(attribute) : new ArrayList<String>();
        }
        return attrValList;
    }

    private List<String> getAttributeValues(Attribute attribute) {
        List<String> attrValList = new ArrayList<String>();
        List<XMLObject> attributes = attribute.getAttributeValues();
        if (attributes != null && !attributes.isEmpty()) {
            for (XMLObject object : attributes) {
                XSString attrb = (XSString) object;
                String attrValue = attrb.getValue();
                if (attrValue != null) {
                    LOG.info("attrValue {}", attrValue);
                    attrValList.add(attrValue);
                }
            }
        }
        return attrValList;
    }

    private Collection<GrantedAuthority> getAuthorities(SAMLCredential credential) {
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("anonymous"));
        Attribute attribute = credential.getAttribute("urn:mace:dir:attribute-def:eduPersonAffiliation");
        if (attribute == null) {
            attribute = credential.getAttribute("urn:oid:1.3.6.1.4.1.5923.1.1.1.1");
        }
        for (XMLObject xmlObject : attribute.getAttributeValues()) {
            if (xmlObject instanceof XSString) {
                roles.add(new SimpleGrantedAuthority(((XSString) xmlObject).getValue()));
            }
        }

        return roles;
    }
}
