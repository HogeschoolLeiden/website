package nl.hsleiden.beans.compounds;

import hslbeans.ImageTeasersCompoundMixin;
import nl.hsleiden.componentsinfo.ImageTeasersInfo;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@Node(jcrType = "hsl:ImageTeasersCompoundMixin")
public class ImageTeasersCompoundMixinBean extends ImageTeasersCompoundMixin implements ImageTeasersInfo {

    public Boolean getUseMixin() {
        throw new UnsupportedOperationException();
    }

    public String getFirstTeaser() {
        String result = null;
        if (!getImageteasers().isEmpty()) {
            HippoBean textTeaserBean = getImageteasers().get(0);
            if (textTeaserBean != null) {
                result = textTeaserBean.getPath();
            }
        }
        return result;
    }

    public String getSecondTeaser() {
        String result = null;
        if (getImageteasers().size() > 1) {
            HippoBean textTeaserBean = getImageteasers().get(1);
            if (textTeaserBean != null) {
                result = textTeaserBean.getPath();
            }
        }
        return result;
    }
}
