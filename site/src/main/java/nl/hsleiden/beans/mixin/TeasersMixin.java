package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.TeasersCompoundMixinBean;

@Mixin("hsl:TeasersMixin")
public interface TeasersMixin {

    @JcrPath("hsl:teasersCompoundMixin")
    public TeasersCompoundMixinBean getTeasersCompoundMixinBean();

}
