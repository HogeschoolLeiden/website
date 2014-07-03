package nl.hsleiden.components.catalog;

import hslbeans.NewsPage;
import nl.hsleiden.componentsinfo.RelatedItemsInfo;
import nl.hsleiden.componentsinfo.RelatedNewsInfo;
import nl.hsleiden.utils.HslUtils;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.parameters.ParametersInfo;

@ParametersInfo(type = RelatedNewsInfo.class)
public class RelatedNews extends RelatedItems {
    
    @Override
    protected HstQuery createQuery(HstRequest request, RelatedItemsInfo parametersInfo) throws QueryException {
        
        HstQuery result = null;
        HippoBean scope = HslUtils.getSelectedBean(request, parametersInfo.getContentBeanPath());
        if(scope!=null){            
            result = request.getRequestContext().getQueryManager().createQuery(scope, NewsPage.JCR_TYPE);
        }
        return result; 
    }

    
}
