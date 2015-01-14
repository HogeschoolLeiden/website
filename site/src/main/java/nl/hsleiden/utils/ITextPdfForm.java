package nl.hsleiden.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hippoecm.hst.component.support.forms.FormField;
import org.hippoecm.hst.component.support.forms.FormMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.onehippo.cms7.eforms.hst.beans.FormBean;
import com.onehippo.cms7.eforms.hst.model.AbstractField;
import com.onehippo.cms7.eforms.hst.model.Form;

public class ITextPdfForm {

    private static final Logger LOG = LoggerFactory.getLogger(ITextPdfForm.class);

    public InputStream createFormPdf(FormBean formBean, Form form, String introText, FormMap map) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Document doc = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(doc, baos);

            doc.open();

            addTitle(formBean, doc);
            doc.add(Chunk.NEWLINE);

            addFormData(form, doc, map);
            doc.add(Chunk.NEWLINE);

            addIntroduction(formBean, introText, doc);
            doc.add(Chunk.NEWLINE);

            doc.close();
        } catch (DocumentException e) {
            LOG.error("exception creating pdf out of form data", e);
        }

        return new ByteArrayInputStream(baos.toByteArray());
    }

    private void addFormData(Form form, Document doc, FormMap map) throws DocumentException {
        List<AbstractField> fields = form.getFields();

        PdfPTable tbl = new PdfPTable(2);
        for (AbstractField abstractField : fields) {
            if (abstractField.isMultiple()) {
                if ("radiogroup".equalsIgnoreCase(abstractField.getType())) {
                    addSingleValueFields(tbl, abstractField);
                } else if ("checkboxgroup".equalsIgnoreCase(abstractField.getType())){
                    addMultivalueFields(tbl, abstractField);
                    addSingleValueFormField(tbl, map.getField(abstractField.getName()+"-other"));
                }else {
                    addMultivalueFields(tbl, abstractField);
                }
            } else {
                addSingleValueFields(tbl, abstractField);
            }
        }
        doc.add(tbl);
    }

    private void addMultivalueFields(PdfPTable tbl, AbstractField abstractField) {
        Map<String, Collection<String>> multiValues = abstractField.getMultiValues();
        for (Entry<String, Collection<String>> multivaluesEntry : multiValues.entrySet()) {
            if (multivaluesEntry.getValue().toString() != null && !multivaluesEntry.getValue().isEmpty()
                    && !multivaluesEntry.getValue().toString().isEmpty()) {
                addTableCell(tbl, multivaluesEntry.getKey());
                addTableCell(tbl, multivaluesEntry.getValue().toString());
            }
        }
    }

    private void addSingleValueFields(PdfPTable tbl, AbstractField abstractField) {
        if (abstractField != null && abstractField.getValue() != null && !abstractField.getValue().isEmpty()) {
            addTableCell(tbl, abstractField.getLabelOrName());
            addTableCell(tbl, abstractField.getValue());
        }
    }

    private void addSingleValueFormField(PdfPTable tbl, FormField formField) {
        if (formField != null && formField.getValue() != null && !formField.getValue().isEmpty()) {
            addTableCell(tbl, formField.getName());
            addTableCell(tbl, formField.getValue());
        }
    }

    private void addTableCell(PdfPTable tbl, String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content));
        cell.disableBorderSide(Rectangle.BOX);
        tbl.addCell(cell);
    }

    private void addIntroduction(FormBean formBean, String introText, Document doc) throws DocumentException {
        if (introText != null && !introText.isEmpty()) {
            doc.add(new Paragraph(introText));
        }
    }

    private void addTitle(FormBean formBean, Document doc) throws DocumentException {
        String formName = formBean.getFormName();
        if (formName != null && !formName.isEmpty()) {
            Font font = new Font(FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLACK);
            Phrase titlePhrase = new Phrase(formBean.getFormName(), font);
            doc.add(titlePhrase);
            doc.add(Chunk.NEWLINE);
        }
    }
}
