package nl.hsleiden.beans.compounds;

import hslbeans.FacebookPostsCompoundMixin;
import nl.hsleiden.componentsinfo.FacebookPostsInfo;

import org.hippoecm.hst.content.beans.Node;

@Node(jcrType = "hsl:FacebookPostsCompoundMixin")
public class FacebookPostsCompoundMixinBean extends FacebookPostsCompoundMixin implements FacebookPostsInfo {

    @Override
    public Boolean getUseMixin() {
       throw new UnsupportedOperationException();
    }

    @Override
    public String getAccount() {
        return super.getFrom();
    }

    public int getPostsLimit() {
        return super.getLimit().intValue();
    }
}
