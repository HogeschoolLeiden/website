package nl.hsleiden.components;

import java.util.HashMap;
import java.util.Map;

import nl.hsleiden.utils.Constants.Attributes;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

public class FacetDeepLinkNav extends BaseHstComponent {
    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        request.setAttribute(Attributes.MODEL, getModel(request, response));
    }

    private Map<String, Object> getModel(HstRequest request, HstResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
//        com.tdclighthouse.prototype.utils.FacetDeepLink.
        return model;
    }
}
