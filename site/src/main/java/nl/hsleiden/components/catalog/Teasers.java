package nl.hsleiden.components.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.hsleiden.componentsinfo.TeasersInfo;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;
import com.tdclighthouse.prototype.utils.BeanUtils;

public abstract class Teasers extends AjaxEnabledComponent {

    private static final Logger LOG = LoggerFactory.getLogger(Teasers.class);
    
    protected void addItemsToModel(HstRequest request, Map<String, Object> model, TeasersInfo parametersInfo, String message) {
        List<HippoBean> items = new ArrayList<HippoBean>();
        
        addItem(request, parametersInfo.getFirstTeaser(), items);
        addItem(request, parametersInfo.getSecondTeaser(), items);
        
        if (!items.isEmpty()) {
            model.put(Attributes.ITEMS, items);
        } else {
            LOG.debug("No items !! Setting \"" + message + "\" message to request");
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, message);
        }
    }

    private void addItem(HstRequest request, String path, List<HippoBean> items) {
        if(BeanUtils.getBeanViaAbsolutePath(path, request)!=null){
            items.add(BeanUtils.getBeanViaAbsolutePath(path, request));
        }
    }
}
