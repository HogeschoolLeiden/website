package nl.hsleiden.components.catalog;

import java.util.Map;

import nl.hsleiden.componentsinfo.FacetCatalogInfo;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.components.MonolithicFacetedOverview;
import com.tdclighthouse.prototype.utils.Constants;

@ParametersInfo(type = FacetCatalogInfo.class)
public class FacetCatalog extends MonolithicFacetedOverview {

    @Override
    protected String enhanceQuery(String query) {
        String result;
        if ((query != null) && !query.endsWith("*")) {
            result = query + "*";
        } else {
            result = query;
        }
        return result;
    } 
    
    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        Map<String, Object> model = super.getModel(request, response);
        model.put(Constants.AttributesConstants.PARAM_INFO, getComponentParametersInfo(request));
        return model;
    }
    
}