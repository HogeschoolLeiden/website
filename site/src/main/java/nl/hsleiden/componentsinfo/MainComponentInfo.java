package nl.hsleiden.componentsinfo;

import org.hippoecm.hst.core.parameters.Parameter;

public interface MainComponentInfo {

    @Parameter(name = "overviewSitemapRefId", displayName="overzicht sitemap ref id", defaultValue="")
    public String getOverviewSitemapRefId();
    
}
