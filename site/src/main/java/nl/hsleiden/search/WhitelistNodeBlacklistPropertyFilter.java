package nl.hsleiden.search;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.hippoecm.hst.core.jcr.RuntimeRepositoryException;

public class WhitelistNodeBlacklistPropertyFilter implements NodePropertyFilter {

    private final Set<String> nodeWhitelist = new HashSet<String>();
    private final Set<String> propertyBlacklist = new HashSet<String>();

    public boolean filterOutNode(Node node) {
        try {
            return !nodeWhitelist.contains(node.getPrimaryNodeType().getName());
        } catch (RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }

    public boolean filterOutProperty(Property property) {
        try {
            return propertyBlacklist.contains(property.getName());
        } catch (RepositoryException e) {
            throw new RuntimeRepositoryException(e);
        }
    }
    
    public void setNodeWhitelist(List<String> nodeWhitelist) {
        this.nodeWhitelist.addAll(nodeWhitelist);
    }
    
    public void setPropertyBlacklist(List<String> propertyBlacklist) {
        this.propertyBlacklist.addAll(propertyBlacklist);
    }

}
