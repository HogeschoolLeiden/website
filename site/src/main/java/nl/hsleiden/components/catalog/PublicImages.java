package nl.hsleiden.components.catalog;

import hslbeans.ImageSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.PublicImagesMixin;
import nl.hsleiden.componentsinfo.PublicImagesInfo;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentBean;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = PublicImagesInfo.class)
public class PublicImages extends AjaxEnabledComponent {

    private static final Logger LOG = LoggerFactory.getLogger(PublicImages.class);

    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            PublicImagesInfo parametersInfo = getConfiguration(request);    
            return populateModel(request, parametersInfo);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    protected Map<String, Object> populateModel(HstRequest request, PublicImagesInfo parametersInfo) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("info", parametersInfo);
        addItemsToModel(request, model, parametersInfo);
        return model;
    }
    
    private void addItemsToModel(HstRequest request, Map<String, Object> model, PublicImagesInfo parametersInfo) {
        
        HippoBean imageFolder = BeanUtils.getBeanViaAbsolutePath(parametersInfo.getContentBeanPath(), request);
        List<ImageSet> items = getFolderImages(imageFolder);
        
        if (items!=null && !items.isEmpty()) {
            model.put(Attributes.ITEMS, items);
        } else {
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.nocontacts.message");
        }
    }
    
    private List<ImageSet> getFolderImages(HippoBean imageFolder) {
        
        List<ImageSet> result = new ArrayList<ImageSet>();
        
        if(imageFolder instanceof HippoFolderBean){
            HippoFolderBean selectedFolder = ((HippoFolderBean) imageFolder);
            List<HippoDocumentBean> documents = selectedFolder.getDocuments();
            LOG.debug("images list size = " + documents.size());
            addImagesToList(result, documents);
        }     
        return result;
    }
    
    private void addImagesToList(List<ImageSet> result, List<HippoDocumentBean> documents) {
        for (HippoDocumentBean hippoDocumentBean : documents) {
            if(hippoDocumentBean instanceof ImageSet){
                result.add((ImageSet) hippoDocumentBean);
            }
        }
    }

    private PublicImagesInfo getConfiguration(HstRequest request) throws RepositoryException {
        PublicImagesInfo paramInfo = this.<PublicImagesInfo> getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && paramInfo.getUseMixin()) {
            HippoBean proxy = BeanUtils.getMixinProxy(request.getRequestContext().getContentBean());
            if (proxy instanceof PublicImagesMixin) {
                paramInfo = ((PublicImagesMixin) proxy).getPublicImagesCompoundMixinBean();
            }
        }
        return paramInfo;
    }
    
}
