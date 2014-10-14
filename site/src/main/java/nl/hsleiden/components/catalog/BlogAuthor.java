package nl.hsleiden.components.catalog;

import java.util.HashMap;
import java.util.Map;

import nl.hsleiden.beans.Author;
import nl.hsleiden.beans.Blogpost;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;

public class BlogAuthor extends AjaxEnabledComponent {

    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        HippoBean contentBean = request.getRequestContext().getContentBean();
        if (contentBean instanceof Blogpost) {
            Author author = ((Blogpost) contentBean).getAuthors().get(0);
            model.put("author", author);
        }else{
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.authorinfo.only.blogs");
        }
        return model;

    }
}
