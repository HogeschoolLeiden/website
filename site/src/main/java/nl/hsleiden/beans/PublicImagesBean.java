package nl.hsleiden.beans;

import hslbeans.ImageSet;
import hslbeans.PublicImages;

import java.util.ArrayList;
import java.util.List;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentBean;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Node(jcrType = "hsl:PublicImages")
public class PublicImagesBean extends PublicImages{
    
    private static final Logger LOG = LoggerFactory.getLogger(PublicImagesBean.class);

    private List<ImageSet> images;
    
    public List<ImageSet> getImages() {
        if (this.images == null) {
            this.images = getFolderImages(getImageFolder());
        }
        return this.images;
    }

    private List<ImageSet> getFolderImages(HippoBean imageFolder) {
        
        List<ImageSet> result = new ArrayList<ImageSet>();
        
        if(imageFolder instanceof HippoFolderBean){
            
            HippoFolderBean selectedFolder = ((HippoFolderBean) imageFolder);
            
            List<HippoFolderBean> subFolders = selectedFolder.getFolders();
            LOG.debug("subFolders size = " + subFolders.size());
            
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
}
