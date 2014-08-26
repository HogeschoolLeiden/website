package nl.hsleiden.beans;

import hslbeans.ArticlePage;

import java.util.Calendar;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.onehippo.forge.feed.api.FeedType;
import org.onehippo.forge.feed.api.annot.SyndicationElement;
import org.onehippo.forge.feed.api.transform.CalendarToDateConverter;
import org.onehippo.forge.feed.api.transform.DocumentLinkResolver;
import org.onehippo.forge.feed.api.transform.rss.StringToDescriptionConverter;

@Node(jcrType = "hsl:ArticlePage")
public class ArticlePageBean extends ArticlePage {
    
    @Override
    @SyndicationElement(type = FeedType.RSS, name = "title")
    public String getTitle() {
       return super.getTitle();
    }
    
    @Override
    @SyndicationElement(type = FeedType.RSS, name = "description", converter = StringToDescriptionConverter.class)
    public String getIntroduction() {
        return super.getIntroduction();
    }
    
    @Override
    @SyndicationElement(type = FeedType.RSS, name = "pubDate", converter = CalendarToDateConverter.class)
    public Calendar getReleaseDate() {
        return super.getReleaseDate();
    }
    
    @SyndicationElement(type = FeedType.RSS, name = "link", transformer = DocumentLinkResolver.class)
    public HippoBean getLink() {
        return this;
    }

}
