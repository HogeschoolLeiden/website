package nl.hsleiden.componentsinfo;

import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.FacetedOverviewPageInfo;

public interface FacetCatalogInfo extends FacetedOverviewPageInfo {

    @Parameter(name = "firstFacetName", displayName="Eerste Facet Naam", defaultValue="")
    public String getFirstFacetName();

    @Parameter(name = "secondFacetName", displayName="Tweede Facet Naam", defaultValue="")
    public String getSecondFacetName();
}
