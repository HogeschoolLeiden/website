package nl.hsleiden.beans.compounds;

import org.hippoecm.hst.content.beans.Node;

import nl.hsleiden.componentsinfo.TwitterFeedInfo;

@Node(jcrType = "hsl:TwitterCompoundMixin")
public class TwitterCompoundMixinBean extends hslbeans.TwitterCompoundMixin implements TwitterFeedInfo {

    @Override
    public Boolean getUseMixin() {
       throw new UnsupportedOperationException();
    }

    @Override
    public String getTitle() {
       return getTwitterParameters().getTitle();
    }

    @Override
    public int getLimit() {
        return getTwitterParameters().getLimit().intValue();
    }

    @Override
    public String getFrom() {
        return getTwitterParameters().getFrom();
    }

    @Override
    public Boolean getHorizontal() {
        return getTwitterExtraParameters().getHorizontal();
    }
    
    @Override
    public String getFollowText() {
        return getTwitterExtraParameters().getFollowText();
    }

    @Override
    public String getQuery() {
        return getTwitterExtraParameters().getQuery();
    }
}
