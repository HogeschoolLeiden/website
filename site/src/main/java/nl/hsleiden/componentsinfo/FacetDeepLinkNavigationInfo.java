package nl.hsleiden.componentsinfo;

import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.FacetDeepLinkNavInfo;

public interface FacetDeepLinkNavigationInfo extends FacetDeepLinkNavInfo{

    @Parameter(name = "overviewSitemapRefId", displayName="overzicht sitemap ref id", defaultValue="")
    public String getOverviewSitemapRefId();
}
