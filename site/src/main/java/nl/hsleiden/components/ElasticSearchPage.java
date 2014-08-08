package nl.hsleiden.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import nl.hsleiden.beans.FacetConfig;
import nl.hsleiden.beans.ElasticSearchFacetsBean;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.Constants.Parameters;
import nl.hsleiden.utils.FacetMapException;
import nl.hsleiden.utils.ElasticSearchUtils;

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

public class ElasticSearchPage extends BaseHstComponent {

    private Client client = HstServices.getComponentManager().getComponent(Client.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        try {
            Map<String, List<String>> selectedFacetsMap = getFacetMap();
            FilterBuilder filter = getFilter(selectedFacetsMap);
            FacetConfig[] facetConfigs = getFacetConfig();
            List<FacetBuilder> facets = getFacets(filter, facetConfigs);
            SearchRequestBuilder searchBuilder = client.prepareSearch("live").setTypes("NewsPage");
            
            searchBuilder.setQuery(QueryBuilders.queryString(getUserQuery(request)));
            searchBuilder.setPostFilter(filter);
            for (FacetBuilder facetBuilder : facets) {
                searchBuilder.addFacet(facetBuilder);
            }
            
            SearchResponse searchResponse = searchBuilder.execute().get();
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("facets", new ElasticSearchFacetsBean(searchResponse.getFacets(), selectedFacetsMap, facetConfigs));
            model.put("items", searchResponse.getHits());
            request.setAttribute(Attributes.MODEL, model);
        } catch (FacetMapException e) {
            redirectToNotFound(request, response);
        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }
    
    

    private String getUserQuery(HstRequest request) {
        String parameter = request.getRequestContext().getServletRequest().getParameter(Parameters.QUERY);
        //TODO some sort of filtering to bad characters and injection attacks.
        return parameter + "*";
        
    }



    private FilterBuilder getFilter(Map<String, List<String>> selectedFacetsMap) throws FacetMapException {
        BoolFilterBuilder filter = selectedFacetsMap.size() > 0 ? FilterBuilders.boolFilter() : null;
        for(Iterator<Entry<String, List<String>>> iterator = selectedFacetsMap.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, List<String>> entry = iterator.next();
            filter.must(FilterBuilders.termFilter(entry.getKey(), entry.getValue()));
        }
        return filter;
    }



    private List<FacetBuilder> getFacets(FilterBuilder filter, FacetConfig[] facetConfigs) throws JsonParseException, JsonMappingException,
            IOException {
        List<FacetBuilder> result = new ArrayList<FacetBuilder>();
        for (FacetConfig facetConfig : facetConfigs) {
            TermsFacetBuilder facetBuilder = FacetBuilders.termsFacet(facetConfig.getTitle()).field(facetConfig.getTerm());
            if (filter != null) {
                facetBuilder.facetFilter(filter);
            }
            result.add(facetBuilder);
        }
        return result;
    }



    private FacetConfig[] getFacetConfig() throws IOException, JsonParseException, JsonMappingException {
        String facetsParameter = getComponentParameter("facets");
        FacetConfig[] readValue = objectMapper.readValue(facetsParameter, FacetConfig[].class);
        return readValue;
    }

    private Map<String, List<String>> getFacetMap() throws FacetMapException {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        String parameter = getComponentParameter("selectedFacets");
        return ElasticSearchUtils.parseFacetUrl(result, parameter);
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

}
