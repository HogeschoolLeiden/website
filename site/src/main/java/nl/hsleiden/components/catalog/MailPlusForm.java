package nl.hsleiden.components.catalog;

import hslbeans.MailPlusCompoundMixin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.MailPlusMixin;
import nl.hsleiden.componentsinfo.MailPlusFormInfo;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.Constants.WidgetConstants;
import nl.hsleiden.utils.HslUtils;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = MailPlusFormInfo.class)
public class MailPlusForm extends AjaxEnabledComponent {

    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            MailPlusCompoundMixin mixinInfo = null;
            MailPlusFormInfo paramInfo = this.<MailPlusFormInfo> getComponentParametersInfo(request);
            if (paramInfo.getUseMixin() != null && request.getRequestContext().getContentBean() != null
                    && paramInfo.getUseMixin()) {
                mixinInfo = getMixinConfiguration(request);
            }
            return populateModel(request, mixinInfo);
        } catch (RepositoryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }
    
    private MailPlusCompoundMixin getMixinConfiguration(HstRequest request) throws RepositoryException {

        MailPlusCompoundMixin result = null;

        HippoBean contentBean = HslUtils.getBean(request);
        HippoBean proxy = BeanUtils.getMixinProxy(contentBean);
        if (proxy instanceof MailPlusMixin) {
            result = ((MailPlusMixin) proxy).getMailPlusCompoundMixin();
        }

        return result;
    }

    private Map<String, Object> populateModel(HstRequest request, MailPlusCompoundMixin mixinInfo) {
        Map<String, Object> model = new HashMap<String, Object>();
        if (mixinInfo == null) {
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.mailplus.nomixin");
        }else{            
            model.put("info", mixinInfo);
            List<HippoBean> items = new ArrayList<HippoBean>();
            items.add(mixinInfo.getWidgetParameters());
            model.put(Attributes.ITEMS, items);
        }
        return model;
    }
}
