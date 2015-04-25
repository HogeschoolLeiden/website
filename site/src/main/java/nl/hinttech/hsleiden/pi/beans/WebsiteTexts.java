package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import java.util.List;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

/**
 * Document type that can be used to configure texts within the website that have no direct link with a document.
 * Use this as some sort of resource bundle (without the translations)
 */
@Node(jcrType = "intranet:WebsiteTeksten")
public class WebsiteTexts extends BaseDocument {

    public static final String PROPERTY_TEXTS = NAMESPACE_PREFIX + "texts";
    public static final String PROPERTY_LABELS = NAMESPACE_PREFIX + "labels";
    public static final String PROPERTY_HTML_TEXTS = NAMESPACE_PREFIX + "htmlTexts";

    public List<LabelText> getLabelTexts() {
        return getChildBeansByName(PROPERTY_LABELS, LabelText.class);
    }

    public List<HtmlText> getHtmlTexts() {
        return getChildBeansByName(PROPERTY_HTML_TEXTS, HtmlText.class);
    }

    public List<SimpleText> getSimpleTexts() {
        return getChildBeansByName(PROPERTY_TEXTS, SimpleText.class);
    }

    public HippoHtml getHtmlText(String key) {

        for (HtmlText text : getHtmlTexts()) {
            if (text.getKey().equals(key)) {
                return text.getValue();
            }
        }
        return null;
    }

    public String getLabel(String key) {
        for (LabelText text : getLabelTexts()) {
            if (text.getKey().equals(key)) {
                return text.getValue();
            }
        }
        return "";
    }

    public String getText(String key) {
        for (SimpleText text : getSimpleTexts()) {
            if (text.getKey().equals(key)) {
                return text.getValue();
            }
        }
        return "";
    }
}
