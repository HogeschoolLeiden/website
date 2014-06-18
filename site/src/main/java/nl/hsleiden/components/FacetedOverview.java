package nl.hsleiden.components;

import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.components.MonolithicFacetedOverview;
import com.tdclighthouse.prototype.componentsinfo.FacetedOverviewPageInfo;

@ParametersInfo(type = FacetedOverviewPageInfo.class)
public class FacetedOverview extends MonolithicFacetedOverview {
	
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
}
