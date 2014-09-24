package nl.hsleiden.beans.compounds;

import hslbeans.InternalLinksTeasersCompoundMixin;
import nl.hsleiden.componentsinfo.InternalLinksTeasersInfo;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@Node(jcrType = "hsl:InternalLinksTeasersCompoundMixin")
public class InternalLinksTeasersCompoundMixinBean extends InternalLinksTeasersCompoundMixin implements InternalLinksTeasersInfo {

    public Boolean getUseMixin() {
        throw new UnsupportedOperationException();
    }

    public String getFirstTeaser() {
        String result = null;
        if (!getInternallinksteasers().isEmpty()) {
            HippoBean textTeaserBean = getInternallinksteasers().get(0);
            if (textTeaserBean != null) {
                result = textTeaserBean.getPath();
            }
        }
        return result;
    }

    public String getSecondTeaser() {
        String result = null;
        if (getInternallinksteasers().size() > 1) {
            HippoBean textTeaserBean = getInternallinksteasers().get(1);
            if (textTeaserBean != null) {
                result = textTeaserBean.getPath();
            }
        }
        return result;
    }
    
    public Boolean getHorizontal() {
        return super.getHorizontal();
    }
}
