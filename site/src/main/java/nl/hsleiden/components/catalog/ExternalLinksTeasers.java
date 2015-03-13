package nl.hsleiden.components.catalog;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.ExternalLinksTeasersMixin;
import nl.hsleiden.componentsinfo.ExternalLinksTeasersInfo;
import nl.hsleiden.utils.HslUtils;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = ExternalLinksTeasersInfo.class)
public class ExternalLinksTeasers extends Teasers {

    private static final Logger LOG = LoggerFactory.getLogger(ExternalLinksTeasers.class);

    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            ExternalLinksTeasersInfo parametersInfo = getConfiguration(request);
            return populateModel(request, parametersInfo);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    protected Map<String, Object> populateModel(HstRequest request, ExternalLinksTeasersInfo parametersInfo) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("info", parametersInfo);
        super.addItemsToModel(request, model, parametersInfo, "webmaster.no.extlinks.teasers.message");
        return model;
    }

    private ExternalLinksTeasersInfo getConfiguration(HstRequest request) throws RepositoryException {
        ExternalLinksTeasersInfo paramInfo = this.<ExternalLinksTeasersInfo> getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && request.getRequestContext().getContentBean() != null
                && paramInfo.getUseMixin()) {

            HippoBean contentBean = HslUtils.getBean(request);
            HippoBean proxy = BeanUtils.getMixinProxy(contentBean);
            if (proxy instanceof ExternalLinksTeasersMixin) {
                paramInfo = ((ExternalLinksTeasersMixin) proxy).getExternalLinksTeasersCompoundMixinBean();
            }
        }
        return paramInfo;
    }
}
