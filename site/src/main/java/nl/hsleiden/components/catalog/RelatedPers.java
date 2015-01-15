package nl.hsleiden.components.catalog;

import hslbeans.PersPage;
import nl.hsleiden.beans.mixin.RelatedPersMixin;
import nl.hsleiden.componentsinfo.RelatedItemsInfo;
import nl.hsleiden.componentsinfo.RelatedPersInfo;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.parameters.ParametersInfo;

import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = RelatedPersInfo.class)
public class RelatedPers extends RelatedItems {

    @Override
    protected HstQuery createQuery(HstRequest request, RelatedItemsInfo parametersInfo) throws QueryException {

        HstQuery result = null;
        HippoBean scope = BeanUtils.getBeanViaAbsolutePath(parametersInfo.getContentBeanPath());
        if (scope != null) {
            result = request.getRequestContext().getQueryManager().createQuery(scope, PersPage.JCR_TYPE);
        } else {
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.nofolder.message");
        }
        return result;
    }

    protected RelatedItemsInfo getParametersFromMixin(RelatedItemsInfo parametersInfo, HippoBean proxy) {
        RelatedItemsInfo result = parametersInfo;
        if (proxy instanceof RelatedPersMixin) {
            result = ((RelatedPersMixin) proxy).getRelatedPersCompoundMixin();
        }
        return result;
    }
}
