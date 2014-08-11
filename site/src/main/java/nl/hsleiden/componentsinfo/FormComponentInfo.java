package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

public interface FormComponentInfo {

    @Parameter(name = Constants.WidgetConstants.FIELD_USER_MIXIN, defaultValue = "off", displayName=WidgetConstants.FIELD_USER_MIXIN_MESSAGE)
    public Boolean getUseMixin();
    
    @Parameter(name = Constants.WidgetConstants.CONTENT_BEAN_PATH, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.FORMS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = {"ef:form"})
    public String getContentBeanPath();

    @Parameter(name = Constants.WidgetConstants.THANKS_BEAN_PATH, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = {"hsl:ArticlePage"})
    public String getThanksBeanPath();
}
