package nl.hsleiden.components.catalog;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.HeadTeasersMixin;
import nl.hsleiden.componentsinfo.HeadTeasersInfo;
import nl.hsleiden.utils.HslUtils;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = HeadTeasersInfo.class)
public class HeadTeasers extends Teasers {

    private static final Logger LOG = LoggerFactory.getLogger(HeadTeasers.class);

    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            HeadTeasersInfo parametersInfo = getConfiguration(request);    
            return populateModel(request, parametersInfo);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    protected Map<String, Object> populateModel(HstRequest request, HeadTeasersInfo parametersInfo) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("info", parametersInfo);
        super.addItemsToModel(request, model, parametersInfo, "webmaster.no.head.teasers.message");
        return model;
    }
    
    private HeadTeasersInfo getConfiguration(HstRequest request) throws RepositoryException {
        HeadTeasersInfo paramInfo = this.<HeadTeasersInfo> getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && request.getRequestContext().getContentBean() != null 
                && paramInfo.getUseMixin()) {
        	
        	HippoBean contentBean = HslUtils.getBean(request);
        	HippoBean proxy = BeanUtils.getMixinProxy(contentBean);
            if (proxy instanceof HeadTeasersMixin) {
                paramInfo = ((HeadTeasersMixin) proxy).getHeadTeasersCompoundMixinBean();
            }
        }
        return paramInfo;
    }

    
}
