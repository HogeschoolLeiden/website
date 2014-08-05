package nl.hsleiden.search;

public interface FieldNameConverter {

    String jcrToElasticsearch(String fieldName);

    String elasticsearchToJcr(String fieldName);

}
