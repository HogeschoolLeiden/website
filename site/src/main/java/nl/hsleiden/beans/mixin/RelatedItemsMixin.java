package nl.hsleiden.beans.mixin;


import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.RelatedCompoundMixinBean;

@Mixin("hsl:RelatedItemsMixin")
public interface RelatedItemsMixin {
	
	@JcrPath("hsl:relatedCompoundMixin")
	public RelatedCompoundMixinBean getRelatedCompoundMixin();

}
