package nl.hsleiden.components;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.configuration.site.HstSite;
import org.hippoecm.hst.configuration.sitemenu.HstSiteMenuConfiguration;
import org.hippoecm.hst.configuration.sitemenu.HstSiteMenusConfiguration;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuImpl;

import com.tdclighthouse.prototype.components.WebDocumentDetail;
import com.tdclighthouse.prototype.componentsinfo.SimpleNavigationInfo;
import com.tdclighthouse.prototype.utils.Constants;
import com.tdclighthouse.prototype.utils.Constants.AttributesConstants;

@ParametersInfo(type = SimpleNavigationInfo.class)
public class CrossDomainNavigation extends WebDocumentDetail {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {

        SimpleNavigationInfo parametersInfo = getComponentParametersInfo(request);
        HstRequestContext requestContext = request.getRequestContext();
        Mount mount = requestContext.getMount("hsl");
        HstSite hstSite = mount.getHstSite();
        
        HstSiteMenuConfiguration siteMenuConfiguration = getSimeMenuConfiguration(parametersInfo, hstSite);

        HstSiteMenu menu = new HstSiteMenuImpl(null, siteMenuConfiguration, requestContext);
        request.setAttribute(Constants.AttributesConstants.MENU, menu);
        request.setAttribute(AttributesConstants.PARAM_INFO, getComponentParametersInfo(request));
    }

    protected HstSiteMenuConfiguration getSimeMenuConfiguration(SimpleNavigationInfo parametersInfo, HstSite hstSite) {

        HstSiteMenusConfiguration menusConfig = hstSite.getSiteMenusConfiguration();
        return menusConfig.getSiteMenuConfiguration(parametersInfo.getMenuName());
    }
}
