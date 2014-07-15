package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;

import org.hippoecm.hst.content.beans.standard.HippoBean;

@Mixin("hsl:CalendarMixin")
public interface CalendarMixin {

    @JcrPath("hsl:title")
    public String getTitle();

    @JcrPath("hsl:scope")
    public HippoBean getScope();

}
