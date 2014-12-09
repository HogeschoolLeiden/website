package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.Parameter;

@FieldGroupList({
        @FieldGroup(titleKey = "mixin.group.title", value = { Constants.WidgetConstants.FIELD_USER_MIXIN }),
})
public interface LectoratNewsInfo {

    @Parameter(name = Constants.WidgetConstants.FIELD_USER_MIXIN, defaultValue = "on", 
                displayName=WidgetConstants.FIELD_USER_MIXIN_MESSAGE)
    public Boolean getUseMixin();
}
