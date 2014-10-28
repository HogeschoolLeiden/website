/*
 * Copyright 2010-2013 Hippo B.V. (http://www.onehippo.com)
 *
 *   Licensed under the Apache License, Version 2.0 (the  "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS"
 *   BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * Modified by Hsleiden
 */

package nl.hsleiden.eforms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.component.support.forms.FormField;
import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.onehippo.cms7.eforms.hst.model.AbstractField;
import com.onehippo.cms7.eforms.hst.model.Form;
import com.onehippo.cms7.eforms.hst.util.EformUtils;
import com.onehippo.cms7.eforms.hst.util.FreemarkerHtmlEscapedClassTemplateLoader;
import com.onehippo.cms7.eforms.hst.util.TemplateParser;
import com.onehippo.cms7.eforms.hst.util.TemplateType;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * FreemarkerParser
 *
 * @version $Id: FreemarkerParser.java 1381 2013-01-30 10:52:49Z mmilicevic $
 */
public final class QRFreemarkerParser implements TemplateParser {

    private static final String ERROR_POPULATING_TEMPLATE_DATA = "Error populating template data";

    private static final Logger LOG = LoggerFactory.getLogger(QRFreemarkerParser.class);

    private static final QRFreemarkerParser INSTANCE = new QRFreemarkerParser();

    private QRFreemarkerParser() {
    }

    private static String populateTemplate(final HstRequest request, final Form form, final FormMap formMap,
            final String paragraph, final boolean includeFieldData, final Template template,
            final TemplateType templateType) {
        if (template == null) {
            LOG.error("Template was null");
            return "";
        }
        Map<String, Object> context = populateContext(request, form, formMap, paragraph, includeFieldData);
        return populateData(template, context);
    }

