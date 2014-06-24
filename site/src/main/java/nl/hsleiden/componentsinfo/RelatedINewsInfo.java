package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.parameters.DropDownList;
import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.ContentBeanPathInfo;

@FieldGroupList({
        @FieldGroup(titleKey = "mixin.group.title", value = { Constants.WidgetConstants.FIELD_USER_MIXIN }),
        @FieldGroup(titleKey = "widget.group.title", value = { Constants.WidgetConstants.WIDGET_TITLE,
                Constants.WidgetConstants.SIZE, Constants.WidgetConstants.CONTENT_BEAN_PATH }),
        @FieldGroup(titleKey = "items.sort.title", value = { Constants.WidgetConstants.SORT_BY,
                Constants.WidgetConstants.SORT_ORDER }),
        @FieldGroup(titleKey = "overview.group.title", value = { Constants.WidgetConstants.SHOW_OVERVIEW,
                Constants.WidgetConstants.OVERVIEW_BEAN_PATH, Constants.WidgetConstants.OVERVIEW_LINK_LABEL }),
        @FieldGroup(titleKey = "filter.group.title", value = { Constants.WidgetConstants.THEMA_FILTER,
                Constants.WidgetConstants.OVER_FILTER }) })
public interface RelatedINewsInfo extends ContentBeanPathInfo {

    @Parameter(name = Constants.WidgetConstants.FIELD_USER_MIXIN, defaultValue = "off")
    public Boolean getUseMixin();

    @Parameter(name = Constants.WidgetConstants.WIDGET_TITLE, defaultValue = Constants.WidgetConstants.WIDGET_TITLE_DEFAULT)
    public String getWidgetTitle();

    @Parameter(name = Constants.WidgetConstants.SHOW_OVERVIEW, defaultValue = "on")
    public Boolean getShowOverview();

    @Parameter(name = Constants.WidgetConstants.OVERVIEW_BEAN_PATH, defaultValue = "")
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.INITIAL_LOCATION, pickerSelectableNodeTypes = { Constants.WidgetConstants.OVERVIEW_BEAN_PATH_DOC_TYPE })
    public String getOverviewBeanPath();

    @Parameter(name = Constants.WidgetConstants.OVERVIEW_LINK_LABEL, defaultValue = "")
    public String getOverviewLinkLabel();

    @Override
    @Parameter(name = Constants.WidgetConstants.CONTENT_BEAN_PATH, required = true)
    @JcrPath(isRelative = false, pickerInitialPath = Constants.WidgetConstants.INITIAL_LOCATION, pickerSelectableNodeTypes = { Constants.WidgetConstants.CONTENT_BEAN_PATH_SELECTABLE })
    public String getContentBeanPath();

    @Parameter(name = Constants.WidgetConstants.SORT_ORDER, defaultValue = Constants.Values.DESCENDING)
    @DropDownList(value = { Constants.Values.DESCENDING, Constants.Values.ASCENDING })
    public String getSortOrder();

    @Parameter(name = Constants.WidgetConstants.SORT_BY, defaultValue = Constants.DisplayedFieldNames.RELEASE_DATE)
    @DropDownList(value = { Constants.DisplayedFieldNames.RELEASE_DATE, Constants.DisplayedFieldNames.TITLE })
    public String getSortBy();

    @Parameter(name = Constants.WidgetConstants.SIZE, defaultValue = "1")
    public int getSize();

    @Parameter(name = Constants.WidgetConstants.THEMA_FILTER, defaultValue = "off")
    public Boolean getThemaFilter();

    @Parameter(name = Constants.WidgetConstants.OVER_FILTER, defaultValue = "off")
    public Boolean getOverFilter();

}
