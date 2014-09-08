package nl.hsleiden.components;

import org.hippoecm.hst.configuration.site.HstSite;
import org.hippoecm.hst.configuration.sitemenu.HstSiteMenuConfiguration;
import org.hippoecm.hst.configuration.sitemenu.HstSiteMenusConfiguration;

import com.tdclighthouse.prototype.componentsinfo.SimpleNavigationInfo;

public class FooterExtraMenuNavigation extends CrossDomainNavigation {

    @Override
    protected HstSiteMenuConfiguration getSimeMenuConfiguration(SimpleNavigationInfo parametersInfo, HstSite hstSite) {

        HstSiteMenusConfiguration menusConfig = hstSite.getSiteMenusConfiguration();
        HstSiteMenuConfiguration menuConfig = menusConfig.getSiteMenuConfiguration("footerExtra");
        return menuConfig;
    }
}
