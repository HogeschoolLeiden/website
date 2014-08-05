package nl.hsleiden.search;

import org.junit.Assert;
import org.junit.Test;

public class NamespaceFilterFieldNameConverterTest {

    private static final String[] FIELDS = new String[] { "hsl:title", "hippotranslation:id",
            "hippostdpubwf:lastModificationDate" };

    @Test
    public void jcrToElasticsearchTest() {
        NamespaceFilterFieldNameConverter converter = new NamespaceFilterFieldNameConverter("hsl");
        Assert.assertEquals("title", converter.jcrToElasticsearch(FIELDS[0]));
        Assert.assertEquals("hippotranslation_id", converter.jcrToElasticsearch(FIELDS[1]));
        Assert.assertEquals("hippostdpubwf_lastModificationDate", converter.jcrToElasticsearch(FIELDS[2]));
    }

    @Test
    public void consistancyTest() {
        NamespaceFilterFieldNameConverter converter = new NamespaceFilterFieldNameConverter("hsl");
        for (String field : FIELDS) {
            Assert.assertEquals(field, converter.elasticsearchToJcr(converter.jcrToElasticsearch(field)));
        }
    }

}
