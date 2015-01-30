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
                value = { "headerName", "defaultBrowserTitle", "logoPath", "headerIntroTitle" }
        )
})
public interface WebsiteInfo extends ChannelInfo {

    @Parameter(name = "headerName", defaultValue = "HST Website")
    String getHeaderName();

    @Parameter(name = "headerIntroTitle", defaultValue = "Lectoraat")
    String getHeaderIntroTitle();
    
    @Parameter(name = "defaultBrowserTitle", defaultValue = "hsl")
    String getDefaultBrowserTitle();
    
    @Parameter(name = "logoPath", defaultValue = "/content/gallery/hsl/logos/logo.png")
    @JcrPath(
            pickerConfiguration = PikcerTypes.IMAGE_PICKER,
            pickerSelectableNodeTypes = { "hsl:ImageSet" },
            pickerInitialPath = "/content/gallery/hsl/logos/"
            )
    public String getLogoPath();
    
    @Parameter( name = "chatJsConfig", 
                defaultValue = "//userlike-cdn-widgets.s3-eu-west-1.amazonaws.com/c75c13e08f5d01415c24694f65fd977dc2164059e2a23789e554ab4fbac26496.js")
    public String getChatJsConfig();

    @Parameter( name = "googleTagManagerConfig", 
                defaultValue = "<noscript><iframe src=\"//www.googletagmanager.com/ns.html?id=GTM-55GJBQ\" "
                    + "height=\"0\" width=\"0\" style=\"display:none;visibility:hidden\"></iframe></noscript> " 
                    + "<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':"
                    + "new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0], "
                    + "j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src= "
                    + "'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f); "
                    + "})(window,document,'script','dataLayer','GTM-55GJBQ');</script>")
    public String getGoogleTagManagerConfig();

}
