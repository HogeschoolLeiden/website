package nl.hsleiden.beans.compounds;


import nl.hsleiden.componentsinfo.EventCalendarInfo;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@Node(jcrType = "hsl:CalendarEventsCompoundMixin")
public class CalendarEventsCompoundMixinBean extends hslbeans.CalendarEventsCompoundMixin implements EventCalendarInfo  {

    @Override
    public Boolean getUseMixin() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getScope() {
        String result = null;
        HippoBean scopeBean = getContentBeanPath();
        if(scopeBean!=null){
            result = scopeBean.getPath();
        }
        return result;
    }
    
    @Override
    public Boolean getFutureFilter() {
       Boolean futureFilter = super.getFutureFilter();
       return futureFilter != null ? futureFilter : false;
    }

}
