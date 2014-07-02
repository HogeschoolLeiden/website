package nl.hsleiden.components.catalog;

import hslbeans.EventPage;
import nl.hsleiden.componentsinfo.RelatedItemsInfo;
import nl.hsleiden.utils.HslUtils;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.parameters.ParametersInfo;

@ParametersInfo(type = RelatedItemsInfo.class)
public class RelatedEvents extends RelatedItems {
    
    @Override
    protected HstQuery createQuery(HstRequest request, RelatedItemsInfo parametersInfo) throws QueryException {
        
        HstQuery result = null;
        HippoBean scope = HslUtils.getSelectedBean(request, parametersInfo.getContentBeanPath());
        if(scope!=null){            
            result = request.getRequestContext().getQueryManager().createQuery(scope, EventPage.JCR_TYPE);
        }
        return result; 
    }

    
}
