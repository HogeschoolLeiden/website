package nl.hsleiden.components.base;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.componentsinfo.GenericOverviewPageInfo;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.Constants.Values;
import nl.hsleiden.utils.PaginatorWidget;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;

@ParametersInfo(type = GenericOverviewPageInfo.class)
public class GenericOverviewPage extends BaseHslComponent {

	@Override
	public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
		try {
			setDocumentToRequest(request);

			GenericOverviewPageInfo parametersInfo = getComponentParametersInfo(request);
			HstQuery query = getQuery(request);
			HstQueryResult queryResult = query.execute();
			PaginatorWidget paginator = getPaginator(request, getPageSize(request), queryResult.getTotalSize());
			List<HippoBean> items = getItems(queryResult);

			request.setAttribute(Attributes.ITEMS, items);
			if (parametersInfo.getShowPaginator()) {
				request.setAttribute(Attributes.PAGINATOR, paginator);
			}

		} catch (QueryException e) {
			throw new HstComponentException(e);
		}
	}

	private List<HippoBean> getItems(HstQueryResult queryResult) {
		List<HippoBean> items = new ArrayList<HippoBean>();
		for (HippoBeanIterator hippoBeans = queryResult.getHippoBeans(); hippoBeans.hasNext();) {
			items.add(hippoBeans.nextHippoBean());
		}
		return items;
	}

	private void setDocumentToRequest(HstRequest request) {
		HippoBean contentBean = request.getRequestContext().getContentBean();
		if (contentBean != null) {
			request.setAttribute(Attributes.DOCUMENT, contentBean);
		}
	}

	protected HstQuery getQuery(HstRequest request) throws QueryException {
		GenericOverviewPageInfo parametersInfo = getComponentParametersInfo(request);
		HippoBean scope = getQueryScope(request, parametersInfo);
		HstQuery query = request.getRequestContext().getQueryManager().createQuery(scope, parametersInfo.getShowTypes());

		String sortBy = parametersInfo.getSortBy();
		if (StringUtils.isNotBlank(sortBy)) {
			if (Values.DESCENDING.equals(parametersInfo.getSortOrder())) {
				query.addOrderByDescending(sortBy);
			} else {
				query.addOrderByAscending(sortBy);
			}
		}

		setLimitAndOffset(request, query);
		addFilter(query);
		return query;
	}

	protected void addFilter(HstQuery query) {
	}

	private void setLimitAndOffset(HstRequest request, HstQuery query) {
		int pageSize = getPageSize(request);
		int pageNumber = getPageNumber(request);
		query.setLimit(pageSize);
		query.setOffset((pageNumber - 1) * pageSize);
	}

	protected HippoBean getQueryScope(HstRequest request, GenericOverviewPageInfo parametersInfo) {
		HippoBean scope = request.getRequestContext().getContentBean();
		if (!(scope instanceof HippoFolderBean)) {
			scope = getContentBeanViaParameters(request, parametersInfo);
		}
		return scope;
	}

}
