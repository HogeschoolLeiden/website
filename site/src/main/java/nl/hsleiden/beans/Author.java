package nl.hsleiden.beans;

/**
 * Copyright 2014 Hippo B.V. (http://www.onehippo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

import hslbeans.WebPage;

import java.util.List;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSetBean;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.components.model.AuthorEntry;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "hsl:author")
@Node(jcrType = "hsl:author")
public class Author extends WebPage implements AuthorEntry {

    public static final String ROLE = "hsl:role";
    public static final String ACCOUNTS = "hsl:accounts";
    public static final String FULL_NAME = "hsl:fullname";
    public static final String IMAGE = "hsl:image";
    public static final String CONTENT = "hsl:content";

    @HippoEssentialsGenerated(internalName = "hsl:fullname")
    public String getFullName() {
        return  getProperty(FULL_NAME);
    }

    @HippoEssentialsGenerated(internalName = "hsl:content")
    public HippoHtml getContent() {
        return getHippoHtml(CONTENT);
    }

    @HippoEssentialsGenerated(internalName = "hsl:role")
    public String getRole() {
        return getProperty(ROLE);
    }

    @HippoEssentialsGenerated(internalName = "hsl:image")
    public HippoGalleryImageSetBean getImage() {
        return getLinkedBean(IMAGE, HippoGalleryImageSetBean.class);
    }
    
    @HippoEssentialsGenerated(internalName = "hsl:account")
    public List<Account> getAccounts() {
        return getChildBeansByName(ACCOUNTS, Account.class);
    }

}
