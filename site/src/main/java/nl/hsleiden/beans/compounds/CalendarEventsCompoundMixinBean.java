package nl.hsleiden.beans.compounds;


import nl.hsleiden.componentsinfo.CalendarEventsInfo;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@Node(jcrType = "hsl:CalendarEventsCompoundMixin")
public class CalendarEventsCompoundMixinBean extends hslbeans.CalendarEventsCompoundMixin implements CalendarEventsInfo  {

    public Boolean getUseMixin() {
        throw new UnsupportedOperationException();
    }

    public String getScope() {
        String result = null;
        HippoBean scopeBean = getContentBeanPath();
        if(scopeBean!=null){
            result = scopeBean.getPath();
        }
        return result;
    }

}
