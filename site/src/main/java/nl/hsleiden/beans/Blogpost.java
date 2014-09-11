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

import java.util.Calendar;
import java.util.List;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSetBean;
import org.onehippo.cms7.essentials.components.model.Authors;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "hsl:blogpost")
@Node(jcrType = "hsl:blogpost")
public class Blogpost extends WebPage implements Authors {

    public static final String TITLE = "hsl:title";
    public static final String INTRODUCTION = "hsl:introduction";
    public static final String CATEGORIES = "hsl:categories";
    public static final String AUTHOR = "hsl:author";
    public static final String AUTHOR_NAMES = "hsl:authornames";
    public static final String LINK = "hsl:link";
    public static final String AUTHORS = "hsl:authors";
    public static final String TAGS = "hippostd:tags";
    
    private Boolean hideFromSearch;
    private Boolean hideFromSitemap;
    private Boolean share;
    private String keywords;
    private String description;
    private Calendar releaseDate;
    private String browserTitle;
    
    public Boolean getHideFromSearch() {
        if (this.hideFromSearch == null) {
            this.hideFromSearch = getProperty("hsl:hideFromSearch");
        }
        return this.hideFromSearch;
    }

    public Boolean getHideFromSitemap() {
        if (this.hideFromSitemap == null) {
            this.hideFromSitemap = getProperty("hsl:hideFromSitemap");
        }
        return this.hideFromSitemap;
    }

    public Boolean getShare() {
        if (this.share == null) {
            this.share = getProperty("hsl:share");
        }
        return this.share;
    }

    public String getKeywords() {
        if (this.keywords == null) {
            this.keywords = getProperty("hsl:keywords");
        }
        return this.keywords;
    }

    public String getDescription() {
        if (this.description == null) {
            this.description = getProperty("hsl:description");
        }
        return this.description;
    }

    public Calendar getReleaseDate() {
        if (this.releaseDate == null) {
            this.releaseDate = getProperty("hsl:releaseDate");
        }
        return this.releaseDate;
    }

    public String getBrowserTitle() {
        if (this.browserTitle == null) {
            this.browserTitle = getProperty("hsl:browserTitle");
        }
        return this.browserTitle;
    }
    
    private HippoGalleryImageSetBean headerImage;
    private List<HippoBean> flexibleblock;
    
    public List<HippoBean> getFlexibleblock() {
        if (this.flexibleblock == null) {
            this.flexibleblock = getChildBeansByName("hsl:flexibleblock");
        }
        return this.flexibleblock;
    }
    
    public HippoGalleryImageSetBean getHeaderImage() {
        if (this.headerImage == null) {
            this.headerImage = getLinkedBean("hsl:headerImage", HippoGalleryImageSetBean.class);
        }
        return this.headerImage;
    }
    
    @HippoEssentialsGenerated(internalName = "hsl:authornames")
    public String[] getAuthorNames() {
        return getProperty(AUTHOR_NAMES);
    }

    public String getBlogAuthor() {
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