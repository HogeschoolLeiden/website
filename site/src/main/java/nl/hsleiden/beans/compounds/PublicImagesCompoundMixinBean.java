package nl.hsleiden.beans.compounds;

import nl.hsleiden.componentsinfo.PublicImagesInfo;

import org.hippoecm.hst.content.beans.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Node(jcrType = "hsl:PublicImagesCompoundMixin")
public class PublicImagesCompoundMixinBean extends hslbeans.PublicImagesCompoundMixin implements PublicImagesInfo {

    private static final String CONTENT_GALLERY_HSL_FOLDER_ICON_JPG = "/content/gallery/hsl/folder-icon.jpg";
    private static final Logger LOG = LoggerFactory.getLogger(PublicImagesCompoundMixinBean.class);
    
    public Boolean getUseMixin() {
        throw new UnsupportedOperationException();
    }

    public String getContentBeanPath() {
        String result = null;
        if(getPublicImagesParameters().getContentBeanPath()!=null){
            result = getPublicImagesParameters().getContentBeanPath().getPath();
        }
        return result;
    }

    public int getImagesPerRow() {
        int result = 0;
        String imagesPerRow = getPublicImagesParameters().getImagesPerRow();
        if(imagesPerRow!=null && !imagesPerRow.isEmpty()){
            try{                
                result = Integer.parseInt(imagesPerRow);
            }catch(NumberFormatException e){
               LOG.error("images per row value can not be converted to int"); 
            }
        }            
        return result;
    }

    public int getSize() {
        return getPublicImagesParameters().getSize().intValue();
    }

    @Override
    public String getImageFolderBeanPath() {
        String result = null;
        if(getPublicImagesParameters().getImageFolderBeanPath()!=null){
            result = getPublicImagesParameters().getImageFolderBeanPath().getPath();
        }
        if(result==null){
            result = CONTENT_GALLERY_HSL_FOLDER_ICON_JPG;
        }
        return result;
    }
}
