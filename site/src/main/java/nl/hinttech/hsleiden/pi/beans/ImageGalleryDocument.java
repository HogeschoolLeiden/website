// $Id: ImageGalleryDocument.java 295 2013-07-08 14:12:10Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;

/**
 * Subclass of {@link TextDocument} that adds an image folder property to the document.
 * The front-end uses this property to show all images within the image folder in a gallery.
 */
@Node(jcrType = "intranet:ImageGalleryPagina")
public class ImageGalleryDocument extends TextDocument {

    public static final String PROPERTY_IMAGE_FOLDER = NAMESPACE_PREFIX + "imageFolder";

    public HippoFolderBean getImageFolder() {
        return getLinkedBean(PROPERTY_IMAGE_FOLDER, HippoFolderBean.class);
    }
}
