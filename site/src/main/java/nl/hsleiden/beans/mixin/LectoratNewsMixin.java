package nl.hsleiden.beans.mixin;

import hslbeans.LectoratNewsCompoundMixin;
import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;

@Mixin("hsl:LectoratNewsMixin")
public interface LectoratNewsMixin {

    @JcrPath("hsl:lectoratNewsCompoundMixin")
    public LectoratNewsCompoundMixin getLectoratNewsCompoundMixin();

}
