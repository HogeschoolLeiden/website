package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.Parameter;


public interface RelatedNewsInfo extends RelatedItemsInfo {

    @Override
    @Parameter(name = Constants.WidgetConstants.WIDGET_TITLE, 
            defaultValue = Constants.WidgetConstants.WIDGET_TITLE_DEFAULT_NEWS)
    public String getWidgetTitle();

}
