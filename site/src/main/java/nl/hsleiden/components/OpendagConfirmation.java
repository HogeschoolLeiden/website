package nl.hsleiden.components;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;

import nl.hsleiden.eforms.UUIDBehaivor;
import nl.hsleiden.utils.Constants;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.component.support.forms.FormUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpendagConfirmation extends BaseHstComponent {

    private static final Logger LOG = LoggerFactory.getLogger(OpendagConfirmation.class);
    private static final String HST_FORMFIELDDATA = "hst:formfielddata";

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        RegistrationStatus status;
        Session session = null;
        try {
            String uniqueId = getPublicRequestParameter(request, "id");
            if (StringUtils.isNotBlank(uniqueId)) {
                session = FormUtils.getWritableSession();
                Node formDataNode = getFormDataNode(session, uniqueId);
                status = getRegistrationStatus(model, formDataNode);
            } else {
                status = RegistrationStatus.REGISTRATION_NOT_FOUND;

            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            status = RegistrationStatus.REGISTRATION_FAILED;
        } finally {
            if (session != null) {
                saveSession(session);
            }
        }
        model.put("status", status);
        request.setAttribute(Constants.Attributes.MODEL, model);

    }

    private RegistrationStatus getRegistrationStatus(Map<String, Object> model, Node formDataNode) throws RepositoryException {
        RegistrationStatus status;
        if (formDataNode != null) {
            model.put("name", getStringValue(formDataNode, "naam"));
            model.put("surname", getStringValue(formDataNode, "achternaam"));
            Node n = getFieldValueNode(formDataNode, UUIDBehaivor.REGISTRATION_COMPLETE);
            Value[] values = n.getProperty(HST_FORMFIELDDATA).getValues();
            if (values.length > 0 && Constants.Values.TRUE.equals(values[0].getString())) {
                status = RegistrationStatus.ALREADY_REGISTERED;
            } else {
                n.setProperty(HST_FORMFIELDDATA, new String[] { Constants.Values.TRUE });
                status = RegistrationStatus.SUCCESS;
            }
        } else {
            status = RegistrationStatus.REGISTRATION_NOT_FOUND;
        }
        return status;
    }

    private void saveSession(Session session) {
        try {
            session.save();
        } catch (RepositoryException e) {
            throw new HstComponentException(e);
        }
    }
    
    private String getStringValue(Node formDataNode, String fieldName) throws RepositoryException {
        String result = null;
        Node n = getFieldValueNode(formDataNode, fieldName);
        if (n != null) {
            Value[] values = n.getProperty(HST_FORMFIELDDATA).getValues();
            if (values.length == 1) {
                result = values[0].getString();
            }
        }
        
        return result;
    }

    private Node getFieldValueNode(Node formDataNode, String fieldName) throws RepositoryException {
        Node result = null;
        for (NodeIterator nodes = formDataNode.getNodes("hst:formfieldvalue"); nodes.hasNext();) {
            Node n = nodes.nextNode();
            if (fieldName.equals(n.getProperty("hst:formfieldname").getString())) {
                result = n;
            }
        }
        return result;
    }

    private Node getFormDataNode(Session session, String uniqueId) throws RepositoryException {
        Node node = null;
        QueryManager queryManager = session.getWorkspace().getQueryManager();
        String xpathQuery = new StringBuilder(
                "//formdata/permanent//element(*,hst:formdata)[hst:formfieldvalue/@hst:formfielddata='")
                .append(uniqueId).append("']").toString();
        Query query = queryManager.createQuery(xpathQuery, "xpath");
        NodeIterator nodes = query.execute().getNodes();
        if (nodes.getSize() == 1) {
            node = nodes.nextNode();
        }
        return node;
    }

    public enum RegistrationStatus {
        SUCCESS, ALREADY_REGISTERED, REGISTRATION_NOT_FOUND, REGISTRATION_FAILED;
    }
}
