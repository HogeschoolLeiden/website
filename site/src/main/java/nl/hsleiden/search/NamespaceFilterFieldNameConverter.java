package nl.hsleiden.search;


public class NamespaceFilterFieldNameConverter implements FieldNameConverter {
    
    private static final String ELASTICSEARCH_NAMESPACE_SEPARATOR = "_";
    private static final String JCR_NAMESPACE_SEPARATOR = ":";
    private final String targetNamespace;

    public NamespaceFilterFieldNameConverter(String targetNamespace) {
        this.targetNamespace = targetNamespace + JCR_NAMESPACE_SEPARATOR;
    }

    @Override
    public String jcrToElasticsearch(String fieldName) {
        String result;
        if (fieldName.startsWith(targetNamespace)) {
            result = fieldName.substring(targetNamespace.length());
        } else {
            result = fieldName.replaceFirst(JCR_NAMESPACE_SEPARATOR, ELASTICSEARCH_NAMESPACE_SEPARATOR);
        }
        return result;
    }

    @Override
    public String elasticsearchToJcr(String fieldName) {
        String result;
        int indexOfSeparator = fieldName.indexOf(ELASTICSEARCH_NAMESPACE_SEPARATOR);
        if (indexOfSeparator < 0) {
            result = targetNamespace + fieldName;
        } else if (indexOfSeparator == 0) {
            result = fieldName;
        } else {
            result = fieldName.replaceFirst(ELASTICSEARCH_NAMESPACE_SEPARATOR, JCR_NAMESPACE_SEPARATOR);
        }
        return result;
    }

}
