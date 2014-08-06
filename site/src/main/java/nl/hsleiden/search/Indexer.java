package nl.hsleiden.search;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.jcr.Credentials;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.hippoecm.hst.site.HstServices;
import org.hippoecm.repository.HippoStdNodeType;
import org.hippoecm.repository.api.HippoNodeType;
import org.onehippo.cms7.event.HippoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.commons.utils.general.Html2Text;

public class Indexer implements Runnable {

    private static final String EVENT_PARAMETER_METHOD_NAME = "methodName";
    private static final String EVENT_PARAMETER_HANDLE_UUID = "handleUuid";
    private static final String EVENT_TYPE_RENAME = "rename";
    private static final String EVENT_TYPE_COPY = "copy";
    private static final String EVENT_TYPE_DELETE = "delete";
    private static final String EVENT_TYPE_COMMIT_EDITABLE_INSTANCE = "commitEditableInstance";
    private static final String EVENT_TYPE_ADD = "add";
    private static final String EVENT_TYPE_DEPUBLISH = "depublish";
    private static final String EVENT_TYPE_PUBLISH = "publish";

    private FieldNameConverter fieldNameConverter;
    @SuppressWarnings("rawtypes")
    private HippoEvent event;

    private Client client = HstServices.getComponentManager().getComponent(Client.class.getName());
    private Repository repository = HstServices.getComponentManager().getComponent(Repository.class.getName());
    private Credentials liveCred = HstServices.getComponentManager().getComponent(
            Credentials.class.getName() + ".default");
    private Credentials previewCred = HstServices.getComponentManager().getComponent(
            Credentials.class.getName() + ".preview");

    private static final Logger LOG = LoggerFactory.getLogger(Indexer.class);

    public Indexer(@SuppressWarnings("rawtypes") HippoEvent event) {
        this(event, null);
    }

    public Indexer(@SuppressWarnings("rawtypes") HippoEvent event, FieldNameConverter fieldNameConverter) {
        this.event = event;
        this.fieldNameConverter = fieldNameConverter;
    }

