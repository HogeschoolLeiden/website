package nl.hsleiden.beans.compounds;

import nl.hsleiden.componentsinfo.HeadTeasersInfo;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@Node(jcrType = "hsl:HeadTeasersCompoundMixin")
public class HeadTeasersCompoundMixinBean extends hslbeans.HeadTeasersCompoundMixin implements HeadTeasersInfo {

    public Boolean getUseMixin() {
        throw new UnsupportedOperationException();
    }

    public String getFirstTeaser() {
        String result = null;
        if (!getTeasers().isEmpty()) {
            HippoBean contactBean = getTeasers().get(0);
            if (contactBean != null) {
                result = contactBean.getPath();
            }
        }
        return result;
    }

    public String getSecondTeaser() {
        String result = null;
        if (getTeasers().size() > 1) {
            HippoBean contactBean = getTeasers().get(1);
            if (contactBean != null) {
                result = contactBean.getPath();
            }
        }
        return result;
    }

    public String getThirdTeaser() {
        String result = null;
        if (getTeasers().size() > 2) {
            HippoBean contactBean = getTeasers().get(2);
            if (contactBean != null) {
                result = contactBean.getPath();
            }
        }
        return result;
    }

}
