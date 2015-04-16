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
@FieldGroupList({ @FieldGroup(titleKey = "fields.website", value = { "headerName", "defaultBrowserTitle", "logoPath",
        "headerIntroTitle", "disableGoogleTagManager", "googleTagManagerConfig", "disableChatJs", "chatJsConfig" }) })
public interface WebsiteInfo extends ChannelInfo {

    @Parameter(name = "headerName", defaultValue = "HST Website")
    String getHeaderName();

    @Parameter(name = "headerIntroTitle", defaultValue = "Lectoraat")
    String getHeaderIntroTitle();

    @Parameter(name = "defaultBrowserTitle", defaultValue = "hsl")
    String getDefaultBrowserTitle();

    @Parameter(name = "logoPath", defaultValue = "/content/gallery/hsl/logos/logo.png")
    @JcrPath(pickerConfiguration = PikcerTypes.IMAGE_PICKER, pickerSelectableNodeTypes = { "hsl:ImageSet" }, pickerInitialPath = "/content/gallery/hsl/logos/")
    public String getLogoPath();

    @Parameter(name = "chatJsConfig", defaultValue = "//userlike-cdn-widgets.s3-eu-west-1.amazonaws.com/c75c13e08f5d01415c24694f65fd977dc2164059e2a23789e554ab4fbac26496.js")
    public String getChatJsConfig();

    @Parameter(name = "googleTagManagerConfig", defaultValue = "")
    public String getGoogleTagManagerConfig();

    @Parameter(name = "disableGoogleTagManager", defaultValue = "false")
    public String getDisableGoogleTagManager();

    @Parameter(name = "disableChatJs", defaultValue = "false")
    public String getDisableChatJs();

}
