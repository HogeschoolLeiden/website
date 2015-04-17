package nl.hinttech.hsleiden.pi.components;

import nl.hinttech.hsleiden.pi.beans.ContentDocument;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

/**
 * Component for rendering the internal links of a {@link ContentDocument}.
 */
public class InternalLinksComponent extends BaseComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);

        ContentDocument document = request.getRequestContext().getContentBean(ContentDocument.class);

        request.setAttribute("document", document);
    }
}
