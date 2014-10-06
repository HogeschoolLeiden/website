package nl.hsleiden.channels;

import nl.hsleiden.utils.Constants.PikcerTypes;

import org.hippoecm.hst.configuration.channel.ChannelInfo;
import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.hippoecm.hst.core.parameters.JcrPath;
import org.hippoecm.hst.core.parameters.Parameter;

/**
 * Retrieves the properties of a website channel.
 */
@FieldGroupList({
        @FieldGroup(
                titleKey = "fields.website",
                value = { "headerName", "defaultBrowserTitle", "logoPath" }
        )
})
public interface WebsiteInfo extends ChannelInfo {

    @Parameter(name = "headerName", defaultValue = "HST Website")
    String getHeaderName();
    
    @Parameter(name = "defaultBrowserTitle", defaultValue = "hsl")
    String getDefaultBrowserTitle();
    
    @Parameter(name = "logoPath", defaultValue = "/content/gallery/hsl/logos/logo.png")
    @JcrPath(
            pickerConfiguration = PikcerTypes.IMAGE_PICKER,
            pickerSelectableNodeTypes = { "hsl:ImageSet" },
            pickerInitialPath = "/content/gallery/hsl/logos/"
            )
    public String getLogoPath();

}
