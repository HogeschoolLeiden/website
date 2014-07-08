package nl.hsleiden.components.catalog;

import hslbeans.EventPage;

import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.RelatedEventsMixin;
import nl.hsleiden.componentsinfo.RelatedEventsInfo;
import nl.hsleiden.componentsinfo.RelatedItemsInfo;
import nl.hsleiden.utils.Constants.WidgetConstants;
import nl.hsleiden.utils.HslUtils;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = RelatedEventsInfo.class)
public class RelatedEvents extends RelatedItems {
    
    @Override
    protected HstQuery createQuery(HstRequest request, RelatedItemsInfo parametersInfo) throws QueryException {
        
        HstQuery result = null;
        HippoBean scope = HslUtils.getSelectedBean(request, parametersInfo.getContentBeanPath());
        if(scope!=null){            
            result = request.getRequestContext().getQueryManager().createQuery(scope, EventPage.JCR_TYPE);
        }else{
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.nofolder.message");
        }
        return result; 
    }
    
    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            RelatedItemsInfo parametersInfo = this.<RelatedEventsInfo> getComponentParametersInfo(request);
            if (parametersInfo.getUseMixin()) {
                HippoBean proxy = BeanUtils.getMixinProxy(request.getRequestContext().getContentBean());
                if (proxy instanceof RelatedEventsMixin) {
                    parametersInfo = ((RelatedEventsMixin) proxy).getRelatedEventsCompoundMixin();
                }
            }
            return populateModel(request, parametersInfo);
        } catch (RepositoryException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }
}
