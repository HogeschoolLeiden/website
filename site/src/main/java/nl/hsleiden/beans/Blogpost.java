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
import java.util.Calendar;
import java.util.List;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.onehippo.cms7.essentials.components.model.Authors;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "hsl:blogpost")
@Node(jcrType = "hsl:blogpost")
public class Blogpost extends HippoDocument implements Authors {

    public static final String TITLE = "hsl:title";
    public static final String INTRODUCTION = "hsl:introduction";
    public static final String PUBLICATION_DATE = "hsl:publicationdate";
    public static final String CATEGORIES = "hsl:categories";
    public static final String AUTHOR = "hsl:author";
    public static final String AUTHOR_NAMES = "hsl:authornames";
    public static final String LINK = "hsl:link";
    public static final String AUTHORS = "hsl:authors";
    public static final String TAGS = "hippostd:tags";

    private List<HippoBean> flexibleblock;
    
    public List<HippoBean> getFlexibleblock() {
        if (this.flexibleblock == null) {
            this.flexibleblock = getChildBeansByName("hsl:flexibleblock");
        }
        return this.flexibleblock;
    }
    
   @HippoEssentialsGenerated(internalName = "hsl:publicationdate")
    public Calendar getPublicationDate() {
        return getProperty(PUBLICATION_DATE);
    }

    @HippoEssentialsGenerated(internalName = "hsl:authornames")
    public String[] getAuthorNames() {
        return getProperty(AUTHOR_NAMES);
    }

    public String getAuthor() {
        final String[] authorNames = getAuthorNames();
        if(authorNames !=null && authorNames.length > 0){
            return authorNames[0];
        }
        return null;
    }

    @HippoEssentialsGenerated(internalName = "hsl:title")
    public String getTitle() {
        return getProperty(TITLE);
    }

    @HippoEssentialsGenerated(internalName = "hsl:introduction")
    public String getIntroduction() {
        return getProperty(INTRODUCTION);
    }

    @HippoEssentialsGenerated(internalName = "hsl:link")
    public String getLink() {
        return getProperty(LINK);
    }

    @HippoEssentialsGenerated(internalName = "hsl:categories")
    public List<String> getCategories() {
        return getProperty(CATEGORIES);
    }

    @Override
    @HippoEssentialsGenerated(internalName = "hsl:authors")
    public List<Author> getAuthors() {
        return getLinkedBeans(AUTHORS, Author.class);
    }

    @HippoEssentialsGenerated(internalName = "hippostd:tags")
    public String[] getTags() {
        return getProperty(TAGS);
    }

}