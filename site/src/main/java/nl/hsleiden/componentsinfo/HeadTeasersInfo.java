package nl.hsleiden.componentsinfo;

import hslbeans.HeadTeaser;
import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

public interface HeadTeasersInfo {

    @Parameter(name = Constants.WidgetConstants.FIELD_USER_MIXIN, defaultValue = "off", displayName=WidgetConstants.FIELD_USER_MIXIN_MESSAGE)
    public Boolean getUseMixin();

    @Parameter(name = Constants.WidgetConstants.TEASER_1, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.TEASERS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { HeadTeaser.JCR_TYPE })
    public String getFirstTeaser();

    @Parameter(name = Constants.WidgetConstants.TEASER_2, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.TEASERS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { HeadTeaser.JCR_TYPE })
    public String getSecondTeaser();
    
    @Parameter(name = Constants.WidgetConstants.TEASER_3, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.TEASERS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { HeadTeaser.JCR_TYPE })
    public String getThirdTeaser();

}