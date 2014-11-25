package nl.hsleiden.componentsinfo;

import hslbeans.WebPage;
import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.FacetedOverviewPageInfo;

public interface FacetCatalogInfo extends FacetedOverviewPageInfo {

    @Parameter(name = "firstFacetName", displayName="Eerste Facet Naam", defaultValue="")
    public String getFirstFacetName();

    @Parameter(name = "secondFacetName", displayName="Tweede Facet Naam", defaultValue="")
    public String getSecondFacetName();

    @Parameter(name = Constants.WidgetConstants.OVERVIEW_BEAN_PATH, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.INITIAL_LOCATION, pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { WebPage.JCR_TYPE })
    public String getOverviewBeanPath();
}
