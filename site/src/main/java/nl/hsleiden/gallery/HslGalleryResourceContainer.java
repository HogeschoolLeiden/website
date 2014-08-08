package nl.hsleiden.gallery;

import org.hippoecm.hst.core.linking.AbstractResourceContainer;

public class HslGalleryResourceContainer extends AbstractResourceContainer {

    @Override
    public String getNodeType() {
        return "hsl:ImageSet";
    }

}
