package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.Parameter;

public interface LinkedinInfo {
	
	@Parameter(name = Constants.WidgetConstants.COMPANY_PROFILE_ID, defaultValue = "24763")
    public String getCompanyProfileId();

}
