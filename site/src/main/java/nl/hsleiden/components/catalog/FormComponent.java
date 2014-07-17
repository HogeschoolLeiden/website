package nl.hsleiden.components.catalog;

import javax.jcr.RepositoryException;

import net.sourceforge.hstmixinsupport.DynamicProxyFactory;
import nl.hsleiden.beans.mixin.FormComponentMixin;
import nl.hsleiden.componentsinfo.FormComponentInfo;
import nl.hsleiden.utils.HslUtils;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.site.HstServices;
import org.onehippo.forge.easyforms.beans.FormBean;
import org.onehippo.forge.easyforms.hst.FormStorageComponent;
import org.onehippo.forge.easyforms.model.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = FormComponentInfo.class)
public class FormComponent extends FormStorageComponent {

    private static final Logger LOG = LoggerFactory.getLogger(FormComponent.class);

    private final DynamicProxyFactory dynamicProxyFactory = HstServices.getComponentManager().getComponent(
            DynamicProxyFactory.class);

    @Override
    public FormBean getFormBean(final HstRequest request) {
        FormBean result = null;
        FormComponentInfo parametersInfo = getFormParametersInfo(request);

        HippoBean selectedForm = BeanUtils.getBeanViaAbsolutionPath(parametersInfo.getContentBeanPath(), request);      
        if (selectedForm == null || !(selectedForm.isHippoDocumentBean()) || !(selectedForm instanceof FormBean)) {
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.noform.message");
        } else {
            result = (FormBean) selectedForm;
        }
        return result;
    }

    @Override
    public void onProcessDone(final HstRequest request, final HstResponse response, final Form form, final FormMap map) {
        LOG.debug("@process finished");
        String redirect = getRedirectionSitemap(request);
        redirectToSitemap(request, response, redirect);

    }
    
    private FormComponentInfo getFormParametersInfo(final HstRequest request) {
        FormComponentInfo parametersInfo = this.<FormComponentInfo> getComponentParametersInfo(request);
        try {
            if (parametersInfo.getUseMixin()) {
                HippoBean proxy = dynamicProxyFactory.getProxy(request.getRequestContext().getContentBean());

                if (proxy instanceof FormComponentMixin) {
                    parametersInfo = ((FormComponentMixin) proxy).getFormCompoundMixin();
                }
            }
        } catch (RepositoryException e) {
            LOG.warn(e.getMessage(), e);
        }
        return parametersInfo;
    }

    private String getRedirectionSitemap(final HstRequest request) {
        String result = getComponentParameter(request, ATTRIBUTE_DONE_REDIRECT, null);
        FormComponentInfo parametersInfo = getFormParametersInfo(request);
        String beanPath = parametersInfo.getThanksBeanPath();
        if (beanPath!=null && !beanPath.isEmpty()) {
            result = HslUtils.getMatchingSitemap(request, beanPath);
        }
        return result;
    }

    private void redirectToSitemap(final HstRequest request, final HstResponse response, String redirect) {
        if (notEmpty(redirect)) {
            LOG.debug("redirecting to {}", redirect);
            sendRedirect(redirect, request, response);
        }
    }
}
