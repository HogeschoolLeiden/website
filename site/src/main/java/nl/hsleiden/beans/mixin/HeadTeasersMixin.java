package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.HeadTeasersCompoundMixinBean;

@Mixin("hsl:HeadTeasersMixin")
public interface HeadTeasersMixin {

    @JcrPath("hsl:headTeasersCompoundMixin")
    public HeadTeasersCompoundMixinBean getHeadTeasersCompoundMixinBean();

}
