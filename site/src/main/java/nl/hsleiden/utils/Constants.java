package nl.hsleiden.utils;

public class Constants {

    private Constants() {
        super();
    }
    
    public static class Attributes {
        public static final String DOCUMENT = "document";
        public static final String LOGO = "logo";
        public static final String HEADER_NAME = "headerName";
        
        private Attributes() {
            super();
        }
    }

    public static class PikcerTypes {

        public static final String ASSET_PICKER = "cms-pickers/assets";
        public static final String DOCUMENT_PICKER = "cms-pickers/documents";
        public static final String FOLDER_PICKER = "cms-pickers/folders";
        public static final String IMAGE_PICKER = "cms-pickers/images";
        public static final String DOCUMENT_PICKER_ONLY = "cms-pickers/documents-only";

        private PikcerTypes() {
            super();
        }
    }
}
