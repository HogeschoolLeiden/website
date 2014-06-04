package nl.hsleiden.components.base;

import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

public class Detail extends BaseHstComponent {

    public void doBeforeRender(HstRequest request, HstResponse response) {
        HippoBean contentBean = request.getRequestContext().getContentBean();
        if (contentBean != null) {
            request.setAttribute(Constants.Attributes.DOCUMENT, contentBean);
        }
    }
}
