package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.ImageTeasersCompoundMixinBean;

@Mixin("hsl:ImageTeasersMixin")
public interface ImageTeasersMixin {

    @JcrPath("hsl:imageTeasersCompoundMixin")
    public ImageTeasersCompoundMixinBean getImageTeasersCompoundMixinBean();

}
