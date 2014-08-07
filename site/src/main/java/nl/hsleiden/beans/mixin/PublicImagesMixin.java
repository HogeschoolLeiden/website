package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.PublicImagesCompoundMixinBean;

@Mixin("hsl:PublicImagesMixin")
public interface PublicImagesMixin {

    @JcrPath("hsl:publicImagesCompoundMixin")
    public PublicImagesCompoundMixinBean getPublicImagesCompoundMixinBean();

}
