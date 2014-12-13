package nl.hsleiden.gallery;

import java.util.Map.Entry;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.hippoecm.hst.core.linking.AbstractResourceContainer;
import org.hippoecm.repository.api.HippoNodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetinaResourceContainer extends AbstractResourceContainer {

    private static final Logger LOG = LoggerFactory.getLogger(RetinaResourceContainer.class);

    @Override
    public String getNodeType() {
        return "hsl:ImageSet";
    }

    public Node resolveToResourceNode(Session session, String pathInfo) {
        
        String actualPath = pathInfo;
        String[] elems = actualPath.substring(1).split("/");

        String mapTo = getMapTo(actualPath, elems);
        actualPath = getRetinaPath(actualPath, elems, mapTo);

        try {
            Item item = session.getItem(actualPath);
            if (!item.isNode()) {
                LOG.debug("path '{}' does not point to a node", actualPath);
                return null;
            }
            Node node = (Node) item;
            if (node.isNodeType(HippoNodeType.NT_RESOURCE)) {
                // the path directly map to a resource node: return this one
                LOG.debug("Resource Node found at '{}'. Return resource", actualPath);
                return node;
            }
            if (node.isNodeType(HippoNodeType.NT_HANDLE)) {
                try {
                    node = node.getNode(node.getName());
                } catch (PathNotFoundException e) {
                    LOG.info("Cannot return binary for a handle with no hippo document. Return null", e);
                    return null;
                }
            }

            if (node.isNodeType(getNodeType())) {
                if (mapTo != null && node.hasNode(mapTo)) {
                    Node resourceNode = node.getNode(mapTo);
                    if (resourceNode.isNodeType(HippoNodeType.NT_RESOURCE)) {
                        return resourceNode;
                    }
                    LOG.debug(
                            "Expected resource node of type '{}' but found node of type '{}'. Try to return the primary item from this resource container.",
                            HippoNodeType.NT_RESOURCE, resourceNode.getPrimaryNodeType().getName());
                }
                if (getPrimaryItem() != null && node.hasNode(getPrimaryItem())) {
                    Node resourceNode = node.getNode(getPrimaryItem());
                    if (resourceNode.isNodeType(HippoNodeType.NT_RESOURCE)) {
                        return resourceNode;
                    }
                    LOG.debug(
                            "Expected resource node of type '{}' but found node of type '{}'. Try to return the primary jcr item (primary item as in cnd).",
                            HippoNodeType.NT_RESOURCE, resourceNode.getPrimaryNodeType().getName());

                }
                if (!hasPrimaryItem(node)) {
                    LOG.debug("Node of type '{}' at path '{}' does not have a primary jcr item. Return null.", node
                            .getPrimaryNodeType().getName(), node.getPath());
                    return null;
                }
                Item primItem = node.getPrimaryItem();
                if (primItem.isNode()) {
                    Node resourceNode = (Node) primItem;
                    if (resourceNode.isNodeType(HippoNodeType.NT_RESOURCE)) {
                        return resourceNode;
                    } else {
                        LOG.debug("Expected resource node of type '{}' but found node of type '{}'. Return null.",
                                HippoNodeType.NT_RESOURCE, resourceNode.getPrimaryNodeType().getName());
                    }
                } else {
                    LOG.debug("Primary jcr item was a property where we expected a node of type '{}'. Return null",
                            HippoNodeType.NT_RESOURCE);
                }
                return null;
            } else {
                LOG.debug(
                        "'{}' is not a resource container that is applicable for node of type '{}' at path :"
                                + node.getPath() + ". Return null", this.getClass().getName(), node
                                .getPrimaryNodeType().getName());
            }
        } catch (PathNotFoundException e) {
            LOG.info("PathNotFoundException", e);
            LOG.debug("Cannot find resource node for path '{}' beloning to pathInfo '{}'", actualPath, pathInfo);
        } catch (RepositoryException e) {
            LOG.info("RepositoryException", e);
            LOG.warn("RepositoryException: '{}'", e.getMessage());
        }

        return null;
    }

    private String getRetinaPath(String actualPath, String[] elems, String mapTo) {
        String result = actualPath;
        if (mapTo != null) {
            result = result.substring(1).substring(elems[0].length());
        }

        if (result.contains("@2x.")) {
            result = result.replace("@2x.", ".");
        }
        return result;
    }

    private String getMapTo(String actualPath, String[] elems) {
        String mapTo = null;
        for (Entry<String, String> mapping : getMappings().entrySet()) {
            if (mapping.getValue().equals(elems[0])) {
                mapTo = mapping.getKey();
                break;
            }
        }

        if (actualPath.contains("@2x.")) {
            mapTo = mapTo + "2x";
        }

        return mapTo;
    }

    private boolean hasPrimaryItem(final Node node) throws RepositoryException {
        return node.getPrimaryNodeType().getPrimaryItemName() != null;
    }

}
