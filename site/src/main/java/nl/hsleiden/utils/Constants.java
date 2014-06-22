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
        super();
    }

    public static class Attributes extends AttributesConstants {
        public static final String LOGO = "logo";
        public static final String HEADER_NAME = "headerName";

        private Attributes() {
            super();
        }

    }

    public static class PikcerTypes extends PikcerTypesConstants {
        public static final String IMAGE_PICKER = "cms-pickers/images";

        private PikcerTypes() {
            super();
        }
    }

    public static class HstParameters extends HstParametersConstants {

        private HstParameters() {
            super();
        }
    }

    public static class Values extends ValuesConstants {

        private Values() {
            super();
        }
    }

    public static class Parameters extends ParametersConstants {

        private Parameters() {
            super();
        }
    }

    public static class FieldName extends FieldNameConstants {

        private FieldName() {
            super();
        }
    }

    public static class NodeName extends NodeNameConstants {

        private NodeName() {
            super();
        }
    }
}
