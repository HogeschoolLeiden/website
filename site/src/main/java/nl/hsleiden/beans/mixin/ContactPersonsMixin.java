package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.ContactPersonsCompoundMixinBean;

@Mixin("hsl:ContactPersonsMixin")
public interface ContactPersonsMixin {

    @JcrPath("hsl:contactPersonsCompoundMixin")
    public ContactPersonsCompoundMixinBean getContactPersonsCompoundMixinBean();

}
