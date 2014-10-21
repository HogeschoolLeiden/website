package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.core.parameters.Parameter;

public interface FacebookPostsInfo {

    @Parameter(name = Constants.WidgetConstants.FIELD_USER_MIXIN, defaultValue = "false", displayName = WidgetConstants.FIELD_USER_MIXIN_MESSAGE)
    public Boolean getUseMixin();

    @Parameter(name = "account", displayName = "Account", defaultValue = "HSLeidenNL", description = "Hogeschool's facebook account.")
    public String getAccount();

    @Parameter(name = "limit", displayName = "Number of items", defaultValue = "3", description = "Number of items to be displays.")
    public int getLimit();

}
