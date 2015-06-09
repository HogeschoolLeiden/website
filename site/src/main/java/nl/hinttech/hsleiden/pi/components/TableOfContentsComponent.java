package nl.hinttech.hsleiden.pi.components;

import nl.hinttech.hsleiden.pi.beans.TableOfContents;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

/**
 * Component for rendering the Table Of Contents;
 */
public class TableOfContentsComponent extends BaseComponent {

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        
        // The table of contents object is set on the request by other components!!!
        // just make it available to the jsp.
        TableOfContents toc = (TableOfContents) request.getRequestContext().getAttribute("tableOfContents");
        
        request.setAttribute("tableOfContents", toc);
        
    }
}
