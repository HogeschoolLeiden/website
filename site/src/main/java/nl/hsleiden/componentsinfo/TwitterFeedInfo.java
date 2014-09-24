package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.core.parameters.Parameter;

public interface TwitterFeedInfo {

    @Parameter(name = Constants.WidgetConstants.FIELD_USER_MIXIN, defaultValue = "false", displayName=WidgetConstants.FIELD_USER_MIXIN_MESSAGE)
    public Boolean getUseMixin();
    
    @Parameter(name = "query", displayName = "Zoekopdracht", defaultValue = "")
    public String getQuery();

    @Parameter(name = "from", displayName = "Gebruiker", defaultValue = "")
    public String getFrom();

    @Parameter(name = "limit", displayName = "Max aantal tweets", defaultValue = "5")
    public int getLimit();

    @Parameter(name = "horizontal", displayName = "Tonen horizontaal", defaultValue = "false")
    public Boolean getHorizontal();

}
