package nl.hsleiden.components.catalog;

import hslbeans.NewsPage;
import nl.hsleiden.componentsinfo.RelatedItemsInfo;
import nl.hsleiden.componentsinfo.RelatedNewsInfo;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = RelatedNewsInfo.class)
public class RelatedNews extends RelatedItems {
    
    @Override
    protected HstQuery createQuery(HstRequest request, RelatedItemsInfo parametersInfo) throws QueryException {
        
        HstQuery result = null;
        HippoBean scope = BeanUtils.getBeanViaAbsolutePath(parametersInfo.getContentBeanPath(), request);
        if(scope!=null){            
            result = request.getRequestContext().getQueryManager().createQuery(scope, NewsPage.JCR_TYPE);
        }else{
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.nofolder.message");
        }
        return result; 
    }    
}
