package nl.hsleiden.search;

import java.util.ArrayList;
import java.util.List;


public class ElasticsearchConfig {

    private List<Address> hosts = new ArrayList<ElasticsearchConfig.Address>();
    private String clusterName;
    
    public String getClusterName() {
        return clusterName;
    }
    
    public List<Address> getHosts() {
        return hosts;
    }

    public static class Address {
        private String hostName;
        private int portNumber;

        public String getHostName() {
            return hostName;
        }

        public int getPortNumber() {
            return portNumber;
        }

    }

}
