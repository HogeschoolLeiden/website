package nl.hsleiden.beans.compounds;

import nl.hsleiden.componentsinfo.TextTeasersInfo;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@Node(jcrType = "hsl:TextTeasersCompoundMixin")
public class TextTeasersCompoundMixinBean extends hslbeans.TextTeasersCompoundMixin implements TextTeasersInfo {

    @Override
    public Boolean getUseMixin() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getFirstTeaser() {
        String result = null;
        if (!getTextteasers().isEmpty()) {
            HippoBean textTeaserBean = getTextteasers().get(0);
            if (textTeaserBean != null) {
                result = textTeaserBean.getPath();
            }
        }
        return result;
    }

    @Override
    public String getSecondTeaser() {
        String result = null;
        if (getTextteasers().size() > 1) {
            HippoBean textTeaserBean = getTextteasers().get(1);
            if (textTeaserBean != null) {
                result = textTeaserBean.getPath();
            }
        }
        return result;
    }
}
