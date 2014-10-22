package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.Parameter;

@FieldGroupList({
    @FieldGroup(
            titleKey = "fields.widget",
            value = { Constants.WidgetConstants.FIELD_USER_MIXIN, "query", "from", "limit", Constants.WidgetConstants.SHOW_IMAGES }
    )
})
public interface TwitterFeedInfo {

    @Parameter(name = Constants.WidgetConstants.FIELD_USER_MIXIN, defaultValue = "false")
    public Boolean getUseMixin();
    
    @Parameter(name = "query", displayName = "Zoekopdracht", defaultValue = "")
    public String getQuery();

    @Parameter(name = "from", displayName = "Gebruiker", defaultValue = "")
    public String getFrom();

    @Parameter(name = "limit", displayName = "Max aantal tweets", defaultValue = "5")
    public int getLimit();
    
    @Parameter(name = Constants.WidgetConstants.SHOW_IMAGES, defaultValue = "false")
    public Boolean getShowImages();

}
