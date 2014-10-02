package nl.hsleiden.components.catalog;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.TextTeasersMixin;
import nl.hsleiden.componentsinfo.TextTeasersInfo;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = TextTeasersInfo.class)
public class TextTeasers extends Teasers {

    private static final Logger LOG = LoggerFactory.getLogger(TextTeasers.class);

    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            TextTeasersInfo parametersInfo = getConfiguration(request);    
            return populateModel(request, parametersInfo);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    protected Map<String, Object> populateModel(HstRequest request, TextTeasersInfo parametersInfo) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("info", parametersInfo);
        super.addItemsToModel(request, model, parametersInfo, "webmaster.no.text.teasers.message");
        return model;
    }
   
    private TextTeasersInfo getConfiguration(HstRequest request) throws RepositoryException {
        TextTeasersInfo paramInfo = this.<TextTeasersInfo> getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && request.getRequestContext().getContentBean() != null 
                && paramInfo.getUseMixin()) {
            HippoBean proxy = BeanUtils.getMixinProxy(request.getRequestContext().getContentBean());
            if (proxy instanceof TextTeasersMixin) {
                paramInfo = ((TextTeasersMixin) proxy).getTextTeasersCompoundMixinBean();
            }
        }
        return paramInfo;
    }

    
}
