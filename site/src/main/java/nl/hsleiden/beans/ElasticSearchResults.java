package nl.hsleiden.beans;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.manager.ObjectBeanManager;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.springframework.util.Assert;

public class ElasticSearchResults {

    private final SearchHits rawHits;
    private final ObjectBeanManager objectBeanManager;
    private final List<Hit> hits;

    // private List<HippoBean>
    public ElasticSearchResults(SearchHits rawHits, ObjectBeanManager objectBeanManager) {
        Assert.notNull(rawHits);
        Assert.notNull(objectBeanManager);
        this.rawHits = rawHits;
        this.objectBeanManager = objectBeanManager;
        SearchHit[] sh = rawHits.getHits();
        hits = new ArrayList<Hit>(sh.length);
        for (SearchHit hit : sh) {
            hits.add(new Hit(hit));
        }
    }
    
    public List<Hit> getHits() {
        return hits;
    }

    public long getTotalHits() {
        return rawHits.getTotalHits();
    }

    public float getMaxScore() {
        return rawHits.getMaxScore();
    }

    public SearchHits getRawHits() {
        return rawHits;
    }

    public class Hit {
        private final SearchHit searchHit;
        private HippoBean bean;

        public Hit(SearchHit searchHit) {
            this.searchHit = searchHit;
        }

        public float getScore() {
            return searchHit.getScore();
        }

        public HippoBean getBean() throws ObjectBeanManagerException {
            if (bean == null) {
                bean = (HippoBean) objectBeanManager.getObjectByUuid(searchHit.getId());
            }
            return bean;
        }

        public SearchHit getSearchHit() {
            return searchHit;
        }
    }
}
