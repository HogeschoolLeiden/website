package nl.hsleiden.componentsinfo;

import hslbeans.ContactPerson;
import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

public interface ContactPersonsInfo {

    @Parameter(name = Constants.WidgetConstants.FIELD_USER_MIXIN, defaultValue = "off")
    public Boolean getUseMixin();

    @Parameter(name = Constants.WidgetConstants.WIDGET_TITLE, defaultValue = Constants.WidgetConstants.WIDGET_TITLE_DEFAULT_CONTACTS)
    public String getWidgetTitle();

    @Parameter(name = Constants.WidgetConstants.CONTACT_1, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.CONTACTS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { ContactPerson.JCR_TYPE })
    public String getFirstContact();

    @Parameter(name = Constants.WidgetConstants.CONTACT_2, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.CONTACTS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { ContactPerson.JCR_TYPE })
    public String getSecondContact();
    
    @Parameter(name = Constants.WidgetConstants.CONTACT_3, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.CONTACTS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { ContactPerson.JCR_TYPE })
    public String getThirdContact();

}
