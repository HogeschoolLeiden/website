package nl.hsleiden.components;

import org.junit.Assert;
import org.junit.Test;

public class FacetedOverviewTest {

    @Test
    public void enhanceQueryWithStartTest() {
        testEnhanceQuery("Test*", "Test*");
    }

    @Test
    public void enhanceQueryWithoutStartTest() {
        testEnhanceQuery("Test", "Test*");
    }

    private void testEnhanceQuery(String query, String expected) {
        FacetedOverview facetedOverview = new FacetedOverview();
        String enhancedQuery = facetedOverview.enhanceQuery(query);
        Assert.assertEquals(expected, enhancedQuery);
    }

}
