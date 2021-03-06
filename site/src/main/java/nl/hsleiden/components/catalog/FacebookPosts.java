package nl.hsleiden.components.catalog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.FacebookConfig;
import nl.hsleiden.beans.mixin.FacebookPostsMixin;
import nl.hsleiden.componentsinfo.FacebookPostsInfo;
import nl.hsleiden.utils.HslUtils;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.apache.commons.lang3.StringUtils;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.types.Post;
import com.tdclighthouse.prototype.components.AjaxEnabledComponent;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = FacebookPostsInfo.class)
public class FacebookPosts extends AjaxEnabledComponent {

    private static final Logger LOG = LoggerFactory.getLogger(FacebookPosts.class);
    private final FacebookConfig config = HstServices.getComponentManager().getComponent(FacebookConfig.class);

    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            Map<String, Object> model = new HashMap<String, Object>();
            FacebookPostsInfo info = getConfiguration(request);
            if (StringUtils.isNotBlank(info.getAccount())) {
                AccessToken token = getOauthToken();

                if (token != null) {
                    List<Post> posts = getPosts(token.getAccessToken(), info, request);
                    List<String> postIDs = getPostIDs(posts);
                    model.put("postIDs", postIDs);
                } else {
                    LOG.error("failed to retrieve an Oauth token.");
                }
            } else {
                request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.nofbaccount.provided");
                LOG.debug("no facebook account has been configured.");
            }
            model.put("info", info);
            return model;
        } catch (RepositoryException | IOException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private List<String> getPostIDs(List<Post> posts) {
        List<String> result = new ArrayList<String>();
        if(posts!=null){            
            for (Post post : posts) {
                result.add(post.getId().substring(16));
            }
        }
        return result;
    }

    private List<Post> getPosts(String token, FacebookPostsInfo info, HstRequest request) throws IOException {

        List<Post> result = null;
        if(info.getPostsLimit()>0){            
            FacebookClient facebookClient = new DefaultFacebookClient(token);
            Connection<Post> fetchConnection = facebookClient.fetchConnection(info.getAccount() + "/posts", Post.class,
                    Parameter.with("limit", info.getPostsLimit()));
            result = fetchConnection.getData();
        }else{
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.0.posts.configured"); 
        }
        
        return result;
    }

    private AccessToken getOauthToken() {
        return new DefaultFacebookClient().obtainAppAccessToken(config.getClientId(), config.getClientSecret());
    }

    private FacebookPostsInfo getConfiguration(HstRequest request) throws RepositoryException {
        FacebookPostsInfo paramInfo = this.<FacebookPostsInfo> getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && request.getRequestContext().getContentBean() != null
                && paramInfo.getUseMixin()) {

            HippoBean contentBean = HslUtils.getBean(request);
            HippoBean proxy = BeanUtils.getMixinProxy(contentBean);
            if (proxy instanceof FacebookPostsMixin) {
                paramInfo = ((FacebookPostsMixin) proxy).getFacebookPostsCompoundMixin();
            }
        }
        return paramInfo;
    }

}
