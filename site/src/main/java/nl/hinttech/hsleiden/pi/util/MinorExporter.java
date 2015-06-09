// $Id: MinorExporter.java 1500 2014-01-27 07:36:45Z rharing $
package nl.hinttech.hsleiden.pi.util;

import java.util.ArrayList;
import java.util.List;

import nl.hinttech.hsleiden.pi.beans.Accessibility;
import nl.hinttech.hsleiden.pi.beans.ContactPerson;
import nl.hinttech.hsleiden.pi.beans.Minor;
import nl.hinttech.hsleiden.pi.beans.Roster;
import nl.hinttech.hsleiden.pi.beans.RosterDay;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstRequest;
import org.onehippo.forge.selection.hst.contentbean.ValueList;
import org.onehippo.forge.selection.hst.contentbean.ValueListItem;

/**
 * This class is responsible for exporting one or more {@link Minor} documents to an excel workbook, using the apache
 * poi library.
 * 
 * @author rob
 */
public class MinorExporter {

    private BaseHstComponent component;
    private HstRequest request;

    public MinorExporter(final BaseHstComponent component, final HstRequest request) {
        this.component = component;
        this.request = request;
    }

    /**
     * Exports a single {@link Minor} document.
     */
    public Workbook export(final Minor minor) {
        List<Minor> minors = new ArrayList<Minor>();
        minors.add(minor);
        return export(minors);
    }

    /**
     * Exports a List of {@link Minor} documents.
     */
    public Workbook export(final List<Minor> minors) {

        // Create a new workbook and sheet
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Minoren");

        // export all minors to the same sheet.
        int column = 1;
        for (Minor minor : minors) {
            export(minor, sheet, column);
            column++;
        }
        // all data is now inserted... set the column widths to a sensible default
        setColumnWidth(sheet, 50);

        return workbook;
    }

    private void setColumnWidth(final Sheet sheet, final int width) {

        Row row = sheet.getRow(0);
        for (int col = 0; col < row.getPhysicalNumberOfCells(); col++) {
            sheet.setColumnWidth(col, calculateWidth(width));
        }
    }

    public int calculateWidth(final int width) {
        if (width > 254)
            return 65280; // Maximum allowed column width.
        if (width > 1) {
            int floor = (int) (Math.floor(((double) width) / 5));
            int factor = (30 * floor);
            int value = 450 + factor + ((width - 1) * 250);
            return value;
        } else
            return 450; // default to column size 1 if zero, one or negative number is passed.
    }

