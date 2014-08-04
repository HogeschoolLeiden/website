package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.TwitterCompoundMixinBean;

@Mixin("hsl:TwitterMixin")
public interface TwitterMixin {

    @JcrPath("hsl:twitterCompoundMixin")
    public TwitterCompoundMixinBean getTwitterCompoundMixin();

}
