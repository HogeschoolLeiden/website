package nl.hinttech.hsleiden.pi.components;

import java.util.List;

import nl.hinttech.hsleiden.pi.beans.BrowseResult;
import nl.hinttech.hsleiden.pi.beans.ContentDocument;
import nl.hinttech.hsleiden.pi.beans.TextDocument;
import nl.hinttech.hsleiden.pi.util.HSLeiden;
import nl.hinttech.hsleiden.pi.util.ValueListUtil;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryManager;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.forge.selection.hst.contentbean.ValueList;

/**
 * This component is responsible for retrieving all documents for a specified type (student, medewerker or service). The
 * documents are grouped based on the one of the *lijst properties of the document. The result can be limited by using
 * two extra filters (afdeling and opleiding).
 * 
 * @author rob
 */
public class BrowseComponent extends BaseComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {

        super.doBeforeRender(request, response);

        // get the filters for our query
        String type = getType(request);
        String department = getDepartment(request);
        String training = getTraining(request);

        try {
            // Find all documents that match the filters
            HstQueryResult result = findDocuments(request, type, department, training);

            // Package the found documents in a category grouped BrowseResult
            // and sort the grouped documents.
            BrowseResult browseResult = createBrowseResult(request, result, type);
            browseResult.sort();
            request.setAttribute("browseResult", browseResult);
            
            // make the valuelists for trainings and departments available to the front-end to show the filters.
            request.setAttribute("trainings", ValueListUtil.getValueList(this, request, HSLeiden.VALUELIST_TRAININGS));
            request.setAttribute("departments", ValueListUtil.getValueList(this, request, HSLeiden.VALUELIST_DEPARTMENTS));
            
            // instruct the front-end to select the current filters (if any)
            request.setAttribute("selectedTraining", training);
            request.setAttribute("selectedDepartment", department);
            
            boolean hideFilter = Boolean.parseBoolean(getSetting("hide.filter"));
            request.setAttribute("hideFilter", hideFilter);
            
        } catch (QueryException e) {
            log.error("Failed to find documents", e);
        }
        
        setBreadcrumb(request);
        
        HSLeiden.rememberActiveMenu(request);
    }

    private BrowseResult createBrowseResult(HstRequest request, HstQueryResult result, String type) {
         
        BrowseResult browseResult = new BrowseResult();
        
        ValueList valueList = getCategoryValueList(request, type);

        HippoBeanIterator it = result.getHippoBeans();
        while (it.hasNext()) {
            TextDocument document = (TextDocument) it.nextHippoBean();
            if (document != null) {
                List<String> categories = document.getCategoriesForType(type);
                for (String category : categories) {
                    String categoryTitle = ValueListUtil.getLabelForKey(category, valueList);
                    browseResult.addDocument(document, category, categoryTitle);
                }
            }
        }
        return browseResult;
    }

    private ValueList getCategoryValueList(HstRequest request, String type) {
        String valueListName = TextDocument.getCategoryValueListNameForType(type);
        return  ValueListUtil.getValueList(this, request, valueListName);
    }

    private HstQueryResult findDocuments(HstRequest request, String type, String department, String training)
            throws QueryException {

        HstQuery query = createQuery(request, type, department, training);
        HstQueryResult result = query.execute();

        return result;
    }

    private HstQuery createQuery(HstRequest request, String type, String department, String training)
            throws QueryException {

        HstQuery query = null;
        
        HstQueryManager queryManager = request.getRequestContext().getQueryManager();
        query = queryManager.createQuery(getContentRoot(request), HSLeiden.getBrowsableClasses());

        Filter filter = query.createFilter();
        String typeProperty = ContentDocument.getTypePropertyForType(type);

        filter.addEqualTo(typeProperty, Boolean.TRUE);
        if (StringUtils.isNotBlank(department)) {
            filter.addEqualTo(TextDocument.PROPERTY_DEPARTMENT, department);
        }
        if (StringUtils.isNotBlank(training)) {
            filter.addEqualTo(TextDocument.PROPERTY_TRAINING, training);
        }

        query.setFilter(filter);

        return query;
    }

    private String getType(HstRequest request) {
        return getResolvedSiteMapParameter(request, "type");
    }
}
