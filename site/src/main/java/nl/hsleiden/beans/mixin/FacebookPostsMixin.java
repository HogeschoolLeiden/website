package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.FacebookPostsCompoundMixinBean;

@Mixin("hsl:FacebookPostsMixin")
public interface FacebookPostsMixin {

    @JcrPath("hsl:facebookPostsCompoundMixin")
    public FacebookPostsCompoundMixinBean getFacebookPostsCompoundMixin();

}
