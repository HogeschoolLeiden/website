package nl.hinttech.hsleiden.pi.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * The purpose of this TagLibrary is to abbreviate the passed in text so it fits the specified maximum size. It is word
 * aware, so it will not break in the middle of a word.
 */
public class AbbreviateTag extends SimpleTagSupport {

    private String text;
    private int maxLength;

    public void setMaxLength(final String maxLength) {
        this.maxLength = Integer.parseInt(maxLength);
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public void doTag() throws JspException, IOException {

        PageContext pageContext = (PageContext) getJspContext();

        JspWriter out = pageContext.getOut();
        String result = abbreviate(text, maxLength);
        out.print(result);
    }

    public static String abbreviate(final String inputText, final int maxWidth) {

        if ((maxWidth == 0) || (inputText == null)) {
            return "";
        }

        String text = inputText.trim();
        String result = text;
        final String ELIPSIS = " ...";

        if (maxWidth <= ELIPSIS.length()) {
            if (text.length() > maxWidth) {
                result = text.substring(0, maxWidth);
            } else {
                result = text.substring(0, text.length());
            }
        } else {
            if (text.length() > ELIPSIS.length()) {

                if (text.length() > maxWidth) {
                    int lastSpacePosition = text.lastIndexOf(" ", maxWidth - ELIPSIS.length());
                    if (lastSpacePosition == -1) {
                        // no space was found
                        result = StringUtils.abbreviate(text, maxWidth);
                    } else {
                        result = text.substring(0, lastSpacePosition) + ELIPSIS;
                    }
                }
            }

        }

        return result;
    }
}
