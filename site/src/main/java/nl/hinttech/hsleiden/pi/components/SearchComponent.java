package nl.hinttech.hsleiden.pi.components;

import java.util.ArrayList;
import java.util.List;

import nl.hinttech.hsleiden.pi.beans.Asset;
import nl.hinttech.hsleiden.pi.beans.ContentDocument;
import nl.hinttech.hsleiden.pi.beans.InternalLink;
import nl.hinttech.hsleiden.pi.beans.Metadata;
import nl.hinttech.hsleiden.pi.beans.Metadata.Group;
import nl.hinttech.hsleiden.pi.beans.Metadata.Item;
import nl.hinttech.hsleiden.pi.beans.Paginator;
import nl.hinttech.hsleiden.pi.beans.SearchResult;
import nl.hinttech.hsleiden.pi.beans.SearchResultItem;
import nl.hinttech.hsleiden.pi.beans.TextDocument;
import nl.hinttech.hsleiden.pi.util.HSLeiden;
import nl.hinttech.hsleiden.pi.util.SearchUtil;
import nl.hinttech.hsleiden.pi.util.ValueListUtil;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoAsset;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.util.SearchInputParsingUtils;

/**
 * Component that performs a search and renders the search results.
 */
public class SearchComponent extends BaseComponent {

    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        
        // get the paging information
        int currentPage = getCurrentPage(request);
        int pageSize = DEFAULT_PAGE_SIZE;
        
        // get the filter/query information from the request
        String searchText = getSearchText(request);
        String department = getDepartment(request);
        String training = getTraining(request);
        
        // Remember that the search menu is now active
        HSLeiden.rememberActiveMenu(request);
        setBreadcrumb(request);
        
        // make the valuelists for trainings and departments available to the front-end to show the filters.
        request.setAttribute("trainings", ValueListUtil.getValueList(this, request, HSLeiden.VALUELIST_TRAININGS));
        request.setAttribute("departments", ValueListUtil.getValueList(this, request, HSLeiden.VALUELIST_DEPARTMENTS));
        
        // instruct the front-end to select the current filters (if any)
        request.setAttribute("selectedTraining", training);
        request.setAttribute("selectedDepartment", department);
        
        boolean hideFilter = Boolean.parseBoolean(getSetting("hide.filter"));
        request.setAttribute("hideFilter", hideFilter);
        
