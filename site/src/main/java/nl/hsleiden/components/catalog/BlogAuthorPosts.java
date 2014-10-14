package nl.hsleiden.components.catalog;

import nl.hsleiden.beans.Blogpost;
import nl.hsleiden.componentsinfo.BlogAuthorPostsInfo;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsBlogAuthorPostsComponent;

@ParametersInfo(type = BlogAuthorPostsInfo.class)
public class BlogAuthorPosts extends EssentialsBlogAuthorPostsComponent{

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        HippoBean contentBean = request.getRequestContext().getContentBean();
        if(contentBean instanceof Blogpost){
            super.doBeforeRender(request, response);
        }else{
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.authorblog.only.blogs");
        }
    }
    
}
