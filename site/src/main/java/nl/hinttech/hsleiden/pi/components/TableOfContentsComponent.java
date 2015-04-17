package nl.hinttech.hsleiden.pi.components;

import nl.hinttech.hsleiden.pi.beans.ContentDocument;
import nl.hinttech.hsleiden.pi.beans.TextDocument;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

/**
 * Component for rendering the Table Of Contents of a {@link TextDocument};
 */
public class TableOfContentsComponent extends BaseComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);

        TextDocument document = request.getRequestContext().getContentBean(TextDocument.class);
        
        String tocTitle = null;
        if (document instanceof ContentDocument) {
            tocTitle = ((ContentDocument)document).getTocTitle();
        }
        if (StringUtils.isBlank(tocTitle)) {
            tocTitle = "Inhoudsopgave";
        }
        
        request.setAttribute("tocTitle", tocTitle);
        request.setAttribute("document", document);
    }
}
