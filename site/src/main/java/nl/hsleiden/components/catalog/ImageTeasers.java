package nl.hsleiden.components.catalog;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.ImageTeasersMixin;
import nl.hsleiden.componentsinfo.ImageTeasersInfo;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = ImageTeasersInfo.class)
public class ImageTeasers extends Teasers {

    private static final Logger LOG = LoggerFactory.getLogger(ImageTeasers.class);

    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            ImageTeasersInfo parametersInfo = getConfiguration(request);    
            return populateModel(request, parametersInfo);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    protected Map<String, Object> populateModel(HstRequest request, ImageTeasersInfo parametersInfo) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("info", parametersInfo);
        super.addItemsToModel(request, model, parametersInfo, "webmaster.no.image.teasers.message");
        return model;
    }
   
    private ImageTeasersInfo getConfiguration(HstRequest request) throws RepositoryException {
        ImageTeasersInfo paramInfo = this.<ImageTeasersInfo> getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && request.getRequestContext().getContentBean() != null 
                && paramInfo.getUseMixin()) {
            HippoBean proxy = BeanUtils.getMixinProxy(request.getRequestContext().getContentBean());
            if (proxy instanceof ImageTeasersMixin) {
                paramInfo = ((ImageTeasersMixin) proxy).getImageTeasersCompoundMixinBean();
            }
        }
        return paramInfo;
    }
}