        if (hasSearchParameters(searchText, training, department)) {
            try {
                // find all documents that match the search text and the filter.
                HstQueryResult result = findDocuments(request, searchText, training, department, currentPage, pageSize);
    
                if (result != null && result.getTotalSize() > 0) {
                    
                    // Create a SearchResult object from the found documents.
                    SearchResult foundDocuments = createSearchResult(request, result);
                    if (!foundDocuments.getItems().isEmpty()) {
                        request.setAttribute("foundDocuments", foundDocuments);
                    }
                } else {
                    request.setAttribute("message", "Er zijn geen documenten gevonden");
                }
                
                // create a Paginator to enable the paging on the front-end.
                Paginator paginator = new Paginator(pageSize, currentPage, result.getTotalSize());
    
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("paginator", paginator);
                request.setAttribute("searchText", searchText);
    
            } catch (QueryException e) {
                log.error("Failed to execute search", e);
                request.setAttribute("message", "Er is een fout opgetreden tijdens het zoeken");
            }
        } else {
            request.setAttribute("message", "Gebruik bovenstaand formulier om een zoekopdracht samen te stellen");
        }
        
    }

    private boolean hasSearchParameters(final String searchText, final String training, final String department) {
       
        return !(StringUtils.isBlank(searchText) && StringUtils.isBlank(training) && StringUtils.isBlank(department));
    }

    private SearchResult createSearchResult(final HstRequest request, final HstQueryResult result) {

        List<SearchResultItem> items = new ArrayList<SearchResultItem>();

        HippoBeanIterator it = result.getHippoBeans();
        while (it.hasNext()) {
            HippoBean document = it.next();

            SearchResultItem item = null;
            if (document instanceof TextDocument) {
                // The found item is a normal document.
                Metadata metadata = ((TextDocument) document).getMetadata(this, request);
                item = new SearchResultItem(document, metadata);

            } else if (document instanceof HippoAsset) {
                // The found document is an asset
                // We also need to find the documents that refer to this asset
                HippoAsset asset = (HippoAsset) document;
                List<TextDocument> referingDocuments = SearchUtil.findReferingDocuments(this, request, asset);
                String assetTitle = getAssetTitle(asset, referingDocuments);
                
                // The metadata that is displayed for this item will
                // be an aggregation of the metadata of all refering documents.
                Metadata metadata = getMetadata(request, referingDocuments);
                item = new SearchResultItem(new Asset(asset, assetTitle), referingDocuments, metadata);
            }
            items.add(item);
        }

        SearchResult searchResult = new SearchResult(items, result);

        return searchResult;
    }

    private Metadata getMetadata(final HstRequest request, final List<TextDocument> referingDocuments) {

        Metadata metadata = new Metadata();
        for (TextDocument document : referingDocuments) {
            Metadata documentData = document.getMetadata(this, request);
            for (Group group : documentData.getGroups()) {
                for (Item item : group.getItems()) {
                    metadata.addItem(group.getTitle(), item.getTitle(), item.getPath());
                }
            }
        }
        return metadata;
    }

    private String getAssetTitle(final HippoAsset asset, final List<TextDocument> referingDocuments) {
        String title = asset.getLocalizedName();
        for (TextDocument document : referingDocuments) {
            if (document instanceof ContentDocument) {
                for (InternalLink link : ((ContentDocument) document).getAttachments()) {
                    if (link.getDocument() != null) {
                        if (link.getDocument().getCanonicalUUID().equals(asset.getCanonicalUUID())) {
                            return link.getTitle();
                        }
                    }
                }
            }
        }
        return title;
    }

    private HstQueryResult findDocuments(final HstRequest request, final String searchText, final String training, final String department, final int currentPage, final int pageSize)
            throws QueryException {

        HstQuery query = createQuery(request, searchText, training, department, currentPage, pageSize);
        if (query != null) {
            return query.execute();
        }
        return null;
    }

    private HstQuery createQuery(final HstRequest request, final String searchText, final String training, final String department, final int currentPage, final int pageSize)
            throws QueryException {

        HippoBean scope = getContentRoot(request);

        final List<HippoBean> extraScopes = new ArrayList<HippoBean>();
        extraScopes.add(getAssetBaseBean(request));

        HstQuery query = request.getRequestContext().getQueryManager().createQuery(scope, HSLeiden.getSearchableClasses());

        query.addScopes(extraScopes);
        query.setLimit(pageSize);
        query.setOffset(pageSize * (currentPage - 1));
        
        String parsedQuery = SearchInputParsingUtils.parse(searchText, true);
        if (parsedQuery != null && !parsedQuery.equals(searchText)) {
            log.debug("Replaced query '{}' with '{}' because it contained invalid chars.", query, parsedQuery);
        }
        
        Filter mainFilter = query.createFilter();
        
        if (StringUtils.isNotBlank(parsedQuery)) {
            
            Filter textFilter = query.createFilter();
            
            Filter titleFilter = query.createFilter();
            titleFilter.addContains(TextDocument.PROPERTY_TITLE, parsedQuery);

            Filter introductionFilter = query.createFilter();
            introductionFilter.addContains(TextDocument.PROPERTY_INTRODUCTION, parsedQuery);
            
            Filter tagsFilter = query.createFilter();
            tagsFilter.addContains(TextDocument.PROPERTY_TAGS, parsedQuery);
            
            Filter contentFilter = query.createFilter();
            contentFilter.addContains("hsleiden:titelTekst/hsleiden:tekst/hippostd:content", parsedQuery);
            
            Filter fullTextFilter = query.createFilter();
            fullTextFilter.addContains(".", parsedQuery);
            
            // to give hippo documents more "weight" (as opposed to PDF assets) we boost
            // the scoring on some document fields by adding them
            // more than one time to the text filter. 
            textFilter.addOrFilter(titleFilter);
            textFilter.addOrFilter(titleFilter);
            textFilter.addOrFilter(titleFilter);
            textFilter.addOrFilter(introductionFilter);
            textFilter.addOrFilter(tagsFilter);
            textFilter.addOrFilter(tagsFilter);
            textFilter.addOrFilter(contentFilter);
            textFilter.addOrFilter(contentFilter);
            textFilter.addOrFilter(fullTextFilter);
            
            mainFilter.addAndFilter(textFilter);
        }
        if (StringUtils.isNotBlank(training)) {
            Filter trainingFilter = query.createFilter();
            trainingFilter.addEqualTo(TextDocument.PROPERTY_TRAINING, training);
            mainFilter.addAndFilter(trainingFilter);
        }
        if (StringUtils.isNotBlank(department)) {
            Filter departmentFilter = query.createFilter();
            departmentFilter.addEqualTo(TextDocument.PROPERTY_DEPARTMENT, department);
            mainFilter.addAndFilter(departmentFilter);
        }
      
        query.setFilter(mainFilter);
        return query;
    }

    private String getSearchText(final HstRequest request) {
        return getPublicRequestParameter(request, "query");
    }

    private int getCurrentPage(final HstRequest request) {
        int currentPage = 1;
        String value = getPublicRequestParameter(request, "page");
        if (StringUtils.isNotBlank(value)) {
            currentPage = Integer.parseInt(value);
            if (currentPage <= 0) {
                currentPage = 1;
            }
        }
        return currentPage;
    }
}
