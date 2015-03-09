package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.FacetedOverviewPageInfo;

public interface FacetCatalogInfo extends FacetedOverviewPageInfo {

    @Parameter(name = "firstFacetName", displayName="Eerste Facet Naam", defaultValue="")
    public String getFirstFacetName();

    @Parameter(name = "secondFacetName", displayName="Tweede Facet Naam", defaultValue="")
    public String getSecondFacetName();

    @Parameter(name = Constants.WidgetConstants.LEFT_OVERVIEW_LINK, defaultValue = "vind-je-studie")
    public String getLeftOverviewLink();

    @Parameter(name = Constants.WidgetConstants.RIGHT_OVERVIEW_LINK, defaultValue = "masters/Soort/masterclass")
    public String getRightOverviewLink();
}
