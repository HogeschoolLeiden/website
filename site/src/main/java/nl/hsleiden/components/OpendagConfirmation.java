package nl.hsleiden.components;

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
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        RegistrationStatus status;
        Session session = null;
        try {
            String uniqueId = getPublicRequestParameter(request, "id");
            if (StringUtils.isNotBlank(uniqueId)) {
                session = FormUtils.getWritableSession();
                Node formDataNode = getFormDataNode(session, uniqueId);
                if (formDataNode != null) {

                    Node n = getFieldValueNode(formDataNode);
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
        request.setAttribute(Constants.Attributes.MODEL, status);

    }

    private void saveSession(Session session) {
        try {
            session.save();
        } catch (RepositoryException e) {
            throw new HstComponentException(e);
        }
    }

    private Node getFieldValueNode(Node formDataNode) throws RepositoryException {
        Node result = null;
        for (NodeIterator nodes = formDataNode.getNodes("hst:formfieldvalue"); nodes.hasNext();) {
            Node n = nodes.nextNode();
            if (UUIDBehaivor.REGISTRATION_COMPLETE.equals(n.getProperty("hst:formfieldname").getString())) {
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
