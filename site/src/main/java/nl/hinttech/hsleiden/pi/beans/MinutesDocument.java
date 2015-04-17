// $Id: MinutesDocument.java 295 2013-07-08 14:12:10Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;

/**
 * Subclass of {@link TextDocument} that adds an asset folder property to the document.
 * This property is used by the front-end to show all assets (minutes) within that folder.
 * (we assume that the assets are organized within year folders within the configured asset folder).
 */
@Node(jcrType = "hsleiden:NotulenOverzicht")
public class MinutesDocument extends TextDocument {

    public static final String PROPERTY_ASSET_FOLDER = NAMESPACE_PREFIX + "assetFolder";

    public HippoFolderBean getAssetFolder() {
        return getLinkedBean(PROPERTY_ASSET_FOLDER, HippoFolderBean.class);
    }
}
