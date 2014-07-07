package nl.hsleiden.utils;

import com.tdclighthouse.prototype.utils.Constants.AttributesConstants;
import com.tdclighthouse.prototype.utils.Constants.FieldNameConstants;
import com.tdclighthouse.prototype.utils.Constants.HstParametersConstants;
import com.tdclighthouse.prototype.utils.Constants.NodeNameConstants;
import com.tdclighthouse.prototype.utils.Constants.ParametersConstants;
import com.tdclighthouse.prototype.utils.Constants.PikcerTypesConstants;
import com.tdclighthouse.prototype.utils.Constants.ValuesConstants;

public class Constants {

    private Constants() {
    }

    public static class Attributes extends AttributesConstants {
        public static final String LOGO = "logo";
        public static final String HEADER_NAME = "headerName";

        private Attributes() {
        }

    }

    public static class WidgetConstants {
        public static final String FIELD_USER_MIXIN = "tryToUseMixin";

        public static final String WIDGET_TITLE = "widgetTitle";
        public static final String WIDGET_TITLE_DEFAULT = "Related Items";
        public static final String WIDGET_TITLE_DEFAULT_NEWS = "Related News";
        public static final String WIDGET_TITLE_DEFAULT_EVENTS = "Related Events";
        public static final String SIZE = "size";
        public static final String CONTENT_BEAN_PATH = "contentBeanPath";
        public static final String CONTENT_BEAN_PATH_SELECTABLE = "hippostd:folder";

        public static final String SHOW_OVERVIEW = "showOverviewLink";
        public static final String OVERVIEW_BEAN_PATH = "overviewBeanPath";
        public static final String THANKS_BEAN_PATH = "thanksBeanPath";
        public static final String OVERVIEW_BEAN_PATH_DOC_TYPE = "hsl:WebPage";
        public static final String INITIAL_LOCATION = "/content/documents/hsl/pages";
        public static final String OVERVIEW_LINK_LABEL = "overviewLinkLabel";

        public static final String SORT_ORDER = "sortOrder";
        public static final String SORT_BY = "sortBy";

        public static final String THEMA_FILTER = "themaFilter";
        public static final String OVER_FILTER = "overFilter";
        
        public static final String FORMS_INITIAL_LOCATION = "/content/documents/hsl/componenten/formulieren";
        
        public static final String WEB_MASTER_MESSAGE = "webMasterMessage";
        
        private WidgetConstants() {
        }
        
    }

    public static class PikcerTypes extends PikcerTypesConstants {
        public static final String IMAGE_PICKER = "cms-pickers/images";

        private PikcerTypes() {
        }
    }

    public static class HstParameters extends HstParametersConstants {

        private HstParameters() {
        }
    }
    
    public static class DisplayedFieldNames {
        
        public static final String RELEASE_DATE = "Publicatiedatum";
        public static final String TITLE = "Titel";
        
        private DisplayedFieldNames() {
        }

    }

    public static class Values extends ValuesConstants {

        private Values() {
        }
    }

    public static class Parameters extends ParametersConstants {

        private Parameters() {
        }
    }

    public static class FieldName extends FieldNameConstants {

        public static final String RELEASE_DATE = "hsl:releaseDate";
        public static final String TITLE = "hsl:title";
        
        private FieldName() {
        }
    }

    public static class NodeName extends NodeNameConstants {

        private NodeName() {
        }
    }
}
