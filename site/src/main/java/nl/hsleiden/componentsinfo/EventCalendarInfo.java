package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.MixinEnabled;

@FieldGroupList({
    @FieldGroup(titleKey = "mixin.group.title", value = { Constants.WidgetConstants.FIELD_USER_MIXIN }),
    @FieldGroup(titleKey = "widget.group.title", value = { "title", "scope", Constants.WidgetConstants.THEMA_FILTER,
            Constants.WidgetConstants.OVER_FILTER,}) })
public interface EventCalendarInfo extends MixinEnabled {

    @Parameter(name = "title", defaultValue = WidgetConstants.WIDGET_TITLE_DEFAULT)
    public String getWidgetTitle();

    @Parameter(name = "scope")
    @JcrPath(isRelative = false, pickerRemembersLastVisited=false, 
             pickerInitialPath = WidgetConstants.INITIAL_LOCATION, 
             pickerSelectableNodeTypes = { Constants.WidgetConstants.CONTENT_BEAN_PATH_SELECTABLE })
    public String getScope();

    @Parameter(name = WidgetConstants.THEMA_FILTER, defaultValue = "off")
    public Boolean getThemaFilter();

    @Parameter(name = WidgetConstants.OVER_FILTER, defaultValue = "off")
    public Boolean getOverFilter();

    @Parameter(name = WidgetConstants.FUTURE_FILTER, defaultValue = "off")
    public Boolean getFutureFilter();

}
