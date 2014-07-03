package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.RelatedNewsCompoundMixinBean;

@Mixin("hsl:RelatedItemsMixin")
public interface RelatedItemsMixin {

    @JcrPath("hsl:relatedCompoundMixin")
    public RelatedNewsCompoundMixinBean getRelatedCompoundMixin();

}
