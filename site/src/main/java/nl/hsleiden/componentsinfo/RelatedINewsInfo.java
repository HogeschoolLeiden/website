package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.DropDownList;
import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.ContentBeanPathInfo;

@FieldGroupList({
	@FieldGroup(titleKey = "mixin.group.title", 
	        value = {  RelatedINewsInfo.FIELD_USER_MIXIN }),
	@FieldGroup(titleKey = "widget.group.title", 
	        value = {  RelatedINewsInfo.WIDGET_TITLE,
	                   RelatedINewsInfo.SIZE,
	                   RelatedINewsInfo.CONTENT_BEAN_PATH}),
	@FieldGroup(titleKey = "items.sort.title", 
	        value = {  RelatedINewsInfo.SORT_BY, 
	                   RelatedINewsInfo.SORT_ORDER}),
	@FieldGroup(titleKey = "overview.group.title", 
	        value = {  RelatedINewsInfo.SHOW_OVERVIEW, 
	                   RelatedINewsInfo.OVERVIEW_BEAN_PATH, 
	                   RelatedINewsInfo.OVERVIEW_LINK_LABEL }),
	@FieldGroup(titleKey = "filter.group.title", 
	        value = {  RelatedINewsInfo.THEMA_FILTER, 
	                   RelatedINewsInfo.OVER_FILTER })
})
public interface RelatedINewsInfo extends ContentBeanPathInfo {

	public static final String FIELD_USER_MIXIN = "try to use mixin";
	
	public static final String WIDGET_TITLE = "widgetTitle";
	public static final String WIDGET_TITLE_DEFAULT = "Related Items";
	public static final String SIZE = "size";
	public static final String CONTENT_BEAN_PATH = "contentBeanPath";
	public static final String CONTENT_BEAN_PATH_SELECTABLE = "hippostd:folder";
	
	public static final String SHOW_OVERVIEW = "showOverviewLink";
	public static final String OVERVIEW_BEAN_PATH = "overviewBeanPath";
	public static final String OVERVIEW_BEAN_PATH_DOC_TYPE = "hsl:WebPage";
	public static final String INITIAL_LOCATION = "hsl";
	public static final String OVERVIEW_LINK_LABEL = "overviewLinkLabel";
	
	public static final String SORT_ORDER = "sortOrder";
	public static final String SORT_BY = "sortBy";

	public static final String THEMA_FILTER = "themaFilter";
	public static final String OVER_FILTER = "overFilter";
	
	@Parameter(name = FIELD_USER_MIXIN, defaultValue = "off")
	public Boolean getUseMixin();
	
	@Parameter(name = WIDGET_TITLE, defaultValue = WIDGET_TITLE_DEFAULT)
	public String getWidgetTitle();
	
	@Parameter(name = SHOW_OVERVIEW, defaultValue = "on")
	public Boolean getShowOverview();
	
	@Parameter(name = OVERVIEW_BEAN_PATH, defaultValue = "")
	@JcrPath(isRelative = false, pickerInitialPath = INITIAL_LOCATION, pickerSelectableNodeTypes = {OVERVIEW_BEAN_PATH_DOC_TYPE})
	public String getOverviewBeanPath();
	 
	@Parameter(name = OVERVIEW_LINK_LABEL, defaultValue = "")
	public String getOverviewLinkLabel();
	
	@Override
	@Parameter(name = CONTENT_BEAN_PATH, required = true)
	@JcrPath(isRelative = false, pickerInitialPath = INITIAL_LOCATION, pickerSelectableNodeTypes = {CONTENT_BEAN_PATH_SELECTABLE})
	public String getContentBeanPath();
	
	@Parameter(name = SORT_ORDER, defaultValue = Constants.Values.DESCENDING)
	@DropDownList(value = { Constants.Values.DESCENDING, Constants.Values.ASCENDING })
	public String getSortOrder();

	@Parameter(name = SORT_BY, defaultValue = Constants.DisplayedFieldNames.RELEASE_DATE)
	@DropDownList(value = {Constants.DisplayedFieldNames.RELEASE_DATE, 
	        Constants.DisplayedFieldNames.TITLE})
	public String getSortBy();
	
	@Parameter(name = SIZE, defaultValue = "1")
	public int getSize();
	
	@Parameter(name = THEMA_FILTER, defaultValue = "off")
    public Boolean getThemaFilter();
	
	@Parameter(name = OVER_FILTER, defaultValue = "off")
    public Boolean getOverFilter();
	
}
