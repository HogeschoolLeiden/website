package nl.hsleiden.componentsinfo;

import hslbeans.TextTeaser;
import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

public interface TextTeasersInfo extends TeasersInfo {

    @Override
    @Parameter(name = Constants.WidgetConstants.TEASER_1, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.TEASERS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { TextTeaser.JCR_TYPE })
    public String getFirstTeaser();

    @Override
    @Parameter(name = Constants.WidgetConstants.TEASER_2, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.TEASERS_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { TextTeaser.JCR_TYPE })
    public String getSecondTeaser();

}
