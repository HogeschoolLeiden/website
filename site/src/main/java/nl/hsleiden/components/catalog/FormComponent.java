package nl.hsleiden.components.catalog;

import java.io.InputStream;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import net.sourceforge.hstmixinsupport.DynamicProxyFactory;
import net.sourceforge.hstmixinsupport.HstMinxinSupportInfo;
import nl.hsleiden.beans.mixin.FormComponentMixin;
import nl.hsleiden.componentsinfo.FormComponentInfo;
import nl.hsleiden.utils.Constants.WidgetConstants;
import nl.hsleiden.utils.HslUtils;

import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.content.beans.standard.HippoAssetBean;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoResourceBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onehippo.cms7.eforms.hst.beans.FormBean;
import com.onehippo.cms7.eforms.hst.components.FormStoringEformComponent;
import com.onehippo.cms7.eforms.hst.model.Form;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = FormComponentInfo.class)
public class FormComponent extends FormStoringEformComponent {

    private static final Logger LOG = LoggerFactory.getLogger(FormComponent.class);

    private final DynamicProxyFactory dynamicProxyFactory = HstServices.getComponentManager().getComponent(
            DynamicProxyFactory.class, HstMinxinSupportInfo.MODULE_NAME);

    @Override
    public FormBean getFormBean(final HstRequest request) {
        FormBean result = null;
        FormComponentInfo parametersInfo = getConfiguration(request);

        HippoBean selectedForm = BeanUtils.getBeanViaAbsolutePath(parametersInfo.getContentBeanPath(), request);
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

    private FormComponentInfo getConfiguration(final HstRequest request) {
        FormComponentInfo parametersInfo = this.<FormComponentInfo> getComponentParametersInfo(request);
        try {
            if (parametersInfo.getUseMixin() != null && parametersInfo.getUseMixin()
                    && request.getRequestContext().getContentBean() != null) {

                HippoBean contentBean = HslUtils.getBean(request);
                HippoBean proxy = dynamicProxyFactory.getProxy(contentBean);
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
        FormComponentInfo parametersInfo = getConfiguration(request);
        String beanPath = parametersInfo.getThanksBeanPath();
        if (beanPath != null && !beanPath.isEmpty()) {
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

    public static String getAttachmentFileName(HstRequest request) {
        String result = null;
        try {
            HippoBean hippoAsset = getHippoAsset(request);
            if (hippoAsset != null) {
                Node assetJcrNode = hippoAsset.getNode();
                result = assetJcrNode.getParent().getName();
            }
        } catch (RepositoryException e) {
            LOG.error("repository exception retrieving attachment file name", e);
        }
        return result;
    }

    public static InputStream getAttachmentData(HstRequest request) {
        InputStream resultStream = null;
        try {
            HippoBean hippoAsset = getHippoAsset(request);
            if (hippoAsset != null) {
                Node assetJcrNode = hippoAsset.getNode();
                Property property = assetJcrNode.getProperty("jcr:data");
                resultStream = property.getBinary().getStream();
            }
        } catch (RepositoryException e) {
            LOG.error("repository exception retrieving attachment file data", e);
        }
        return resultStream;
    }

    private static HippoResourceBean getHippoAsset(HstRequest request) {
        HippoResourceBean result = null;
        FormComponentInfo parametersInfo = new FormComponent().getConfiguration(request);
        HippoBean hippoBean = BeanUtils.getBeanViaAbsolutePath(parametersInfo.getAttachmentBeanPath(), request);
        if (hippoBean instanceof HippoAssetBean) {
            HippoAssetBean hippoAssetBean = (HippoAssetBean) hippoBean;
            result = hippoAssetBean.getAsset();
        }
        return result;
    }

    public static boolean isSendPdf(HstRequest request) {
        FormComponentInfo parametersInfo = new FormComponent().getConfiguration(request);
        return parametersInfo.getFormDataPdf();
    }
}