    @Override
    public void run() {
        try {
            String subjectPath = (String) event.get("subjectPath");
            if (StringUtils.isNotBlank(subjectPath) && subjectPath.startsWith("/content/documents")) {
                String handleUuid = (String) event.get(EVENT_PARAMETER_HANDLE_UUID);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("An event of type {} occurred with the flowing values\n{}",
                            event.get(EVENT_PARAMETER_METHOD_NAME), event.getValues().toString());
                }

                switch ((String) event.get(EVENT_PARAMETER_METHOD_NAME)) {
                case EVENT_TYPE_PUBLISH:
                    createOrUpdateIndex(handleUuid, IndexationType.PUBLIC);
                    break;
                case EVENT_TYPE_DEPUBLISH:
                    deleteIndex(handleUuid, IndexationType.PUBLIC);
                    break;
                case EVENT_TYPE_ADD:
                case EVENT_TYPE_COMMIT_EDITABLE_INSTANCE:
                    createOrUpdateIndex(handleUuid, IndexationType.PIRVATE);
                    break;
                case EVENT_TYPE_DELETE:
                    deleteIndex(handleUuid, IndexationType.PIRVATE);
                    break;
                case EVENT_TYPE_COPY:
                    LOG.warn("Copy action is not supported.");
                    break;
                case EVENT_TYPE_RENAME:
                    createOrUpdateIndex(handleUuid, IndexationType.PIRVATE);
                    break;

                default:
                    break;
                }
            }
        } catch (InterruptedException | ExecutionException | RepositoryException | IOException e) {
            LOG.error("An error occurred while trying to index \"" + event.get(EVENT_PARAMETER_HANDLE_UUID)
                    + "\" after an event of type " + (String) event.get(EVENT_PARAMETER_METHOD_NAME) + " occurred.", e);
        }
    }

    private void createOrUpdateIndex(String uuid, IndexationType type) throws RepositoryException, IOException {
        Session session = getSession(type);
        try {
            Node handel = session.getNodeByIdentifier(uuid);
            Node target = handel.getNode(handel.getName());

            IndexRequestBuilder indexBuilder = client.prepareIndex(type.getIndexName(), getConvertedName(target
                    .getPrimaryNodeType().getName()), uuid);
            indexBuilder.setSource(getJson(target));
            indexBuilder.execute();
        } finally {
            session.logout();
        }
    }

    private void deleteIndex(String uuid, IndexationType type) throws RepositoryException, InterruptedException,
            ExecutionException {
        DeleteByQueryResponse deleteResponse = client.prepareDeleteByQuery(type.getIndexName())
                .setQuery(QueryBuilders.termQuery("_id", uuid)).execute().get();
        if (deleteResponse.status() != RestStatus.OK) {
            LOG.error("failed to removed {} index of node \"{}\"", type.toString(), uuid);
        }
    }

    private Session getSession(IndexationType type) throws RepositoryException {
        Session session;
        if (IndexationType.PUBLIC == type) {
            session = getLiveSession();
        } else {
            session = getPreviewSession();
        }
        return session;
    }

    private XContentBuilder getJson(Node target) throws IOException, RepositoryException {
        XContentBuilder result = XContentFactory.jsonBuilder().startObject();
        indexNodeProperties(target, result);
        indexSubnodes(target, result);
        return result.endObject();
    }

    private void indexSubnodes(Node node, XContentBuilder builder) throws RepositoryException, IOException,
            ValueFormatException {
        for (NodeIterator nodes = node.getNodes(); nodes.hasNext();) {
            Node subnode = nodes.nextNode();
            if (!subnode.isNodeType(HippoNodeType.NT_TRANSLATION) && !subnode.isNodeType(HippoNodeType.NT_MIRROR)
                    && !subnode.getName().endsWith("Mixin")) {
                builder.startObject(getConvertedName(subnode.getName()));
                if (subnode.isNodeType(HippoStdNodeType.NT_HTML)) {

                    builder.field(getConvertedName(HippoStdNodeType.HIPPOSTD_CONTENT),
                            Html2Text.getText(subnode.getProperty(HippoStdNodeType.HIPPOSTD_CONTENT).getString()));
                } else {
                    indexNodeProperties(subnode, builder);
                }
                indexSubnodes(subnode, builder);
                builder.endObject();
            }
        }
    }

    private String getConvertedName(String name) {
        String nodeName = fieldNameConverter == null ? name : fieldNameConverter.jcrToElasticsearch(name);
        return nodeName;
    }

    private void indexNodeProperties(Node node, XContentBuilder builder) throws RepositoryException, IOException,
            ValueFormatException {
        for (PropertyIterator iterator = node.getProperties(); iterator.hasNext();) {
            Property property = iterator.nextProperty();
            int type = property.getType();
            if (type == PropertyType.STRING || type == PropertyType.DATE || type == PropertyType.BOOLEAN
                    || type == PropertyType.LONG || type == PropertyType.DOUBLE) {
                String fieldName = fieldNameConverter == null ? property.getName() : fieldNameConverter
                        .jcrToElasticsearch(property.getName());
                if (property.isMultiple()) {
                    builder.array(fieldName, getValuesAsArray(property));
                } else {
                    builder.field(fieldName, getValue(property.getValue()));
                }
            } else {
                LOG.debug("Only propeties of type String, String[], Calendar, Calendar[], Boolean, Boolean[], Long, Long[], Double and Double[] are being indexed.");
            }
        }
    }

    private Object getValue(Value value) throws RepositoryException {
        Object result = null;
        switch (value.getType()) {
        case PropertyType.STRING:
            result = value.getString();
            break;
        case PropertyType.DATE:
            result = value.getDate();
            break;
        case PropertyType.BOOLEAN:
            result = value.getBoolean();
            break;
        case PropertyType.LONG:
            result = value.getLong();
            break;
        case PropertyType.DOUBLE:
            result = value.getDouble();
            break;
        default:
            break;
        }
        return result;
    }

    private Object[] getValuesAsArray(Property property) throws RepositoryException {
        Value[] values = property.getValues();
        Object[] result = new Object[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = getValue(values[i]);
        }
        return result;
    }

    private Session getLiveSession() throws RepositoryException {
        return repository.login(liveCred);
    }

    private Session getPreviewSession() throws RepositoryException {
        return repository.login(previewCred);
    }

    enum IndexationType {
        PUBLIC("live"), PIRVATE("preview");

        private final String indexName;

        private IndexationType(String indexName) {
            this.indexName = indexName;
        }

        public String getIndexName() {
            return indexName;
        }
    }

}
