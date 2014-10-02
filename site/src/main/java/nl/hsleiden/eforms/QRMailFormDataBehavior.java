package nl.hsleiden.eforms;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.ComponentConfiguration;

import com.onehippo.cms7.eforms.hst.behaviors.MailFormDataBehavior;
import com.onehippo.cms7.eforms.hst.model.EmailBlock;
import com.onehippo.cms7.eforms.hst.model.Form;
import com.onehippo.cms7.eforms.hst.util.EformsConstants;
import com.onehippo.cms7.eforms.hst.util.TemplateParser;
import com.onehippo.cms7.eforms.hst.util.TemplateType;

public class QRMailFormDataBehavior extends MailFormDataBehavior {

    @Override
    protected String getHtml(final HstRequest request, ComponentConfiguration config, final Form form,
            final EmailBlock emailBlock, final FormMap formMap, final String paragraph,
            final boolean includeFieldData) {
        
        // to make sure line ends in text will appear on the next line in HTML as well,
        // first replace by a marker and replace by <br/> afterwards, avoiding the parser HTML encoding (as &lt; etc.)
        String markedParagraph = paragraph;
        if (markedParagraph != null) {
            markedParagraph = markedParagraph.replaceAll(EformsConstants.LINE_END_REGEXP,
                    EformsConstants.LINE_END_MAGIC_MARKER);
        }

        TemplateParser parser = QRFreemarkerParser.getInstance();
        String template = getMailTemplate(config, emailBlock);

        String parsedHTML;
        if (StringUtils.isNotEmpty(template)) {
            parsedHTML = parser.populateFromString(request, form, formMap, markedParagraph, includeFieldData, template,
                    TemplateType.HTML);
        } else {
            parsedHTML = parser.populateHtmlTemplate(request, form, formMap, markedParagraph, includeFieldData);
        }

        // replace the line end markers with <br/>
        parsedHTML = parsedHTML.replaceAll(EformsConstants.LINE_END_MAGIC_MARKER, EformsConstants.LINE_END_REPLACEMENT);

        return parsedHTML;
    }
    
    private String getMailTemplate(final ComponentConfiguration config, final EmailBlock emailBlock) {
        if(emailBlock != null && StringUtils.isNotEmpty(emailBlock.getMailTemplate())) {
             return emailBlock.getMailTemplate();
        }

        return config.getRawLocalParameters().get(PARAM_BODY_HTML);
    }
}
