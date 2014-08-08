package nl.hsleiden.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.hsleiden.utils.ElasticSearchUtils;

import org.elasticsearch.search.facet.Facet;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.terms.TermsFacet;

public class ElasticSearchFacetsBean {

    private static final String URL_SEPARATOR = "/";
    private final Map<String, List<String>> selectedFacets;
    private final List<Category> categories = new ArrayList<Category>();

    public ElasticSearchFacetsBean(Facets facets, Map<String, List<String>> selectedFacets, FacetConfig[] facetConfig) {
        this.selectedFacets = selectedFacets;
        for (FacetConfig config : facetConfig) {

            // TODO take care of value list map
            Category category = new Category(config.getTitle(), config.getTerm(), null);
            category.addItems(facets);
            categories.add(category);

        }

    }

    public List<Category> getCategories() {
        return categories;
    }

    private String getUrl(Map<String, List<String>> facets) {
        StringBuilder sb = new StringBuilder(URL_SEPARATOR);

        for (Iterator<Entry<String, List<String>>> iterator = facets.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, List<String>> entry = iterator.next();
            if (entry.getValue() != null && entry.getValue().size() > 0) {
                for (String value : entry.getValue()) {
                    sb.append(entry.getKey()).append(URL_SEPARATOR).append(value).append(URL_SEPARATOR);
                }
            }
        }
        return sb.toString();
    }

    public class Category {
        private final Map<String, Item> items = new LinkedHashMap<String, Item>();
        private final String title;
        private final String term;
        private final Map<String, String> labelsMap;

        public Category(String title, String term, Map<String, String> labelsMap) {
            this.title = title;
            this.term = term;
            this.labelsMap = labelsMap;
        }

        public void addItems(Facets facets) {
            addSelectedItems();
            addUnselectedItem(facets);

        }

        private void addUnselectedItem(Facets facets) {
            Facet facet = facets.getFacets().get(title);
            if (facet instanceof TermsFacet) {
                for (org.elasticsearch.search.facet.terms.TermsFacet.Entry entry : ((TermsFacet) facet).getEntries()) {
                    String term = entry.getTerm().string();
                    items.put(term, new Item(entry.getTerm().string(), items.containsKey(term), entry.getCount()));
                }
            }
        }

        private void addSelectedItems() {
            List<String> list = selectedFacets.get(this.term);
            if (list != null) {
                for (String value : list) {
                    items.put(value, new Item(value, true, 0));
                }
            }
        }

        public Collection<Item> getItems() {
            return items.values();
        }

        public String getTitle() {
            return title;
        }

        public class Item {

            private final boolean selected;
            private final String value;
            private int count;

            public Item(String value, boolean selected, int count) {
                this.selected = selected;
                this.value = value;
                this.count = count;
            }

            public boolean isSelected() {
                return selected;
            }

            public String getLabel() {
                String result = value;
                if (labelsMap != null && labelsMap.containsKey(value)) {
                    result = labelsMap.get(value);
                }
                return result;
            }

            public String getUrl() {
                Map<String, List<String>> map = new HashMap<String, List<String>>();
                for (Map.Entry<String, List<String>> entry: selectedFacets.entrySet()) {
                    if (entry.getValue() != null && entry.getValue().size() > 0) {
                        List<String> list = new ArrayList<String>(entry.getValue().size());
                        list.addAll(entry.getValue());
                        map.put(entry.getKey(), list);
                    }
                }
               
                if (selected) {
                    ElasticSearchUtils.removeFromMap(map, term, value);
                } else {
                    ElasticSearchUtils.addToMap(map, term, value);
                }
                return ElasticSearchFacetsBean.this.getUrl(map);
            }

            public int getCount() {
                return count;
            }

        }

    }

}
