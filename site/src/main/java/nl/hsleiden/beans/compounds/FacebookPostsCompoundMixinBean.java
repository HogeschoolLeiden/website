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
        //FIXME hard coded value needs to be replaced
        return "HSLeidenNL";
    }

    @Override
    public int getLimit() {
        //FIXME hard coded value needs to be replaced
        return 3;
    }

    
}
