package nl.hsleiden.componentsinfo;

import org.hippoecm.hst.core.parameters.Parameter;

public interface NavigationInfo extends SimpleNavigationInfo {

    @Parameter(name = "showFacetedNavigation", defaultValue = "false", displayName = "Show faceted navigation items in the site menu")
    public boolean isShowFacetedNavigation();

}
