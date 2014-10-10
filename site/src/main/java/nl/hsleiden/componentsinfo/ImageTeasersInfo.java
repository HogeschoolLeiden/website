package nl.hsleiden.componentsinfo;

import hslbeans.ImageTeaser;
import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

@FieldGroupList({ @FieldGroup(titleKey = "fields.widget", value = { Constants.WidgetConstants.FIELD_USER_MIXIN,
        Constants.WidgetConstants.TEASER_1, Constants.WidgetConstants.TEASER_2 }) })
public interface ImageTeasersInfo extends TeasersInfo {

    @Override
    @Parameter(name = Constants.WidgetConstants.TEASER_1, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.TEASERS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { ImageTeaser.JCR_TYPE })
    public String getFirstTeaser();

    @Override
    @Parameter(name = Constants.WidgetConstants.TEASER_2, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.TEASERS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { ImageTeaser.JCR_TYPE })
    public String getSecondTeaser();
    
}
