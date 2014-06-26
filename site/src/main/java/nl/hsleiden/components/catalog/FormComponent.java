package nl.hsleiden.components.catalog;

import javax.jcr.RepositoryException;

import net.sourceforge.hstmixinsupport.DynamicProxyFactory;
import nl.hsleiden.beans.mixin.FormComponentMixin;
import nl.hsleiden.componentsinfo.FormComponentInfo;
import nl.hsleiden.utils.HslUtils;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.site.HstServices;
import org.onehippo.forge.easyforms.beans.FormBean;
import org.onehippo.forge.easyforms.hst.FormStorageComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ParametersInfo(type = FormComponentInfo.class)
public class FormComponent extends FormStorageComponent {

    private static Logger LOG = LoggerFactory.getLogger(FormComponent.class);

    private final DynamicProxyFactory dynamicProxyFactory = HstServices.getComponentManager().getComponent(
            DynamicProxyFactory.class);

    @Override
    public FormBean getFormBean(final HstRequest request) {
        FormBean result = null;
        FormComponentInfo parametersInfo = this.<FormComponentInfo> getComponentParametersInfo(request);
        try {
            if (parametersInfo.getUseMixin()) {
                HippoBean proxy;
                proxy = dynamicProxyFactory.getProxy(request.getRequestContext().getContentBean());

                if (proxy instanceof FormComponentMixin) {
                    parametersInfo = ((FormComponentMixin) proxy).getFormCompoundMixin();
                }
            }
        } catch (RepositoryException e) {
            LOG.warn(e.getMessage(), e);
        }

        HippoBean selectedForm = HslUtils.getSelectedBean(request, parametersInfo.getContentBeanPath());
        if (selectedForm == null || !(selectedForm.isHippoDocumentBean()) || !(selectedForm instanceof FormBean)) {
            if (LOG.isDebugEnabled()) {
                LOG.warn("*** EASY_FORMS ***");
                LOG.warn("Form bean is either not defined or doesn't match Form bean type, [ef:form], returning null");
                LOG.warn("Here is how it comes: you've mapped something to the EasyFormComponent, however, your document either doesn't exist or it is not Form document type");
                LOG.warn("You can override [FormBean getFormBean(request)] method to fetch Form bean from some other location, e.g. through facet select");
                LOG.warn("*** EASY_FORMS ***");
            }
        }else{
            result = (FormBean) selectedForm;
        }
        return result;
    }
}
