package nl.hsleiden.components.catalog;

import java.util.HashMap;
import java.util.Map;

import nl.hsleiden.componentsinfo.LinkedinInfo;
import nl.hsleiden.utils.Constants;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.components.AbstractComponent;

@ParametersInfo(type = LinkedinInfo.class)
public class Linkedin extends AbstractComponent {

    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constants.Attributes.PARAMETER_INFO, getComponentParametersInfo(request));
        return map;
    }

}
