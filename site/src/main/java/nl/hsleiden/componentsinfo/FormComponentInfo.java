package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

public interface FormComponentInfo {

    @Parameter(name = Constants.WidgetConstants.FIELD_USER_MIXIN, defaultValue = "off")
    public Boolean getUseMixin();
    
    @Parameter(name = Constants.WidgetConstants.CONTENT_BEAN_PATH)
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.FORMS_INITIAL_LOCATION, pickerSelectableNodeTypes = {"ef:form"})
    public String getContentBeanPath();
}
