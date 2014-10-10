package nl.hsleiden.components.catalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.FacebookPostsMixin;
import nl.hsleiden.componentsinfo.FacebookPostsInfo;
import nl.hsleiden.utils.Constants.Attributes;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = FacebookPostsInfo.class)
public class FacebookPosts extends AjaxEnabledComponent {

    private static final String ENTRY_POINT = "<div id=\"fb-root\"></div> "
            + "<script>"
            + "(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; "
            + "if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; "
            + "js.src = \"//connect.facebook.net/en_US/all.js#xfbml=1\"; "
            + "fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'facebook-jssdk'));"
            + "</script>";
    
    private static final Logger LOG = LoggerFactory.getLogger(FacebookPosts.class);

    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            FacebookPostsInfo parametersInfo = getConfiguration(request);    
            return populateModel(request, parametersInfo);
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    protected Map<String, Object> populateModel(HstRequest request, FacebookPostsInfo parametersInfo) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("info", parametersInfo);
        addItemsToModel(request, model, parametersInfo);
        return model;
    }
    
    protected void addItemsToModel(HstRequest request, Map<String, Object> model, FacebookPostsInfo parametersInfo) {
        List<String> items = new ArrayList<String>();
        
        addItem(request, parametersInfo.getFistPost(), items);
        addItem(request, parametersInfo.getSecondPost(), items);
        addItem(request, parametersInfo.getThirdPost(), items);
        
        if (!items.isEmpty()) {
            model.put("entrypoint", ENTRY_POINT);
            model.put(Attributes.ITEMS, items);
        } else {
            LOG.debug("No items !! Setting webmaster.no.posts.message to request");
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.no.posts.message");
        }
    }

    private void addItem(HstRequest request, String post, List<String> items) {
        String result = "";
        if(post!=null && post.startsWith("<div id=\"fb-root\">") && post.contains("</script>")){
            result = post.substring(post.indexOf("</script>")+10);
            items.add(result);
        }
    }
   
    private FacebookPostsInfo getConfiguration(HstRequest request) throws RepositoryException {
        FacebookPostsInfo paramInfo = this.<FacebookPostsInfo> getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && request.getRequestContext().getContentBean() != null 
                && paramInfo.getUseMixin()) {
            HippoBean proxy = BeanUtils.getMixinProxy(request.getRequestContext().getContentBean());
            if (proxy instanceof FacebookPostsMixin) {
                paramInfo = ((FacebookPostsMixin) proxy).getFacebookPostsCompoundMixin();
            }
        }
        return paramInfo;
    }
}
