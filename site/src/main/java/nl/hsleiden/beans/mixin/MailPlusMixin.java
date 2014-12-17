package nl.hsleiden.beans.mixin;

import hslbeans.MailPlusCompoundMixin;
import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;

@Mixin("hsl:MailPlusMixin")
public interface MailPlusMixin {

    @JcrPath("hsl:mailPlusCompoundMixin")
    public MailPlusCompoundMixin getMailPlusCompoundMixin();

}