    private void export(final Minor minor, final Sheet sheet, final int columnNr) {

        // Export all data from the minor to the sheet

        int rowNr = 0;
        setTitle(minor.getTitle(), "Naam", sheet, rowNr++, columnNr);
        setLong(minor.getStartYear(), "Startjaar", sheet, rowNr++, columnNr);
        setValueListValue(minor.getClusterKey(), "Cluster", sheet, rowNr++, columnNr, "clusters");
        setStringValue(minor.getEducationalUnit(), "Penvoerende opleiding", sheet, rowNr++, columnNr);
        setStringValue(minor.getECTs(), "Aantal ECT's", sheet, rowNr++, columnNr);
        setBooleanValue(minor.getIsAvailableOnKom(), "Beschikbaar op KOM", sheet, rowNr++, columnNr);
        setStringValue(minor.getKeywords(), "Trefwoorden", sheet, rowNr++, columnNr);
        setValueListValues(minor.getEducationTypes(), "Type onderwijs", sheet, rowNr++, columnNr, "onderwijstypes");
        setStringValue(minor.getEducationTypeOther(), "Anders nl:", sheet, rowNr++, columnNr);
        rowNr = setAccessibility(minor.getAccessibilityInternal(), "Intern HL toegankelijk voor", sheet, rowNr,
                columnNr);
        rowNr = setAccessibility(minor.getAccessibilityKOM(), "KOM toegankelijk voor", sheet, rowNr, columnNr);
        setStringValue(minor.getModuleCode(), "Osiris code", sheet, rowNr++, columnNr);
        setStringValue(minor.getClassCodesInternalEducation(), "Klascodes interne HL opleiding", sheet, rowNr++,
                columnNr);
        setLong(minor.getMinNrOfStudents(), "Minimaal aantal studenten", sheet, rowNr++, columnNr);
        setLong(minor.getMaxNrOfStudents(), "Maximaal aantal studenten", sheet, rowNr++, columnNr);
        setLong(minor.getMaxNrOfStudentsOwnEducation(), "Maximaal aantal studenten eigen opleiding", sheet,
                rowNr++, columnNr);
        setLong(minor.getMaxNrOfStudentsOtherEducation(), "Maximaal aantal studenten andere opleidingingen", sheet,
                rowNr++, columnNr);
        setLong(minor.getMaxNrOfStudentsKom(), "Maximaal aantal studenten KOM", sheet, rowNr++, columnNr);
        setValueListValues(minor.getLanguages(), "Aangeboden in", sheet, rowNr++, columnNr, "talen");
        rowNr = setContactPerson(minor.getContactPerson(), sheet, rowNr, columnNr);
        setStringValue(minor.getPeriods(), "Ondewerwijsperiode(s)", sheet, rowNr++, columnNr);
        setStringValue(minor.getMinorType(), "Minor type", sheet, rowNr++, columnNr);
        setStringValue(minor.getIntroduction(), "Omschijvinng", sheet, rowNr++, columnNr);
        setStringValue(minor.getGoals(), "Doelen", sheet, rowNr++, columnNr);
        setStringValue(minor.getExamination(), "Toetsing", sheet, rowNr++, columnNr);
        setStringValue(minor.getAssesmentMethod(), "Wijze van beoordeling", sheet, rowNr++, columnNr);
        setStringValue(minor.getLiterature(), "Literatuur", sheet, rowNr++, columnNr);
        setStringValue(minor.getTeachers(), "Onderwijzers", sheet, rowNr++, columnNr);
        setStringValue(minor.getInvolvedEducations(), "Betrokken opleidingen", sheet, rowNr++, columnNr);
        setRoster(minor.getRoster(), "Rooster", sheet, rowNr++, columnNr);
    }

    private void setRoster(final Roster roster, final String string, final Sheet sheet, final int rowNr, final int columnNr) {
        
        int row = rowNr;
        ValueList rosterDays = ValueListUtil.getValueList(component, request, "lesdagen");
        ValueList dayParts = ValueListUtil.getValueList(component, request, "dagdelen");
        
        StringBuilder value = new StringBuilder();
        if (roster != null) {
            for (ValueListItem day : rosterDays.getItems()) {
                value.append(day.getLabel()).append(": ");
                RosterDay rosterDay = roster.getDay(day.getKey());
                
                if (rosterDay != null) {
                    boolean isFirst = true;
                    for (String dayPart : rosterDay.getDayParts()) {
                        String dayLabel = ValueListUtil.getLabelForKey(dayPart, dayParts);
                        
                        if (!isFirst) {
                            value.append(", ");
                        }   
                        value.append(dayLabel);
                        isFirst = false;
                    }
                }
                value.append("\n");
            }
            setStringValue(value.toString(), "Rooster ", sheet, rowNr, columnNr);
        } else {
            setStringValue("", "Rooster ", sheet, rowNr, columnNr);
        }
    }

    private int setContactPerson(final ContactPerson contactPerson, final Sheet sheet, final int rowNr,
            final int columnNr) {
        int row = rowNr;
        if (contactPerson != null) {
            setStringValue(contactPerson.getName(), "Contactpersoon naam", sheet, row++, columnNr);
            setStringValue(contactPerson.getEmailAddress(), "Contactpersoon email", sheet, row++, columnNr);
            setStringValue(contactPerson.getTelephoneNumber(), "Contactpersoon telefoon", sheet, row++, columnNr);
        } else {
            setStringValue("", "Contactpersoon naam", sheet, row++, columnNr);
            setStringValue("", "Contactpersoon email", sheet, row++, columnNr);
            setStringValue("", "Contactpersoon telefoon", sheet, row++, columnNr);
        }
        return row++;
    }

