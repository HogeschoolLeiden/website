package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.RelatedItemsCompoundMixinBean;

@Mixin("hsl:RelatedEventsMixin")
public interface RelatedEventsMixin {

    @JcrPath("hsl:relatedEventsCompoundMixin")
    public RelatedItemsCompoundMixinBean getRelatedEventsCompoundMixin();

}
