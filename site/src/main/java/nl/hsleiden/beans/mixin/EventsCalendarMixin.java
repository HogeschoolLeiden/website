package nl.hsleiden.beans.mixin;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;
import nl.hsleiden.beans.compounds.CalendarEventsCompoundMixinBean;

@Mixin("hsl:CalendarEventsMixin")
public interface EventsCalendarMixin {

    @JcrPath("hsl:calendarEventsCompoundMixin")
    public CalendarEventsCompoundMixinBean getCalendarEventsCompoundMixin();

}
