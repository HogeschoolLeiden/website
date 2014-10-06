package nl.hsleiden.components.catalog;

import nl.hsleiden.componentsinfo.HelpComponentInfo;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.components.WebDocumentDetail;
import com.tdclighthouse.prototype.utils.BeanUtils;
import com.tdclighthouse.prototype.utils.Constants.AttributesConstants;

@ParametersInfo(type = HelpComponentInfo.class)
public class HelpComponent extends WebDocumentDetail {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {

        HelpComponentInfo parametersInfo = getComponentParametersInfo(request);
        request.setAttribute(AttributesConstants.DOCUMENT, BeanUtils.getBeanViaAbsolutePath(parametersInfo.getContentBeanPath(), request));
    }
    
}