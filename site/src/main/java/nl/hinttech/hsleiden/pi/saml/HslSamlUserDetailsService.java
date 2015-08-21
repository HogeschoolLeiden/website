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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

public class HslSamlUserDetailsService implements SAMLUserDetailsService {
    
    private static final Logger LOG = LoggerFactory.getLogger(HslSamlUserDetailsService.class);
    
    
    public static final String USERNAME_FIELD = "urn:mace:dir:attribute-def:cn";

    @Override
    public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
        String userId = getUserId(credential);
        return new User(userId, userId, getAuthorities(credential));
    }
    
    public static String getUserId(SAMLCredential credential) {
        final List<String> userIdValueList = getAttributeValue("urn:mace:dir:attribute-def:cn", credential);
        String userId = null;
        if (userIdValueList.size() > 0) {
            userId = userIdValueList.get(0);
        }
        return userId;
    }
    
    public static List<String> getAttributeValue(final String name, final SAMLCredential credential) {
        List<String> attrValList = new ArrayList<String>();
        Attribute attribute = credential.getAttribute(name);
        LOG.info("Parsing SAML attribute name:  {}", name);
        if (attribute != null) {
            List<XMLObject> attributes = attribute.getAttributeValues();
            if ((attributes != null) && (attributes.size() > 0)) {
                for (XMLObject object : attributes) {
                    XSString attrb = (XSString) object;
                    String attrValue = attrb.getValue();
                    if (attrValue != null) {
                        LOG.info("attrValue {}", attrValue);
                        attrValList.add(attrValue);
                    }
                }
            }
        }
        return attrValList;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(SAMLCredential credential) {
        ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
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
