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
