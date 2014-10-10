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
    public String getFistPost() {
        String result = null;
        if (getPosts().length > 0) {
           result = getPosts()[0];
        }
        return result;
    }

    @Override
    public String getSecondPost() {
        String result = null;
        if (getPosts().length > 1) {
            result = getPosts()[1];
         }
        return result;
    }

    @Override
    public String getThirdPost() {
        String result = null;
        if (getPosts().length > 2) {
            result = getPosts()[2];
        }
        return result;
    }
}
