package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

/**
 * Compound type for specifying a block of formatted text.
 */
@Node(jcrType = "hsleiden:TitelTekstBlok")
public class TextBlock extends BaseDocument {

    public static final String PROPERTY_TITLE = NAMESPACE_PREFIX + "titel";
    public static final String PROPERTY_TEXT = NAMESPACE_PREFIX + "tekst";

    private HippoHtml html;
    private String title;
    
    public TextBlock() {
    }
    
    public TextBlock(HippoHtml html) {
       this.html = html;
    }

    public String getTitle() {
        if (title == null) {
            title = getProperty(PROPERTY_TITLE);
        }
        return title;
    }

    public HippoHtml getHtml() {
        if (html == null) {
            html = getBean(PROPERTY_TEXT);
        }
        return html;
    }

}