package nl.hinttech.hsleiden.pi.counters;

public abstract class Sort {

    /**
     * SortDirection enum
     */
    public enum SortDirection {

        ASCENDING("asc"), DESCENDING("desc");

        private String value;

        private SortDirection(String value) {
            this.value = value;
        }

        private String getValue() {
            return this.value;
        }

        public static SortDirection getSortDirection(String value) {
            for (SortDirection direction : values()) {
                if (direction.getValue().equals(value)) {
                    return direction;
                }
            }
            return ASCENDING;
        }

        public SortDirection toggle() {
            if (this.equals(ASCENDING)) {
                return DESCENDING;
            } else {
                return ASCENDING;
            }
        }
    }

    /**
     * The SortField enum which defines the possible sort fields.
     */
    public enum SortField {

        DATE("date"), RELEVANCE("relevance");

        private String value;

        private SortField(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static SortField getSortField(String value) {
            for (SortField field : values()) {
                if (field.getValue().equals(value)) {
                    return field;
                }
            }
            return RELEVANCE;
        }
    }
}
