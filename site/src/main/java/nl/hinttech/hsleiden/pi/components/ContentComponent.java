package nl.hinttech.hsleiden.pi.components;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.PAGE_NOT_FOUND;
import nl.hinttech.hsleiden.pi.beans.ContentDocument;
import nl.hinttech.hsleiden.pi.beans.Metadata;
import nl.hinttech.hsleiden.pi.beans.TableOfContents;
import nl.hinttech.hsleiden.pi.counters.PageViewCounter;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.util.HstResponseUtils;

/**
 * Component for rendering a ContentDocument (detail page).
 * Note: the sidebar items have their own components.
 *
 */
public class ContentComponent extends BaseComponent {

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {

        super.doBeforeRender(request, response);

        ContentDocument document = request.getRequestContext().getContentBean(ContentDocument.class);
        if (document == null) {
            HstResponseUtils.sendRedirect(request, response, PAGE_NOT_FOUND);
            return;
        }
        
        // get the metadata of the document in our Metadata object.
        Metadata metadata = document.getMetadata(this, request);
                
        request.setAttribute("document", document);
        request.setAttribute("metadata", metadata);
        
        TableOfContents toc = new TableOfContents(document.getTocTitle(), document.getTextBlocks());
        request.getRequestContext().setAttribute("tableOfContents", toc);
        
        PageViewCounter.getInstance(request).increment(document, request.getSession(), request);
        setBreadcrumb(request, document.getTitle());
    }

    
}
