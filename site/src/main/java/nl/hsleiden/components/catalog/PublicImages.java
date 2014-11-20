package nl.hsleiden.components.catalog;

import hslbeans.ImageSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.PublicImagesMixin;
import nl.hsleiden.componentsinfo.PublicImagesInfo;
import nl.hsleiden.utils.HslUtils;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.Constants.Parameters;
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
import com.tdclighthouse.prototype.utils.OverviewUtils;
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
        model.put("imageFolderBeanPath",
                BeanUtils.getBeanViaAbsolutePath(parametersInfo.getImageFolderBeanPath(), request));
        addItemsToModel(request, model, parametersInfo);
        return model;
    }

    private void addItemsToModel(HstRequest request, Map<String, Object> model, PublicImagesInfo parametersInfo) {

        HippoBean imageFolder = getImageFolder(request, model, parametersInfo);
        List<ImageSet> items = getImages(imageFolder, parametersInfo.getSize());

        PaginatorWidget paginator = new PaginatorWidget(items.size(), OverviewUtils.getPageNumber(request),
                parametersInfo.getSize());
        model.put(Attributes.PAGINATOR, paginator);

        if (items != null && !items.isEmpty()) {
            model.put(Attributes.ITEMS,
                    getPageImages(items, OverviewUtils.getPageNumber(request) - 1, parametersInfo.getSize()));
        } else {
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.noimages.message");
        }
    }

    private HippoBean getImageFolder(HstRequest request, Map<String, Object> model, PublicImagesInfo parametersInfo) {
        HippoBean imageFolder = null;
        String publicPathParameter = request.getParameter(Parameters.IMAGE_FOLDER_PARAM);

        if (publicPathParameter != null && !publicPathParameter.isEmpty()) {
            if (BeanUtils.getBeanViaAbsolutePath(publicPathParameter, request).getCanonicalPath()
                    .startsWith(parametersInfo.getContentBeanPath())) {
                imageFolder = BeanUtils.getBeanViaAbsolutePath(publicPathParameter, request);
                handleParentFolderLink(model, parametersInfo, imageFolder);
            }
        } else {
            imageFolder = BeanUtils.getBeanViaAbsolutePath(parametersInfo.getContentBeanPath(), request);
        }
        return imageFolder;
    }

    private void handleParentFolderLink(Map<String, Object> model, PublicImagesInfo parametersInfo,
            HippoBean imageFolder) {
        if (imageFolder.getParentBean().getPath().startsWith(parametersInfo.getContentBeanPath())) {
            model.put(Parameters.HAS_PARENT_FOLDER, true);
            model.put(Parameters.PARENT_IMAGE_FOLDER, imageFolder.getParentBean().getPath());
        } else {
            model.remove(Parameters.HAS_PARENT_FOLDER);
            model.remove(Parameters.PARENT_IMAGE_FOLDER);
        }
    }

    private List<ImageSet> getImages(HippoBean imageFolder, int size) {
        List<ImageSet> allImages = new ArrayList<ImageSet>();

        if (imageFolder instanceof HippoFolderBean && size > 0) {
            HippoFolderBean selectedFolder = (HippoFolderBean) imageFolder;
            addImagesToList(allImages, selectedFolder);
            addFoldersToList(allImages, selectedFolder);
        }
        return allImages;
    }

    private void addFoldersToList(List<ImageSet> allImages, HippoFolderBean imageFolder) {
        List<HippoFolderBean> subFolders = imageFolder.getFolders();
        LOG.debug("subFolders list size = " + subFolders.size());

        for (HippoFolderBean subfolder : subFolders) {
            ImageSet folderImage = new ImageSet();
            folderImage.setName(subfolder.getPath());
            folderImage.setLocalizedName(folderImage.getName().substring(folderImage.getName().lastIndexOf("/") + 1));
            allImages.add(folderImage);
        }

    }

    private void addImagesToList(List<ImageSet> result, HippoFolderBean selectedFolder) {
        List<HippoDocumentBean> documents = selectedFolder.getDocuments();
        LOG.debug("images list size = " + documents.size());

        for (HippoDocumentBean hippoDocumentBean : documents) {
            if (hippoDocumentBean instanceof ImageSet) {
                result.add((ImageSet) hippoDocumentBean);
            }
        }
    }

    private List<ImageSet> getPageImages(List<ImageSet> totalItems, int pageNumber, int itemsPerPage) {
        List<ImageSet> result = new ArrayList<ImageSet>();
        int startFrom = pageNumber * itemsPerPage;
        for (int i = startFrom; i < startFrom + itemsPerPage && i < totalItems.size(); i++) {
            result.add(totalItems.get(i));
        }
        return result;
    }

    private PublicImagesInfo getConfiguration(HstRequest request) throws RepositoryException {
        PublicImagesInfo paramInfo = this.<PublicImagesInfo> getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && paramInfo.getUseMixin()
                && request.getRequestContext().getContentBean() != null) {

            HippoBean contentBean = HslUtils.getBean(request);
            HippoBean proxy = BeanUtils.getMixinProxy(contentBean);
            if (proxy instanceof PublicImagesMixin) {
                paramInfo = ((PublicImagesMixin) proxy).getPublicImagesCompoundMixinBean();
            }
        }
        return paramInfo;
    }
}
