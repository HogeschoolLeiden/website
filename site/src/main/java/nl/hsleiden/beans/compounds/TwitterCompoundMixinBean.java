package nl.hsleiden.beans.compounds;

import hslbeans.TwitterCompoundMixin;

import org.hippoecm.hst.content.beans.Node;

import nl.hsleiden.componentsinfo.TwitterFeedInfo;

@Node(jcrType = "hsl:TwitterCompoundMixin")
public class TwitterCompoundMixinBean extends TwitterCompoundMixin implements TwitterFeedInfo {

    @Override
    public Boolean getUseMixin() {
       throw new UnsupportedOperationException();
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
    public String getQuery() {
        return getTwitterParameters().getQuery();
    }

    @Override
    public Boolean getShowImages() {
        return getTwitterParameters().getShowImages();
    }
}
