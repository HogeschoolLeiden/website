package nl.hsleiden.componentsinfo;


import nl.hsleiden.utils.Constants.HippoNodeTypes;
import nl.hsleiden.utils.Constants.HstParameters;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.ContentBeanPathInfo;
import com.tdclighthouse.prototype.utils.Constants.HstParametersConstants;

public interface PublicImagesInfo extends ContentBeanPathInfo {

    @Parameter(name = WidgetConstants.FIELD_USER_MIXIN, defaultValue = "true", displayName=WidgetConstants.FIELD_USER_MIXIN_MESSAGE)
    public Boolean getUseMixin();
    
    @JcrPath(isRelative = false, pickerInitialPath = WidgetConstants.INITIAL_IMAGE_FOLDER_LOCATION, 
            pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { WidgetConstants.IMAGE_FOLDER_SELECTABLE })
    @Parameter(name = HstParametersConstants.CONTENT_BEAN_PATH, displayName = "Beeldbank openbaar map", 
            defaultValue = WidgetConstants.INITIAL_IMAGE_FOLDER_LOCATION)
    public String getContentBeanPath();

    @JcrPath(isRelative = false, pickerInitialPath = WidgetConstants.ROOT_IMAGE_FOLDER, 
            pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { HippoNodeTypes.HSL_IMAGE_SET })
    @Parameter(name = HstParameters.IMAGE_FOLDER_PATH, displayName = "Map afbeelding", defaultValue = WidgetConstants.DEFAULT_IMAGE_FOLDER)
    public String getImageFolderBeanPath();
    
    @Parameter(name = "imagesPerRow", displayName="Beelden per rij (allen 2, 3 of 4)", defaultValue="2")
    public int getImagesPerRow();
    
    @Parameter(name = WidgetConstants.SIZE, defaultValue = "6", displayName="Beelden per pagina")
    public int getSize();   
    
}
