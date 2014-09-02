package nl.hsleiden.componentsinfo;

import hslbeans.ContactPerson;
import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

public interface TeasersInfo {

    @Parameter(name = Constants.WidgetConstants.FIELD_USER_MIXIN, defaultValue = "off", displayName=WidgetConstants.FIELD_USER_MIXIN_MESSAGE)
    public Boolean getUseMixin();

    @Parameter(name = Constants.WidgetConstants.WIDGET_TITLE, defaultValue = Constants.WidgetConstants.WIDGET_TITLE_DEFAULT_CONTACTS)
    public String getWidgetTitle();

    @Parameter(name = Constants.WidgetConstants.TEASER_1, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.TEASERS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { ContactPerson.JCR_TYPE })
    public String getFirstTeaser();

    @Parameter(name = Constants.WidgetConstants.TEASER_2, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.TEASERS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { ContactPerson.JCR_TYPE })
    public String getSecondTeaser();
    
    @Parameter(name = Constants.WidgetConstants.TEASER_3, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.TEASERS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { ContactPerson.JCR_TYPE })
    public String getThirdTeaser();

}
