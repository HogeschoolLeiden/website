package nl.hsleiden.eforms;

import java.io.InputStream;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;

import nl.hsleiden.components.catalog.FormComponent;
import nl.hsleiden.utils.ITextPdfForm;

import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.ComponentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onehippo.cms7.eforms.hst.beans.FormBean;
import com.onehippo.cms7.eforms.hst.behaviors.ConfirmationBehavior;
import com.onehippo.cms7.eforms.hst.model.Form;
import com.onehippo.cms7.eforms.hst.util.MailSender;
import com.onehippo.cms7.eforms.hst.util.MailTemplate;

public class ConfirmationMailBehavior extends ConfirmationBehavior {

    private static final Logger LOG = LoggerFactory.getLogger(QRFreemarkerParser.class);

    @Override
    protected void sendMail(HstRequest request, ComponentConfiguration config, final FormBean formBean, Form form,
            FormMap map) throws MessagingException, RepositoryException {

        List<Address> emailAddresses = getEmailAddresses(formBean, map);
        if (emailAddresses != null && !emailAddresses.isEmpty()) {

            // SENDER STUFF: sender from CMS managed behaviour or from HST
            // config
            String sessionName = getComponentParameter(request, config, PARAM_MAIL_SESSION, MAIL_SESSION_NAME);
            Session session = getSession(sessionName);

            MailSender sender;
            String confirmationSender = getConfirmationSenderAddress(formBean);
            if (confirmationSender != null) {
                sender = new MailSender(session, null, confirmationSender);
            } else {
                // fallback to HST config
                String fromName = getComponentParameter(request, config, PARAM_FROM_NAME, null);
                String fromEmail = getComponentParameter(request, config, PARAM_FROM_EMAIL, null);
                sender = new MailSender(session, fromName, fromEmail);
            }

            // RECIPIENT STUFF: subject from CMS managed behaviour or from
            // properties file
            String subject = getConfirmationSubject(formBean, map);
            if (subject == null) {
                subject = getLocalizedMessage("confirmation.subject", request.getLocale());
            }

            // TEMPLATE
            final String confirmationText = getConfirmationText(formBean);
            final boolean includeFieldData = isConfirmationIncludeFields(formBean);
            MailTemplate mail = new MailTemplate(sender, emailAddresses, subject, getHtml(request, config, form, map,
                    confirmationText, includeFieldData), getPlainText(request, config, form, map, confirmationText,
                    includeFieldData));

            LOG.debug("Sending confirmation e-mail to {}", emailAddresses);

            if (FormComponent.isSendPdf(request)) {
                addFormDataAsPdfAttachment(request, mail, formBean, form, map);
            } else {
                addSelectedAttachment(request, mail);
            }

            mail.sendMessage();
        }
    }

    private void addSelectedAttachment(HstRequest request, MailTemplate mail) {
        String attachmentFileName = FormComponent.getAttachmentFileName(request);
        InputStream attachmentData = FormComponent.getAttachmentData(request);
        attachDataToMail(mail, attachmentFileName, attachmentData);
    }

    private void attachDataToMail(MailTemplate mail, String attachmentFileName, InputStream attachmentData) {
        if (attachmentData != null && attachmentFileName != null) {
            mail.addAttachment(attachmentFileName, attachmentData);
        }
    }

    private void addFormDataAsPdfAttachment(HstRequest request, MailTemplate mail, FormBean formBean, Form form,
            FormMap map) {
        String attachmentFileName = getPdfFormFileName(request, formBean);
        InputStream attachmentData = getPdfFormData(formBean, form, map);
        attachDataToMail(mail, attachmentFileName, attachmentData);
    }

    private InputStream getPdfFormData(FormBean formBean, Form form, FormMap map) {
        InputStream result = null;
        result = new ITextPdfForm().createFormPdf(formBean, form, getIntroText(formBean), map);
        return result;
    }

    private String getPdfFormFileName(HstRequest request, FormBean formBean) {
        String result = request.getRequestContext().getBaseURL().getPathInfo().replaceAll("/", "_");
        String formName = formBean.getFormName();
        if (formName != null && !formName.isEmpty()) {
            result = formName;
        }
        return result + ".pdf";
    }

    private String getIntroText(FormBean formBean) {
        String result = null;
        try {
            result = super.getConfirmationText(formBean);
        } catch (RepositoryException e) {
            LOG.error("error getting confirmation text of form", e);
        }
        return result;
    }
}
