package nl.hsleiden.componentsinfo;

import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.NavigationInfo;

public interface NavigationComponentInfo extends NavigationInfo{

    @Parameter(name = "overviewSitemapRefId", displayName="overzicht sitemap ref id", defaultValue="")
    public String getOverviewSitemapRefId();

    @Parameter(name = "level", displayName="begin op niveau", defaultValue="1")
    public int getLevel();
    
}
