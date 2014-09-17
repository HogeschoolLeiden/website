package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.ExternalLinksTeasersCompoundMixinBean;

@Mixin("hsl:ExternalLinksTeasersMixin")
public interface ExternalLinksTeasersMixin {

    @JcrPath("hsl:externalLinksTeasersCompoundMixin")
    public ExternalLinksTeasersCompoundMixinBean getExternalLinksTeasersCompoundMixinBean();

}
