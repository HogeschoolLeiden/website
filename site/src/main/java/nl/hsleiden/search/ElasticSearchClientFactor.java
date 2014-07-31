package nl.hsleiden.search;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ElasticSearchClientFactor {

    private static final Builder BUILDER;
    private static final Client CLIENT;

    private ElasticSearchClientFactor() {
    }

    static {
        // TODO get the cluster.name server location and port number from a
        // property file.
        BUILDER = ImmutableSettings.settingsBuilder().put("cluster.name", "ebrahimcluster");
        TransportClient c = new TransportClient(BUILDER.build());
        c.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        CLIENT = c;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                CLIENT.close();
            }
        });
    }

    public static Client getClient() {
        return CLIENT;
    }

}
