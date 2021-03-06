package nl.hsleiden.components.catalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.ContactPersonsMixin;
import nl.hsleiden.componentsinfo.ContactPersonsInfo;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.Constants.WidgetConstants;
import nl.hsleiden.utils.HslUtils;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = ContactPersonsInfo.class)
public class ContactPersons extends AjaxEnabledComponent {

    private static final Logger LOG = LoggerFactory.getLogger(ContactPersons.class);

    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            ContactPersonsInfo parametersInfo = getConfiguration(request);
            return populateModel(request, parametersInfo);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    protected Map<String, Object> populateModel(HstRequest request, ContactPersonsInfo parametersInfo) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("info", parametersInfo);
        addItemsToModel(request, model, parametersInfo);
        return model;
    }

    private void addItemsToModel(HstRequest request, Map<String, Object> model, ContactPersonsInfo parametersInfo) {
        List<HippoBean> items = new ArrayList<HippoBean>();

        addItem(parametersInfo.getFirstContact(), items);
        addItem(parametersInfo.getSecondContact(), items);
        addItem(parametersInfo.getThirdContact(), items);

        if (!items.isEmpty()) {
            model.put(Attributes.ITEMS, items);
        } else {
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.nocontacts.message");
        }
    }

    private void addItem(String path, List<HippoBean> items) {
        HippoBean bean = BeanUtils.getBeanViaAbsolutePath(path);
        if (bean != null) {
            items.add(bean);
        }
    }

    private ContactPersonsInfo getConfiguration(HstRequest request) throws RepositoryException {
        ContactPersonsInfo paramInfo = this.<ContactPersonsInfo> getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && request.getRequestContext().getContentBean() != null
                && paramInfo.getUseMixin()) {

            HippoBean contentBean = HslUtils.getBean(request);
            HippoBean proxy = BeanUtils.getMixinProxy(contentBean);
            if (proxy instanceof ContactPersonsMixin) {
                paramInfo = ((ContactPersonsMixin) proxy).getContactPersonsCompoundMixinBean();
            }
        }
        return paramInfo;
    }
}
