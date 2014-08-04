package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.Parameter;

public interface TwitterFeedInfo {

    @Parameter(name = Constants.WidgetConstants.FIELD_USER_MIXIN, defaultValue = "false")
    public Boolean getUseMixin();
    
    @Parameter(name = "title", displayName = "Titel van het component", defaultValue = "Twitter Feed")
    String getTitle();

    @Parameter(name = "query", displayName = "Zoekopdracht", defaultValue = "")
    String getQuery();

    @Parameter(name = "from", displayName = "Gebruiker", defaultValue = "")
    String getFrom();

    @Parameter(name = "limit", displayName = "Max aantal tweets", defaultValue = "5")
    int getLimit();

    @Parameter(name = "followText", displayName = "Volg ons tekst", defaultValue = "Volg ons op: ")
    String getFollowText();

    @Parameter(name = "horizontal", displayName = "Tonen horizontaal", defaultValue = "false")
    Boolean getHorizontal();

}
