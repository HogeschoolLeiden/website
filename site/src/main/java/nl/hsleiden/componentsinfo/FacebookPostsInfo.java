package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.core.parameters.Parameter;

public interface FacebookPostsInfo {

    @Parameter(name = Constants.WidgetConstants.FIELD_USER_MIXIN, defaultValue = "false", displayName=WidgetConstants.FIELD_USER_MIXIN_MESSAGE)
    public Boolean getUseMixin();
    
    @Parameter(name = "firstPost", displayName = "Eerste Post", defaultValue = "", description="After copy-pasting the post you want to embed, adjust its width according to the part of the page where the widget will be displayed. Values should be inside this range: 350 - 750. Smaller values will be converted to 350, bigger values will be converted to 750.")
    public String getFistPost();
    
    @Parameter(name = "secondPost", displayName = "Tweede Post", defaultValue = "", description="After copy-pasting the post you want to embed, adjust its width according to the part of the page where the widget will be displayed. Values should be inside this range: 350 - 750. Smaller values will be converted to 350, bigger values will be converted to 750.")
    public String getSecondPost();
    
    @Parameter(name = "thirdPost", displayName = "Deerde Post", defaultValue = "", description="After copy-pasting the post you want to embed, adjust its width according to the part of the page where the widget will be displayed. Values should be inside this range: 350 - 750. Smaller values will be converted to 350, bigger values will be converted to 750.")
    public String getThirdPost();

}
