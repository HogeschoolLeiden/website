package nl.hsleiden.componentsinfo;

import nl.hsleiden.utils.Constants.FieldName;
import nl.hsleiden.utils.Constants.Values;

import org.hippoecm.hst.core.parameters.Parameter;

/**
 * @author Ebrahim Aharpour
 *
 */
public interface SortedInfo {

	@Parameter(name = "sortOrder", defaultValue = Values.DESCENDING)
	public String getSortOrder();

	@Parameter(name = "sortBy", defaultValue = FieldName.TDC_RELEASE_DATE)
	public String getSortBy();

}
