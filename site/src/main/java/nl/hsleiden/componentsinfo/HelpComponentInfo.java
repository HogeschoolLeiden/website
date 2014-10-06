package nl.hsleiden.componentsinfo;

import hslbeans.HelpComponent;
import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.ContentBeanPathInfo;
import com.tdclighthouse.prototype.utils.Constants.HstParametersConstants;

public interface HelpComponentInfo extends ContentBeanPathInfo {

    @Override
    @Parameter(name = HstParametersConstants.CONTENT_BEAN_PATH, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.HELP_INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { HelpComponent.JCR_TYPE })
    public String getContentBeanPath();
   
}
