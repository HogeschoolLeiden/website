package nl.hsleiden.components.catalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.TextTeasersMixin;
import nl.hsleiden.componentsinfo.TextTeasersInfo;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = TextTeasersInfo.class)
public class TextTeasers extends AjaxEnabledComponent {

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
        addItemsToModel(request, model, parametersInfo);
        return model;
    }
    
    private void addItemsToModel(HstRequest request, Map<String, Object> model, TextTeasersInfo parametersInfo) {
        List<HippoBean> items = new ArrayList<HippoBean>();
        
        addItem(request, parametersInfo.getFirstTeaser(), items);
        addItem(request, parametersInfo.getSecondTeaser(), items);
        
        if (!items.isEmpty()) {
            model.put(Attributes.ITEMS, items);
        } else {
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.noteasers.message");
        }
    }

    private void addItem(HstRequest request, String path, List<HippoBean> items) {
        if(BeanUtils.getBeanViaAbsolutePath(path, request)!=null){
            items.add(BeanUtils.getBeanViaAbsolutePath(path, request));
        }
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
