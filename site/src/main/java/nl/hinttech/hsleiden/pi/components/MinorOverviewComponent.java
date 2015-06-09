// $Id: MinorOverviewComponent.java 1501 2014-01-27 08:48:58Z rharing $
package nl.hinttech.hsleiden.pi.components;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.PAGE_NOT_FOUND;

import java.util.List;

import nl.hinttech.hsleiden.pi.beans.ContentDocument;
import nl.hinttech.hsleiden.pi.beans.GroupedResult;
import nl.hinttech.hsleiden.pi.beans.Metadata;
import nl.hinttech.hsleiden.pi.beans.Minor;
import nl.hinttech.hsleiden.pi.beans.TableOfContents;
import nl.hinttech.hsleiden.pi.counters.PageViewCounter;

import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.util.HstResponseUtils;

/**
 * This component is responsible for rendering a Minor overview document.
 * NOTE: minors are organized in year folders (right under the "minoren" folder).
 * Each "year" folder contains a document (of type ContentPagina) that MUST have the name "minor".
 * The rest of the documents within the year folder are Minor documents for that specific year.
 * 
 * This component renders the "minor" ContentPagina and (right under it) the overview of Minor documents 
 * that have a startYear that matches the year folder. The minor documents are grouped by cluster.
 * 
 * @author rob
 */
public class MinorOverviewComponent extends MinorBaseComponent {

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {

        super.doBeforeRender(request, response);
        
        // the minor overview is just a normal ContentDocument... get it.
        ContentDocument document = request.getRequestContext().getContentBean(ContentDocument.class);
        if (document == null) {
            HstResponseUtils.sendRedirect(request, response, PAGE_NOT_FOUND);
            return;
        }
        
        TableOfContents toc = new TableOfContents(document.getTocTitle(), document.getTextBlocks());
        toc.addTocItem("Overzicht minoren", "minoroverview");
        // Add an extra item to the table of contents, to enable a 
        // internal link to the this overview component
        request.getRequestContext().setAttribute("tableOfContents", toc);
        
        // Get all minor documents for the specified start year...
        List<Minor> minors = getMinorsByStartYear(request, getStartYear(request));
        // and group them by cluster
        GroupedResult<Minor> groupedResult = createGroupedResult(request, minors);
               
        // get the metadata of the document in our Metadata object.
        Metadata metadata = document.getMetadata(this, request);

        request.setAttribute("groupedResult", groupedResult);
        request.setAttribute("document", document);
        request.setAttribute("metadata", metadata);
         
        PageViewCounter.getInstance(request).increment(document, request.getSession(), request);
        setBreadcrumb(request, document.getTitle());
    }
}
