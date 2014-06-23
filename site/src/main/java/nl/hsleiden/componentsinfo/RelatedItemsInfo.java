package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.DocumentLink;
import org.hippoecm.hst.core.parameters.DropDownList;
import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.ContentBeanPathInfo;

//TODO: change names and grouping according to the mixin types
@FieldGroupList({
	@FieldGroup(titleKey = "mixin.group.title", 
	        value = {  RelatedItemsInfo.FIELD_USER_MIXIN }),
	@FieldGroup(titleKey = "widget.group.title", 
	        value = {  RelatedItemsInfo.WIDGET_TITLE,
	                   RelatedItemsInfo.SIZE,
	                   RelatedItemsInfo.CONTENT_BEAN_PATH}),
	@FieldGroup(titleKey = "items.sort.title", 
	        value = {  RelatedItemsInfo.SORT_BY, 
	                   RelatedItemsInfo.SORT_ORDER}),
	@FieldGroup(titleKey = "overview.group.title", 
	        value = {  RelatedItemsInfo.SHOW_OVERVIEW, 
	                   RelatedItemsInfo.OVERVIEW_BEAN_PATH, 
	                   RelatedItemsInfo.OVERVIEW_LINK_LABEL }),
	@FieldGroup(titleKey = "filter.group.title", 
	        value = {  RelatedItemsInfo.THEMA_FILTER, 
	                   RelatedItemsInfo.OVER_FILTER, 
	                   RelatedItemsInfo.TYPE_FILTER })
})
public interface RelatedItemsInfo extends ContentBeanPathInfo {

	public static final String FIELD_USER_MIXIN = "useMixin";
	
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
	public static final String TYPE_FILTER = "typeFilter";
	
	@Parameter(name = FIELD_USER_MIXIN, defaultValue = "off")
	public boolean getUseMixin();
	
	@Parameter(name = WIDGET_TITLE, defaultValue = WIDGET_TITLE_DEFAULT)
	public String getWidgetTitle();
	
	@Parameter(name = SHOW_OVERVIEW, defaultValue = "on")
	public boolean getShowOverviewLink();
	
	@Parameter(name = OVERVIEW_BEAN_PATH, defaultValue = "")
	@DocumentLink(docType = OVERVIEW_BEAN_PATH_DOC_TYPE, docLocation = INITIAL_LOCATION)
	public String getOverviewBeanPath();
	 
	@Parameter(name = OVERVIEW_LINK_LABEL, defaultValue = "")
	public String getOverviewLinkLabel();
	
	@Override
	@Parameter(name = CONTENT_BEAN_PATH, required = true)
	@JcrPath(isRelative = true, pickerInitialPath = INITIAL_LOCATION, pickerSelectableNodeTypes = {CONTENT_BEAN_PATH_SELECTABLE})
	public String getContentBeanPath();
	
	@Parameter(name = SORT_ORDER, defaultValue = Constants.Values.DESCENDING)
	@DropDownList(value = { Constants.Values.DESCENDING, Constants.Values.ASCENDING })
	public String getSortOrder();

	@Parameter(name = SORT_BY, defaultValue = Constants.FieldName.RELEASE_DATE)
	@DropDownList(value = {Constants.DisplayedFieldNames.RELEASE_DATE, 
	        Constants.DisplayedFieldNames.TITLE})
	public String getSortBy();
	
	@Parameter(name = SIZE, defaultValue = "1")
	public int getSize();
	
	@Parameter(name = THEMA_FILTER, defaultValue = "off")
    public boolean getThemaFilter();
	
	@Parameter(name = OVER_FILTER, defaultValue = "off")
    public boolean getOverFilter();
	
	@Parameter(name = TYPE_FILTER, defaultValue = "off")
    public boolean getTypeFilter();
	
}
