package nl.hsleiden.components;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.FilterException;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.components.GenericOverviewPage;
import com.tdclighthouse.prototype.componentsinfo.GenericOverviewPageInfo;

@ParametersInfo(type = GenericOverviewPageInfo.class)
public class GenericOverview extends GenericOverviewPage {

    @Override
    protected void addFilter(HstQuery query) {
        Filter globalFilter = query.createFilter();
        try {
            globalFilter.addEqualTo("hsl:hideFromSearch", false);
        } catch (FilterException e) {
            throw new HstComponentException(e.getMessage(), e); 
        }
        query.setFilter(globalFilter);
    }
}
