package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.TextTeasersCompoundMixinBean;

@Mixin("hsl:TextTeasersMixin")
public interface TextTeasersMixin {

    @JcrPath("hsl:textTeasersCompoundMixin")
    public TextTeasersCompoundMixinBean getTextTeasersCompoundMixinBean();

}
