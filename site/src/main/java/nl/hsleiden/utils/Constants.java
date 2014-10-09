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
        public static final String HEADER_INTRO_TITLE = "headerIntroTitle";
        public static final String PARAMETER_INFO = "parameterInfo";
        public static final String TRANSLATIONS = "translations";
        public static final String NO_TRANSLATION = "noTranslation";
        public static final String CURRENT_LANGUAGE = "currentLanguage";

        private Attributes() {
        }

    }

    public static class WidgetConstants {
        public static final String FIELD_USER_MIXIN = "tryToUseMixin";

        public static final String WIDGET_TITLE = "widgetTitle";
        public static final String WIDGET_TITLE_DEFAULT = "Related Items";
        public static final String WIDGET_TITLE_DEFAULT_CONTACTS = "Contacts";
        public static final String WIDGET_TITLE_DEFAULT_NEWS = "Related News";
        public static final String WIDGET_TITLE_DEFAULT_EVENTS = "Related Events";
        public static final String SIZE = "size";
        public static final String CONTENT_BEAN_PATH = "contentBeanPath";
        public static final String CONTENT_BEAN_PATH_SELECTABLE = "hippostd:folder";
        public static final String IMAGE_FOLDER_SELECTABLE = "hippogallery:stdImageGallery";

        public static final String SHOW_OVERVIEW = "showOverviewLink";
        public static final String OVERVIEW_BEAN_PATH = "overviewBeanPath";
        public static final String THANKS_BEAN_PATH = "thanksBeanPath";
        public static final String OVERVIEW_LINK_LABEL = "overviewLinkLabel";

        public static final String CONTACT_1 = "firstContact";
        public static final String CONTACT_2 = "secondContact";
        public static final String CONTACT_3 = "thirdContact";

        public static final String HELP_COMPONENT = "helpComponent";

        public static final String TEASER_1 = "firstTeaser";
        public static final String TEASER_2 = "secondTeaser";

        public static final String SORT_ORDER = "sortOrder";
        public static final String SORT_BY = "sortBy";

        public static final String THEMA_FILTER = "themaFilter";
        public static final String OVER_FILTER = "overFilter";

        public static final String INITIAL_LOCATION = "/content/documents/hsl/pages";
        public static final String INITIAL_IMAGE_FOLDER_LOCATION = "/content/gallery/hsl/beeldbank-openbaar";
        public static final String ROOT_IMAGE_FOLDER = "/content/gallery/hsl";
        public static final String DEFAULT_IMAGE_FOLDER = "/content/gallery/hsl/folder-icon.jpg";
        public static final String FORMS_INITIAL_LOCATION = "/content/documents/hsl/componenten/formulieren";
        public static final String CONTACTS_INITIAL_LOCATION = "/content/documents/hsl/componenten/personen";
        public static final String HELP_INITIAL_LOCATION = "/content/documents/hsl/componenten/hulp-nodig";
        public static final String TEASERS_INITIAL_LOCATION = "/content/documents/hsl/componenten/teasers";

        public static final String WEB_MASTER_MESSAGE = "webMasterMessage";

        public static final String FRONT_END_MESSAGE = "frontEndMessage";

        public static final String COMPANY_PROFILE_ID = "companyProfileId";
        
        public static final String FIELD_USER_MIXIN_MESSAGE = "Probeert mixin te gebruiken";

        private WidgetConstants() {
        }

    }

    public static class PikcerTypes extends PikcerTypesConstants {
        public static final String IMAGE_PICKER = "cms-pickers/images";

        private PikcerTypes() {
        }
    }

    public static class HstParameters extends HstParametersConstants {

        public static final String IMAGE_FOLDER_PATH = "imageFolderBeanPath";
        
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

        public static final String PARENT_IMAGE_FOLDER = "parentFolder";
        public static final String HAS_PARENT_FOLDER = "hasParentFolder";
        public static final String IMAGE_FOLDER_PARAM = "folder";
        
        private Parameters() {
        }
    }

    public static class FieldName extends FieldNameConstants {

        public static final String RELEASE_DATE = "hsl:releaseDate";
        public static final String TITLE = "hsl:title";
        public static final String HSL_EVENT_DATE = "hsl:eventDate";

        private FieldName() {
        }
    }

    public static class NodeName extends NodeNameConstants {
        
        public static final String HSL_IMAGE_SET = "hsl:ImageSet";
        
        private NodeName() {
        }
    }
}
