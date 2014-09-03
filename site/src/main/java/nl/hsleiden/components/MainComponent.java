package nl.hsleiden.components;

import java.util.Map;

import nl.hsleiden.componentsinfo.MainComponentInfo;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.components.Detail;
import com.tdclighthouse.prototype.utils.Constants.AttributesConstants;

@ParametersInfo(type = MainComponentInfo.class)
public class MainComponent extends Detail {
    
    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        
        request.setAttribute(AttributesConstants.PARAM_INFO, getComponentParametersInfo(request));
        return super.getModel(request, response);
    }

}
