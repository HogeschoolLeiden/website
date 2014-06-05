package nl.hsleiden.components.base;

import nl.hsleiden.componentsinfo.ContentBeanPathInfo;
import nl.hsleiden.utils.Constants.Attributes;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;

@ParametersInfo(type = ContentBeanPathInfo.class)
public class WebPageDetail extends BaseHslComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        HippoBean contentBean = getWebDocumetBean(request);
        if (contentBean != null) {
            request.setAttribute(Attributes.DOCUMENT, contentBean);
        }
    }

}
