package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.RelatedItemsCompoundMixinBean;

@Mixin("hsl:RelatedPersMixin")
public interface RelatedPersMixin {

    @JcrPath("hsl:relatedPersCompoundMixin")
    public RelatedItemsCompoundMixinBean getRelatedPersCompoundMixin();

}
