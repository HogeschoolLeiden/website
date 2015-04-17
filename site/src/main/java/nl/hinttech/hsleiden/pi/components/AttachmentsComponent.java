package nl.hinttech.hsleiden.pi.components;

import java.util.ArrayList;
import java.util.List;

import nl.hinttech.hsleiden.pi.beans.Asset;
import nl.hinttech.hsleiden.pi.beans.ContentDocument;
import nl.hinttech.hsleiden.pi.beans.InternalLink;

import org.hippoecm.hst.content.beans.standard.HippoAsset;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

/**
 * This component is responsible for retrieving the attachments from the active content bean (of type
 * {@link ContentDocument}) and render them in the sidebar.
 * 
 * @author rob
 * 
 */
public class AttachmentsComponent extends BaseComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);

        ContentDocument document = request.getRequestContext().getContentBean(ContentDocument.class);
        
        // Get all attachments that this document links to
        List<Asset> attachments = getAttachments(document);

        request.setAttribute("document", document);
        request.setAttribute("attachments", attachments);
    }

    private List<Asset> getAttachments(ContentDocument document) {
        
        // Get all attachments (can be either an Image or a Asset)
        // and put them in a list of our own Asset type
        // The front-end is not aware of the actual asset type.
        
        List<Asset> attachments = new ArrayList<Asset>();
        List<InternalLink> links = document.getAttachments();

        for (InternalLink link : links) {
            String title = link.getTitle();
            HippoBean linkedDocument = link.getDocument();
            if (linkedDocument instanceof HippoAsset) {
                addAttachment(attachments, title, (HippoAsset) linkedDocument);

            } else if (linkedDocument instanceof HippoGalleryImageSet) {
                addAttachment(attachments, title, (HippoGalleryImageSet) linkedDocument);
            }
        }
        return attachments;
    }

    private void addAttachment(List<Asset> attachments, String title, HippoAsset linkedDocument) {

        Asset attachment = new Asset(linkedDocument, title);
        attachments.add(attachment);
    }

    private void addAttachment(List<Asset> attachments, String title, HippoGalleryImageSet linkedDocument) {

        Asset attachment = new Asset(linkedDocument, title);
        attachments.add(attachment);
    }
}
