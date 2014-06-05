package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants.HstParameters;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

public interface ContentBeanPathInfo {

    /**
     * you can both use, a relative path from the site content base or an
     * absolute path
     * 
     */
    @JcrPath
    @Parameter(name = HstParameters.CONTENT_BEAN_PATH, displayName = "Index file Path")
    public String getContentBeanPath();
}
