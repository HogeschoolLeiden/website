package nl.hsleiden.beans.compounds;

import hslbeans.ExternalLinksTeasersCompoundMixin;
import nl.hsleiden.componentsinfo.ExternalLinksTeasersInfo;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@Node(jcrType = "hsl:ExternalLinksTeasersCompoundMixin")
public class ExternalLinksTeasersCompoundMixinBean extends ExternalLinksTeasersCompoundMixin implements ExternalLinksTeasersInfo {

    public Boolean getUseMixin() {
        throw new UnsupportedOperationException();
    }

    public String getFirstTeaser() {
        String result = null;
        if (!getExternallinksteasers().isEmpty()) {
            HippoBean textTeaserBean = getExternallinksteasers().get(0);
            if (textTeaserBean != null) {
                result = textTeaserBean.getPath();
            }
        }
        return result;
    }

    public String getSecondTeaser() {
        String result = null;
        if (getExternallinksteasers().size() > 1) {
            HippoBean textTeaserBean = getExternallinksteasers().get(1);
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
