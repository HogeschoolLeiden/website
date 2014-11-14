package nl.hsleiden.componentsinfo;

import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.NavigationInfo;

public interface NavigationComponentInfo extends NavigationInfo{

    @Parameter(name = "overviewSitemapRefId", displayName="overzicht sitemap ref id", defaultValue="")
    public String getOverviewSitemapRefId();

    @Parameter(name = "level", displayName="begin op niveau", defaultValue="1")
    public int getLevel();

    @Parameter(name = "depth", displayName="depth", defaultValue="1")
    public int getDepth();

    @Parameter(name = "leftMenuBlock", displayName="do not display left navigation", defaultValue="false")
    public Boolean getLeftMenuBlock();
    
}