    private int setAccessibility(final Accessibility accessibility, final String title, final Sheet sheet,
            final int rowNr, final int columnNr) {
        int row = rowNr;
        if (accessibility != null) {
            setStringValue(accessibility.getEducations(), title + " (opleidingen)", sheet, row++, columnNr);
            setValueListValues(accessibility.getEducationTypes(), title + " (opleiding type)", sheet, row++,
                    columnNr, "opleidingtypes");
            setStringValue(accessibility.getRequirements(), title + " (fase opleiding)", sheet, row++, columnNr);
        } else {
            setStringValue("", title + " (Opleidingen)", sheet, row++, columnNr);
            setStringValue("", title + " (opleiding type)", sheet, row++, columnNr);
            setStringValue("", title + " (fase opleiding)", sheet, row++, columnNr);
        }
        return row++;
    }

    private void setValueListValues(final List<String> values, final String rowTitle, final Sheet sheet,
            final int rowNr, final int columnNr, final String valueListName) {
        ValueList valueList = ValueListUtil.getValueList(component, request, valueListName);
        StringBuilder cellValue = new StringBuilder();
        for (String value : values) {
            String label = ValueListUtil.getLabelForKey(value, valueList);
            cellValue.append(label).append("\n");
        }
        setStringValue(cellValue.toString(), rowTitle, sheet, rowNr, columnNr);
    }

    private void setBooleanValue(final boolean value, final String rowTitle, final Sheet sheet, final int rowNr,
            final int columnNr) {
        createCell(rowTitle, sheet, rowNr, columnNr).setCellValue(value ? "Ja" : "Nee");
    }

    private void setValueListValue(final String value, final String rowTitle, final Sheet sheet, final int rowNr,
            final int columnNr, final String valueListName) {
        ValueList valueList = ValueListUtil.getValueList(component, request, valueListName);
        String label = ValueListUtil.getLabelForKey(value, valueList);
        setStringValue(label, rowTitle, sheet, rowNr, columnNr);

    }

    private void setStringValue(final String value, final String rowTitle, final Sheet sheet, final int rowNr,
            final int columnNr) {
        createCell(rowTitle, sheet, rowNr, columnNr).setCellValue(value);
    }

    private void setLong(final long value, final String rowTitle, final Sheet sheet, final int rowNr,
            final int columnNr) {
        createCell(rowTitle, sheet, rowNr, columnNr).setCellValue(value);
    }

    private void setTitle(final String value, final String rowTitle, final Sheet sheet, final int rowNr,
            final int columnNr) {
        Cell cell = createCell(rowTitle, sheet, rowNr, columnNr);
        setFontBold(cell);
        cell.setCellValue(value);
    }

    private Cell createCell(final String rowTitle, final Sheet sheet, final int rowNr, final int columnNr) {

        // First create a new row, if it doesn't exist yet
        Row row = sheet.getRow(rowNr);
        if (row == null) {
            row = sheet.createRow(rowNr);
            row.setHeight((short) 0);
            // a new row means that we also have to set the title column to the far left of that row.
            // this title column is in bold
            Cell titleCell = row.getCell(0);
            if (titleCell == null) {
                titleCell = row.createCell(0);
                setFontBold(titleCell);
                titleCell.getCellStyle().setVerticalAlignment(CellStyle.VERTICAL_TOP);
                titleCell.setCellValue(rowTitle);
            }
        }

        // Second, create a new cell
        Cell cell = row.getCell(columnNr);
        if (cell == null) {
            cell = row.createCell(columnNr);
        }
        CellStyle style = sheet.getWorkbook().createCellStyle();
        style.setWrapText(true);
        style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        cell.setCellStyle(style);
        return cell;
    }

    private void setFontBold(final Cell cell) {
        CellStyle style = cell.getCellStyle();
        Font font = cell.getSheet().getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

    }
}
