package nl.hsleiden.search;

import javax.jcr.Node;
import javax.jcr.Property;

public interface NodePropertyFilter {
    
    boolean filterOutNode(Node node);
    
    boolean filterOutProperty(Property property);

}
