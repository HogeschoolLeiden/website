// $Id: MinorExportComponent.java 1496 2014-01-24 14:46:11Z rharing $
package nl.hinttech.hsleiden.pi.components;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;

import nl.hinttech.hsleiden.pi.beans.GroupedResult;
import nl.hinttech.hsleiden.pi.beans.Minor;
import nl.hinttech.hsleiden.pi.util.MinorExporter;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

/**
 * Component that enables the export of {@link Minor} documents to Excel format. The component shows a list of all
 * available minors for a specific year, grouped by cluster. The user can select one or more and export the selected
 * Minors to an Excel document that he can download to his computer.
 * 
 * @author rob
 */
public class MinorExportComponent extends MinorBaseComponent {

    private static final String FILE_NAME = "minor-export.xls";

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        
        // Startyear is provided by a sitemap parameter
        String startYear = getStartYear(request);

        // Get all minor documents for the specified start year...
        List<Minor> minors = getMinorsByStartYear(request, startYear);
        // and group them by cluster
        GroupedResult<Minor> groupedResult = createGroupedResult(request, minors);

        request.setAttribute("groupedResult", groupedResult);
        request.setAttribute("startYear", startYear);
        request.setAttribute("endYear", getEndYear(startYear));        
    }

    @Override
    public void doBeforeServeResource(final HstRequest request, final HstResponse response)
            throws HstComponentException {
        
        // The user selected one or more minors and submitted the form.
        
        // Get the uuids of the minors that where selected by the user
        List<String> exportUuids = Arrays.asList(getPublicRequestParameters(request, "uuid"));
        if (exportUuids != null && exportUuids.size() > 0) {
            
            // Get the minor documents from the repository
            List<Minor> minors = getMinorsByUUID(request, exportUuids);
            // And export them to an excel sheet (using poi library)
            Workbook workbook = exportMinors(request, minors);

            try {
                // Write the workbook to the response
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);

                ServletOutputStream out = response.getOutputStream();
                workbook.write(out);
                out.flush();
                out.close();

            } catch (IOException x) {
                x.printStackTrace();
            }
        }
    }
   

    private Workbook exportMinors(final HstRequest request, final List<Minor> minors) {
        
        MinorExporter exporter = new MinorExporter(this, request);   
        Workbook workbook =  exporter.export(minors);

        return workbook;
    }

    
    private String getEndYear(final String startYear) {
        String result = "";
        if (StringUtils.isNotBlank(startYear)) {
            Long endYear = Long.parseLong(startYear);
            endYear++;
            result = endYear.toString();
        }
        return result;
    }

}
