package nl.hsleiden.beans.compounds;

import hslbeans.RelatedFilterParameters;
import hslbeans.RelatedOverviewParameters;
import hslbeans.RelatedSortParameters;

import java.util.HashMap;
import java.util.Map;

import nl.hsleiden.componentsinfo.RelatedItemsInfo;

import org.hippoecm.hst.content.beans.Node;

@Node(jcrType = "hsl:RelatedCompoundMixin")
public class RelatedCompoundMixinBean extends hslbeans.RelatedCompoundMixin {

    
	private RelatedWidgetParametersBean widgetParameters;
	
	public Map<String, Object> getConfigObject() {
		Map<String, Object> parametersInfoMap = new HashMap<String, Object>(2);
		
		RelatedWidgetParametersBean widgetParams = getWidgetParameters();
		parametersInfoMap.put(RelatedItemsInfo.WIDGET_TITLE, widgetParams.getWidgetTitle());
		parametersInfoMap.put(RelatedItemsInfo.SIZE, widgetParams.getSize());
		parametersInfoMap.put(RelatedItemsInfo.CONTENT_BEAN_PATH, widgetParams.getContentBeanPath().getLocalizedName());
		
		RelatedSortParameters sortParams = getSortParameters();
		parametersInfoMap.put(RelatedItemsInfo.SORT_BY, sortParams.getSortBy());
		parametersInfoMap.put(RelatedItemsInfo.SORT_ORDER, sortParams.getSortOrder());

		RelatedOverviewParameters overviewParams = getOverviewParameters();
		parametersInfoMap.put(RelatedItemsInfo.OVERVIEW_BEAN_PATH, overviewParams.getOverviewBeanPath());
		parametersInfoMap.put(RelatedItemsInfo.SHOW_OVERVIEW, overviewParams.getShowOverview());
		parametersInfoMap.put(RelatedItemsInfo.OVERVIEW_LINK_LABEL, overviewParams.getOverviewLinkLabel());
		
		RelatedFilterParameters filterParams = getFilterParameters();
		parametersInfoMap.put(RelatedItemsInfo.THEMA_FILTER, filterParams.getThemaFilter());
		parametersInfoMap.put(RelatedItemsInfo.OVER_FILTER, filterParams.getOverFilter());
		parametersInfoMap.put(RelatedItemsInfo.TYPE_FILTER, filterParams.getTypeFilter());
		
		return parametersInfoMap;
	}
	
	@Override
	public RelatedWidgetParametersBean getWidgetParameters() {
		if (this.widgetParameters == null) {
			this.widgetParameters = getBean("hsl:widgetParameters", RelatedWidgetParametersBean.class);
		}
		return this.widgetParameters;
	}
}
