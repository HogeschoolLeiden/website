// $Id: MinorDetailComponent.java 1495 2014-01-24 12:05:56Z rharing $
package nl.hinttech.hsleiden.pi.components;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.PAGE_NOT_FOUND;
import nl.hinttech.hsleiden.pi.beans.Metadata;
import nl.hinttech.hsleiden.pi.beans.Minor;
import nl.hinttech.hsleiden.pi.counters.PageViewCounter;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.util.HstResponseUtils;

/**
 * Component responsible for rendering the details of a {@link Minor} document.
 *
 * @author rob
 */
public class MinorDetailComponent extends MinorBaseComponent {

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {

        super.doBeforeRender(request, response);

        Minor minor = request.getRequestContext().getContentBean(Minor.class);
        if (minor == null) {
            HstResponseUtils.sendRedirect(request, response, PAGE_NOT_FOUND);
            return;
        }
        
        // get the metadata of the document in our Metadata object.
        Metadata metadata = minor.getMetadata(this, request);

        request.setAttribute("minor", minor);
        request.setAttribute("metadata", metadata);
        
        // Take care that all necessary value lists are available on the request.
        // The various jsp/tags use these
        setValueList(request, "talen");
        setValueList(request, "opleidingtypes");
        setValueList(request, "onderwijstypes");
        setValueList(request, "lesdagen");
        setValueList(request, "dagdelen");
        
        PageViewCounter.getInstance(request).increment(minor, request.getSession(), request);
        
        setBreadcrumbForMinor(request, minor.getTitle());
    }
    
    
}
