package nl.hsleiden.components.catalog;

import java.util.HashMap;
import java.util.Map;

import nl.hsleiden.beans.Blogpost;
import nl.hsleiden.componentsinfo.DisqusInfo;
import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;

@ParametersInfo(type = DisqusInfo.class)
public class Disqus extends AjaxEnabledComponent {

    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        if(request.getRequestContext().getContentBean() instanceof Blogpost){            
            DisqusInfo info = this.<DisqusInfo> getComponentParametersInfo(request);
            result.put(Constants.Attributes.PARAM_INFO, info);
        }else{
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.disqus.only.blogs");
        }
        return result;
    }

}
