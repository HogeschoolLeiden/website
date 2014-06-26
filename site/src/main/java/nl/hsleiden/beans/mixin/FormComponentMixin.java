package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.FormCompoundMixinBean;

@Mixin("hsl:FormComponentMixin")
public interface FormComponentMixin {

    @JcrPath("hsl:formCompoundMixin")
    public FormCompoundMixinBean getFormCompoundMixin();

}
