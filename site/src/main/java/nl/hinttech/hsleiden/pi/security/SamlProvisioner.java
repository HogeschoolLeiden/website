/*
 * Copyright 2013 Hippo B.V. (http://www.onehippo.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package nl.hinttech.hsleiden.pi.security;

import java.util.ArrayList;
import java.util.List;

import nl.surfnet.spring.security.opensaml.Provisioner;

import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.impl.XSAnyImpl;
import org.opensaml.xml.schema.impl.XSStringImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @version "$Id: SamlProvisioner.java 1422 2013-12-05 14:55:59Z rharing $"
 */
public class SamlProvisioner implements Provisioner {

    private static Logger log = LoggerFactory.getLogger(SamlProvisioner.class);

    @Override
    public UserDetails provisionUser(final Assertion assertion) {

        final String ATT_DISPLAY_NAME = "urn:mace:dir:attribute-def:displayName";
        final String ATT_MAIL = "urn:mace:dir:attribute-def:mail";
        final String ATT_SN = "urn:mace:dir:attribute-def:sn";
        final String ATT_CN = "urn:mace:dir:attribute-def:cn";
        final String ATT_GIVEN_NAME = "urn:mace:dir:attribute-def:givenName";
        final String ATT_PRINCIPAL = "urn:mace:dir:attribute-def:eduPersonPrincipalName";
        final String ATT_ORG = "urn:mace:terena.org:attribute-def:schacHomeOrganization";
        final String ATT_UID = "urn:mace:dir:attribute-def:uid";
        final String ATT_AFFILIATION = "urn:mace:dir:attribute-def:eduPersonAffiliation";
        final String ATT_MEMBER = "urn:mace:dir:attribute-def:isMemberOf";
        // TODO implement for SURFNET
        final boolean accountNonLocked = true;
        final boolean credentialsNonExpired = true;
        final boolean accountNonExpired = true;
        final boolean enabled = true;
        final String password = "";
        String userName = "";

        final List<AttributeStatement> attributeStatements = assertion.getAttributeStatements();
        for (AttributeStatement statement : attributeStatements) {
            final List<Attribute> attributes = statement.getAttributes();
            for (Attribute attribute : attributes) {
                final String name = attribute.getName();
                final XMLObject xmlObject = attribute.getAttributeValues().get(0);
                if (xmlObject != null) {
                    if (xmlObject instanceof XSAnyImpl) {
                        final String value = ((XSAnyImpl) xmlObject).getTextContent();
                        if (name.equals(ATT_MAIL)) {
                            userName = value;
                        }
                    } else if (xmlObject instanceof XSStringImpl) {
                        // For feide.no (in order to keep local testing working)
                        final String value = ((XSStringImpl) attribute.getAttributeValues().get(0)).getValue();
                        log.debug("attribute: {}, value: {}", name, value);
                        if(name.equals("uid")){
                            userName = value;
                        }
                    }

                    else {
                        log.warn("unknown type {}", xmlObject.getClass());
                    }
                }

            }

        }
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
        authorities.add(new GrantedAuthorityImpl("USER"));
        authorities.add(new GrantedAuthorityImpl("everybody"));

        return new User(userName, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                authorities);
    }
}
