package nl.hsleiden.components;

import java.util.Map;

import nl.hsleiden.utils.HslHtmlRewriter;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

import com.tdclighthouse.prototype.components.AbstractComponent;
import com.tdclighthouse.prototype.componentsinfo.ContentBeanPathInfo;
import com.tdclighthouse.prototype.utils.BeanUtils;
import com.tdclighthouse.prototype.utils.Constants;

public class Detail extends AbstractComponent {

    public static final HslHtmlRewriter HSL_HTML_REWRITER = new HslHtmlRewriter();
    
    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {

        HippoBean contentBean = obtainContentBean(request);
        if (contentBean != null) {
            request.setAttribute(Constants.AttributesConstants.DOCUMENT, contentBean);
        }
        request.getRequestContext().setAttribute("hslHtmlRewriter", HSL_HTML_REWRITER);
        return null;
    }
    
    protected HippoBean obtainContentBean(HstRequest request) {
        HippoBean result = null;
        HippoBean contentBean = request.getRequestContext().getContentBean();
        if (!(contentBean instanceof HippoFacetNavigationBean)) {
            result = contentBean;
        } else {
            HippoBean bean = BeanUtils.getContentBeanViaParameters((ContentBeanPathInfo) getComponentParametersInfo(request));
            if (!(bean instanceof HippoFacetNavigationBean)) {
                result = bean;
            }
        }
        return result;
    }

}