    private static Map<String, Object> populateContext(final HstRequest request, final Form form,
            final FormMap formMap, final String paragraph, final boolean includeFieldData) {
        try {
            Map<String, Object> context = new HashMap<String, Object>();

            final Map<String, String> fieldMap = new HashMap<String, String>();
            final List<String> fieldNames = form.getFormRelativeUniqueFieldNames();
            for (String fieldName : fieldNames) {
                fieldMap.put(fieldName, EformUtils.getFormFieldValue(formMap, fieldName));
            }
            final List<AbstractField> fields = form.getFields();
            for (AbstractField field : fields) {
                field.setValue(EformUtils.getFormFieldValue(formMap, field.getFormRelativeUniqueName()));
            }
            FormField uniqueId = formMap.getField(UUIDBehaivor.UNIQUE_ID);
            if (uniqueId != null && StringUtils.isNotBlank(uniqueId.getValue())) {
                HstRequestContext requestContext = request.getRequestContext();
                HstLink link = requestContext.getHstLinkCreator().createByRefId("opendag-confirmation",
                        requestContext.getResolvedMount().getMount());

                String linkToConfirmationPage = link.toUrlForm(requestContext, true);
                ByteArrayOutputStream ops = new ByteArrayOutputStream();
                BitMatrix bitMatrix = new QRCodeWriter().encode(linkToConfirmationPage + "?id=" + uniqueId.getValue(),
                        BarcodeFormat.QR_CODE, 300, 300);
                MatrixToImageWriter.writeToStream(bitMatrix, "png", ops);
                ops.close();
                context.put("QR", "data:image/png;base64," + Base64.encodeBase64String(ops.toByteArray()));
            }
            context.put("form", form);
            context.put("request", request);
            context.put("fieldMap", fieldMap);
            context.put("formMap", formMap);
            context.put("paragraph", paragraph);
            context.put("includeFieldData", includeFieldData);
            return context;
        } catch (IOException | WriterException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    // from interface
    public String populateHtmlTemplate(final HstRequest request, final Form form, final FormMap formMap,
            final String paragraph, final boolean includeFieldData, final String... templatePath) {

        final int length = templatePath.length;
        if (length > 0) {
            String template = templatePath[0];
            LOG.info("Using custom velocity template: {}", template);
            return populateTemplate(request, form, formMap, paragraph, includeFieldData,
                    createTemplate(template, true), TemplateType.HTML);
        }
        return populateTemplate(request, form, formMap, paragraph, includeFieldData,
                createTemplate("com/onehippo/cms7/eforms/hst/templates/email/eforms_html.ftl", true), TemplateType.HTML);

    }

    // from interface
    public String populatePlainTextTemplate(final HstRequest request, final Form form, final FormMap formMap,
            final String paragraph, final boolean includeFieldData, final String... templatePath) {
        final int length = templatePath.length;
        if (length > 0) {
            String template = templatePath[0];
            LOG.info("Using custom velocity template: ", template);

            return populateTemplate(request, form, formMap, paragraph, includeFieldData,
                    createTemplate(template, false), TemplateType.TEXT_PLAIN);
        }
        return populateTemplate(request, form, formMap, paragraph, includeFieldData,
                createTemplate("com/onehippo/cms7/eforms/hst/templates/email/eforms_text.ftl", false),
                TemplateType.TEXT_PLAIN);
    }

    // from interface
    public String populateFromString(final HstRequest request, final Form form, final FormMap formMap,
            final String paragraph, final boolean includeFieldData, final String stringTemplate,
            final TemplateType templateType) {
        if (StringUtils.isBlank(stringTemplate)) {
            return StringUtils.EMPTY;
        }
        Template emailTemplate = createEmailTemplate(stringTemplate, templateType == TemplateType.HTML);
        return populateTemplate(request, form, formMap, paragraph, includeFieldData, emailTemplate, templateType);
    }

    private static Template createTemplate(final String templatePath, boolean escapeHtml) {
        try {
            ClassTemplateLoader ctl = escapeHtml ? new FreemarkerHtmlEscapedClassTemplateLoader(
                    QRFreemarkerParser.class, "/") : new ClassTemplateLoader(QRFreemarkerParser.class, "/");
            Configuration cfg = new Configuration();
            cfg.setTemplateLoader(ctl);

            return cfg.getTemplate(templatePath, ENCODING);

        } catch (IOException e) {
            LOG.error("Error loading freemarker template:" + templatePath, e);
        }
        return null;

    }

    /**
     * Create freemarker template for given String
     *
     * @param templateContent
     *            string containing ftl markup (freemarker template) @return
     *            freemarker template or null if creation fails
     * @param escapeHtml
     *            Escape HTML?
     * @return template
     */
    private static Template createEmailTemplate(final String templateContent, boolean escapeHtml) {
        StringTemplateLoader loader = new StringTemplateLoader();
        String name = "eforms_email_template";
        loader.putTemplate(name,
                escapeHtml ? FreemarkerHtmlEscapedClassTemplateLoader.addHtmlEscapeDirectives(templateContent)
                        : templateContent);
        Configuration config = new Configuration();
        config.setTemplateLoader(loader);
        try {
            return config.getTemplate(name, ENCODING);
        } catch (IOException e) {
            LOG.error("Error creating freemaker template", e);
        }
        return null;
    }

    /**
     * Creates body of email message which we are going to send
     *
     * @param template
     *            freemarker template
     * @param data
     *            data which will be used to populate template variables
     * @return null if populating fails
     */
    private static String populateData(final Template template, final Map<String, Object> data) {
        try {
            // NOTE: no cleanup needed
            StringWriter writer = new StringWriter();
            template.process(data, writer);
            return StringUtils.trimToEmpty(writer.toString());
        } catch (TemplateException e) {
            LOG.error(ERROR_POPULATING_TEMPLATE_DATA, e);
        } catch (IOException e) {
            LOG.error(ERROR_POPULATING_TEMPLATE_DATA, e);
        }
        return null;
    }

    public static TemplateParser getInstance() {
        return INSTANCE;
    }
}
