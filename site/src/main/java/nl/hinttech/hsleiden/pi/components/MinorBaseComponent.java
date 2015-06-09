// $Id: MinorBaseComponent.java 1495 2014-01-24 12:05:56Z rharing $
package nl.hinttech.hsleiden.pi.components;

import java.util.ArrayList;
import java.util.List;

import nl.hinttech.hsleiden.pi.beans.GroupedResult;
import nl.hinttech.hsleiden.pi.beans.Minor;
import nl.hinttech.hsleiden.pi.util.ValueListUtil;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstRequest;
import org.onehippo.forge.selection.hst.contentbean.ValueList;

/**
 * The abstract super class of all Minor components. Contains shared logic.
 *
 * @author rob
 */
public abstract class MinorBaseComponent extends BaseComponent {

    public String getStartYear(final HstRequest request) {
        // The start year is derived from the name of the year folder (via a a
        // sitemap parameter)
        return getResolvedSiteMapParameter(request, "startyear");
    }

    protected GroupedResult<Minor> createGroupedResult(final HstRequest request, final List<Minor> minors) {

        GroupedResult<Minor> groupedResult = new GroupedResult<Minor>();
        ValueList clusters = ValueListUtil.getValueList(this, request, "clusters");

        for (Minor minor : minors) {
            String clusterKey = minor.getClusterKey();
            String clusterName = ValueListUtil.getLabelForKey(clusterKey, clusters);
            groupedResult.addDocument(minor, clusterName);
        }

        return groupedResult;
    }

    @SuppressWarnings("unchecked")
    protected List<Minor> getMinorsByStartYear(final HstRequest request, final String startYear) {

        try {
            HippoBean contentRoot = getContentRoot(request);
            HippoBean minorFolder = contentRoot.getBean("minoren");
            HstQuery query = request.getRequestContext().getQueryManager().createQuery(minorFolder, Minor.class);

            Filter filter = query.createFilter();
            filter.addEqualTo(Minor.PROPERTY_START_YEAR, startYear);

            query.setFilter(filter);

            query.addOrderByAscending(Minor.PROPERTY_CLUSTER);
            query.addOrderByAscending(Minor.PROPERTY_TITLE);

            return executeQuery(query);

        } catch (QueryException x) {
            log.error("Failed to get Minors", x);
        }

        return null;
    }

    private List<Minor> executeQuery(final HstQuery query) throws QueryException {

        List<Minor> minors = new ArrayList<Minor>();

        HstQueryResult result = query.execute();
        HippoBeanIterator it = result.getHippoBeans();
        while (it.hasNext()) {
            Minor minor = (Minor) it.next();
            if (minor != null) {
                minors.add(minor);
            }
        }

        return minors;
    }

    @SuppressWarnings("unchecked")
    protected List<Minor> getMinorsByUUID(final HstRequest request, final List<String> uuids) {

        List<Minor> minors = new ArrayList<Minor>();

        try {
            HippoBean contentRoot = getContentRoot(request);
            HippoBean minorFolder = contentRoot.getBean("minoren");
            HstQuery query = request.getRequestContext().getQueryManager().createQuery(minorFolder, Minor.class);

            Filter mainFilter = query.createFilter();

            for (String uuid : uuids) {
                Filter uuidFilter = query.createFilter();
                uuidFilter.addEqualTo("jcr:uuid", uuid);
                mainFilter.addOrFilter(uuidFilter);
            }

            query.setFilter(mainFilter);

            minors = executeQuery(query);

        } catch (QueryException x) {
            log.error("Failed to get Minors", x);
        }

        return minors;
    }

}
