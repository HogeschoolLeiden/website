package nl.hsleiden.search;

import nl.hsleiden.search.ElasticsearchConfig.Address;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

public class ElasticSearchClientFactor implements FactoryBean<Client>, DisposableBean {

    private final Client client;

    public ElasticSearchClientFactor(ElasticsearchConfig config) {
        Builder builder = ImmutableSettings.settingsBuilder().put("cluster.name", config.getClusterName());
        TransportClient c = new TransportClient(builder.build());
        for (Address address : config.getHosts()) {
            c.addTransportAddress(new InetSocketTransportAddress(address.getHostName(), address.getPortNumber()));
        }
        client = c;
    }

    @Override
    public Client getObject() throws Exception {
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return Client.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        this.client.close();
    }

}
