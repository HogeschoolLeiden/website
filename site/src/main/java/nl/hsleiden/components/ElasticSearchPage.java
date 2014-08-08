package nl.hsleiden.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.FacetMapException;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.linking.HstLinkCreator;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.site.HstServices;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdclighthouse.prototype.utils.PathUtils;

public class ElasticSearchPage extends BaseHstComponent {

    private Client client = HstServices.getComponentManager().getComponent(Client.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        try {
            FilterBuilder filter = getFilter();
            List<FacetBuilder> facets = getFacets(filter);
            SearchRequestBuilder searchBuilder = client.prepareSearch("live").setTypes("NewsPage");
            searchBuilder.setQuery(QueryBuilders.queryString("T*"));
            searchBuilder.setPostFilter(filter);
            for (FacetBuilder facetBuilder : facets) {
                searchBuilder.addFacet(facetBuilder);
            }
            
            SearchResponse searchResponse = searchBuilder.execute().get();
            request.setAttribute(Attributes.MODEL, searchResponse);
        } catch (FacetMapException e) {
            redirectToNotFound(request, response);
        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }
    
    

    private FilterBuilder getFilter() throws FacetMapException {
        Map<String, List<String>> facetMap = getFacetMap();
        BoolFilterBuilder filter = facetMap.size() > 0 ? FilterBuilders.boolFilter() : null;
        for(Iterator<Entry<String, List<String>>> iterator = facetMap.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, List<String>> entry = iterator.next();
            filter.must(FilterBuilders.termFilter(entry.getKey(), entry.getValue()));
        }
        return filter;
    }



    private List<FacetBuilder> getFacets(FilterBuilder filter) throws JsonParseException, JsonMappingException,
            IOException {
        List<FacetBuilder> result = new ArrayList<FacetBuilder>();
        String facetsParameter = getComponentParameter("facets");
        FacetConfig[] readValue = objectMapper.readValue(facetsParameter, FacetConfig[].class);
        for (FacetConfig facetConfig : readValue) {
            TermsFacetBuilder facetBuilder = FacetBuilders.termsFacet(facetConfig.getTitle()).field(facetConfig.getTerm());
            if (filter != null) {
                facetBuilder.facetFilter(filter);
            }
            result.add(facetBuilder);
        }
        return result;
    }

    private Map<String, List<String>> getFacetMap() throws FacetMapException {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        String parameter = getComponentParameter("selectedFacets");
        if (StringUtils.isNotBlank(parameter)) {
            String[] split = PathUtils.normalize(parameter).split("/");
            if (split.length % 2 == 0) {
                for (int i = 0; i < split.length / 2; i++) {
                    addToMap(result, split[i * 2], split[(i * 2) + 1]);
                }
            } else {
                throw new FacetMapException("odd number of fact segment was encountered.");
            }
        }
        return result;
    }

    private void addToMap(Map<String, List<String>> map, String key, String value) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            List<String> list = new ArrayList<String>();
            list.add(value);
            map.put(key, list);
        }
    }

    private void redirectToNotFound(HstRequest request, HstResponse response) {
        try {
            HstRequestContext requestContext = request.getRequestContext();
            HstLinkCreator linkCreator = requestContext.getHstLinkCreator();
            HstLink link = linkCreator.create("/notFound", requestContext.getResolvedMount().getMount());
            response.sendRedirect(link.toUrlForm(requestContext, false));
        } catch (IOException e) {
            throw new HstComponentException(e);
        }
    }

    public static class FacetConfig {

        private String title;
        private String term;

        public String getTitle() {
            return title;
        }

        public String getTerm() {
            return term;
        }

    }
}
