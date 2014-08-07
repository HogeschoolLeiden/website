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

import org.apache.commons.lang3.StringUtils;
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
import com.tdclighthouse.prototype.utils.ComponentUtils;
import com.tdclighthouse.prototype.utils.Constants.ParametersConstants;
import com.tdclighthouse.prototype.utils.PaginatorWidget;

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
        List<ImageSet> items = getImages(imageFolder, request, parametersInfo);
                
        PaginatorWidget paginator = new PaginatorWidget(items.size(), getPageNumber(request),
                parametersInfo.getSize());
        model.put(Attributes.PAGINATOR, paginator); 
        
        if (items!=null && !items.isEmpty()) {
            model.put(Attributes.ITEMS, getPageImages(items, getPageNumber(request) - 1, parametersInfo.getSize()));
        } else {
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.noimages.message");
        }
    }

    private List<ImageSet> getImages(HippoBean imageFolder, HstRequest request, PublicImagesInfo info) {
        List<ImageSet> allImages = new ArrayList<ImageSet>();
        
        if(imageFolder instanceof HippoFolderBean){
            HippoFolderBean selectedFolder = ((HippoFolderBean) imageFolder);
            List<HippoDocumentBean> documents = selectedFolder.getDocuments();
            LOG.debug("images list size = " + documents.size());
            addImagesToList(allImages, documents);
        }
        return allImages;
    }

    
    private void addImagesToList(List<ImageSet> result, List<HippoDocumentBean> documents) {
        for (HippoDocumentBean hippoDocumentBean : documents) {
            if(hippoDocumentBean instanceof ImageSet){
                result.add((ImageSet) hippoDocumentBean);
            }
        }
    }
    
    private List<ImageSet> getPageImages(List<ImageSet> totalItems, int pageNumber, int itemsPerPage){
        List<ImageSet> result = new ArrayList<ImageSet>();
        int startFrom = pageNumber*itemsPerPage;
        for(int i = startFrom; i<startFrom+itemsPerPage && i<totalItems.size(); i++ ){
            result.add(totalItems.get(i));
        }
        return result;
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
    
    private int getPageNumber(HstRequest request) {
        int result = 1;
        String pageString = getPublicRequestParameter(request, 
                ComponentUtils.getComponentSpecificParameterName(request, ParametersConstants.PAGE));
        if (StringUtils.isNotBlank(pageString) && StringUtils.isNumeric(pageString)) {
            result = Integer.parseInt(pageString);
        }
        return result;
    }
    
}
