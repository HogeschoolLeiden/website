package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.core.parameters.DropDownList;
import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

import com.tdclighthouse.prototype.componentsinfo.ContentBeanPathInfo;
import com.tdclighthouse.prototype.utils.Constants;

public interface PublicImagesInfo extends ContentBeanPathInfo {

    @Parameter(name = WidgetConstants.FIELD_USER_MIXIN, defaultValue = "true")
    public Boolean getUseMixin();
    
    @JcrPath(isRelative = false, pickerInitialPath = WidgetConstants.INITIAL_IMAGE_FOLDER_LOCATION, 
            pickerRemembersLastVisited=false, pickerSelectableNodeTypes = { WidgetConstants.IMAGE_FOLDER_SELECTABLE })
    @Parameter(name = Constants.HstParametersConstants.CONTENT_BEAN_PATH, displayName = "Beeldbank openbaar map", 
            defaultValue = WidgetConstants.INITIAL_IMAGE_FOLDER_LOCATION)
    public String getContentBeanPath();
    
    @DropDownList(value={"2","3","4"})
    @Parameter(name = "imagesPerRow", displayName="Beelden per rij", defaultValue="2")
    public int getImagesPerRow();
    
    @Parameter(name = WidgetConstants.SIZE, defaultValue = "6", displayName="Beelden per pagina")
    public int getSize();   
    
}
