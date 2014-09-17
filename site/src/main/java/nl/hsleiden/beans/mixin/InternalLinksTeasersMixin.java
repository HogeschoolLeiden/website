package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.InternalLinksTeasersCompoundMixinBean;

@Mixin("hsl:InternalLinksTeasersMixin")
public interface InternalLinksTeasersMixin {

    @JcrPath("hsl:internalLinksTeasersCompoundMixin")
    public InternalLinksTeasersCompoundMixinBean getInternalLinksTeasersCompoundMixinBean();

}
