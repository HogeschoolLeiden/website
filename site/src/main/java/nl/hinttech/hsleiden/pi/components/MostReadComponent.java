package nl.hinttech.hsleiden.pi.components;

import java.util.ArrayList;
import java.util.List;

import nl.hinttech.hsleiden.pi.beans.InternalLink;
import nl.hinttech.hsleiden.pi.beans.LinkItem;
import nl.hinttech.hsleiden.pi.beans.MetadataDocument;
import nl.hinttech.hsleiden.pi.beans.MostReadBlock;
import nl.hinttech.hsleiden.pi.counters.PageViewCounter;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.manager.ObjectBeanManager;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

/**
 * Component for rendering a {@link MostReadBlock} document.
 *
 */
public class MostReadComponent extends BaseComponent {

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {

        super.doBeforeRender(request, response);

        String type = getType(request);
        List<LinkItem> links = new ArrayList<LinkItem>();
        
        // add all user configured links to the collection
        addUserConfiguredLinkItems(request, links, type);

        // fill up the collection with dynamic links to 
        // documents that are most read (we use our PageViewCounter for this)
        if (links.size() < 5) {
            addDynamicLinkItems(request, links, type);
        }
        
        request.setAttribute("mainTitle", getMainTitle());
        request.setAttribute("overviewPath", getOverviewPath());
        request.setAttribute("overviewLinkText", getOverviewLinkText());

        request.setAttribute("links", links);
    }

    private String getOverviewLinkText() {
        String value = getComponentParameter("buttonText");
        if (StringUtils.isBlank(value)) {
            value = "Alle informatie";
        }
        return value;
    }

    private String getOverviewPath() {
        String value = getComponentParameter("overviewPath");
        if (StringUtils.isBlank(value)) {
            value = "studenten";
        }
        return value;
    }

    private String getMainTitle() {
        String value = getComponentParameter("mainTitle");
        if (StringUtils.isBlank(value)) {
            value = "Informatie";
        }
        return value;
    }

    private void addDynamicLinkItems(final HstRequest request, final List<LinkItem> links, final String type) {
        
        ObjectBeanManager objectBeanManager = request.getRequestContext().getObjectBeanManager();
        PageViewCounter pageViewCounter = PageViewCounter.getInstance(request);
        
        List<MetadataDocument> items = pageViewCounter.getMostReadDocumentsForType(type, 10, 30, objectBeanManager);
        for (MetadataDocument item : items) {
            if (!links.contains(item)) {
                LinkItem linkItem = new LinkItem(item, item.getTitle());
                links.add(linkItem);
            }
            if (links.size() == 5) {
                return;
            }
        }
    }

    private void addUserConfiguredLinkItems(final HstRequest request, final List<LinkItem> links, final String type) {

        try {
            MostReadBlock mostReadBlock = getMostReadBlock(request, type);

            if (mostReadBlock != null) {
                for (InternalLink link : mostReadBlock.getLinks()) {
                    LinkItem linkItem = new LinkItem(link.getDocument(), link.getTitle());
                    links.add(linkItem);
                }
            }
        } catch (QueryException e) {
            log.error("Failed to get MostReadBlock for type: " + type, e);
        }
    }

    @SuppressWarnings("unchecked")
    private MostReadBlock getMostReadBlock(final HstRequest request, final String type) throws QueryException {

        HippoBean scope = getContentRoot(request).getBean("admin");
        HstQuery query = request.getRequestContext().getQueryManager().createQuery(scope, MostReadBlock.class);

        Filter filter = query.createFilter();
        filter.addEqualTo(MostReadBlock.PROPERTY_ABOUT, type);
        query.setFilter(filter);

        HstQueryResult result = query.execute();

        HippoBeanIterator it = result.getHippoBeans();
        while (it.hasNext()) {
            return (MostReadBlock) it.next();
        }
        return null;
    }

    private String getType(final HstRequest request) {
        String type = getResolvedSiteMapParameter(request, "type");
        if (StringUtils.isBlank(type)) {
            type = getComponentParameter("type");
        }

        return type;
    }

}
