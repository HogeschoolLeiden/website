package nl.hsleiden.search;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.onehippo.cms7.event.HippoEvent;
import org.onehippo.cms7.event.HippoEventConstants;
import org.onehippo.repository.events.PersistedHippoEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticSearchEventListener implements PersistedHippoEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchEventListener.class);

    Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public String getChannelName() {
        return "search";
    }

    @Override
    public String getEventCategory() {
        return HippoEventConstants.CATEGORY_WORKFLOW;
    }

    @Override
    public void onHippoEvent(@SuppressWarnings("rawtypes") HippoEvent event) {
        if ("true".equalsIgnoreCase(System.getProperty("repo.bootstrap"))) {
            executor.execute(new Indexer(event));
        } else {
            LOG.info("value of \"repo.bootstrap\" is not true and therefore this node is not going to index anything.");
        }
    }

    @Override
    public boolean onlyNewEvents() {
        return false;
    }

}