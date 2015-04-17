// $Id: ImageGalleryComponent.java 460 2013-07-17 08:35:02Z rharing $
package nl.hinttech.hsleiden.pi.components;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.PAGE_NOT_FOUND;

import java.util.ArrayList;
import java.util.List;

import nl.hinttech.hsleiden.pi.beans.ImageGalleryDocument;
import nl.hinttech.hsleiden.pi.beans.Metadata;

import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.util.HstResponseUtils;

/**
 * Component for rendering a {@link ImageGalleryDocument}.
 */
public class ImageGalleryComponent extends BaseComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        
        ImageGalleryDocument document = request.getRequestContext().getContentBean(ImageGalleryDocument.class);
        if (document == null) {
            HstResponseUtils.sendRedirect(request, response, PAGE_NOT_FOUND);
            return;
        }
        
        // get the metadata of the document in our Metadata object.
        Metadata metadata = document.getMetadata(this, request);
        
        // An ImageGalleryDocument contains a link to a Gallery folder.
        // Get all images that reside in this folder.
        // The images will be displayed in a slideshow on the front-end.
        List<HippoGalleryImageSet> images = getImages(request, document);
        
        request.setAttribute("document", document);
        request.setAttribute("metadata", metadata);
        request.setAttribute("images", images);
        
        setBreadcrumb(request, document);
    }

    private List<HippoGalleryImageSet> getImages(HstRequest request, ImageGalleryDocument document) {
        
        List<HippoGalleryImageSet> images = new ArrayList<HippoGalleryImageSet>();
        if (document != null) {
            HippoFolderBean imageFolder = document.getImageFolder();
            if (imageFolder != null) {
                for (HippoGalleryImageSet image : imageFolder.getChildBeans(HippoGalleryImageSet.class)) {
                    images.add(image);
                }             
            }
        }
        return images;
    }
}
