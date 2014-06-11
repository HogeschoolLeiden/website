package nl.hsleiden.utils;

public class Constants {

    private Constants() {
        super();
    }

    public static class Attributes {
        public static final String DOCUMENT = "document";
        public static final String LOGO = "logo";
        public static final String HEADER_NAME = "headerName";
        public static final String MENU = "menu";
        public static final String ITEMS = "items";
        public static final String PAGINATOR = "paginator";

        private Attributes() {
            super();
        }
    }

    public static class PikcerTypes {
        public static final String IMAGE_PICKER = "cms-pickers/images";

        private PikcerTypes() {
            super();
        }
    }

    public static class HstParameters {
        public static final String CONTENT_BEAN_PATH = "contentBeanPath";
        public static final String ROOT = "root";
        public static final String EXPANDED = "expanded";
        public static final String EXPAND_ONLY_CURRENT_ITEM = "expandOnlyCurrentItem";

        private HstParameters() {
            super();
        }
    }

    public static class Values {
        public static final String TRUE = "true";
        public static final String DESCENDING = "descending";

        private Values() {
            super();
        }
    }
    
    public static class Parameters {
        public static final String PAGE_SIZE = "size";
        public static final String PAGE = "page";

        private Parameters() {
            super();
        }
    }
    
    public static class FieldName {
        public static final String TDC_RELEASE_DATE = "hsl:releaseDate";
        
        private FieldName() {
            super();
        }
    }

    public static class NodeName {
        public static final String INDEX = "index";

        private NodeName() {
            super();
        }
    }
}
