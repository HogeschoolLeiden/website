package nl.hsleiden.componentsinfo;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.parameters.Parameter;

/**
 * @author Ebrahim Aharpour
 *
 */
public interface GenericOverviewPageInfo extends PaginatedInfo, ContentBeanPathInfo, SortedInfo {

	@Parameter(name = "showTypes")
	public Class<? extends HippoBean>[] getShowTypes();

}
