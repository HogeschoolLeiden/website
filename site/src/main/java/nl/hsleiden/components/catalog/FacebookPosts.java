package nl.hsleiden.components.catalog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.FacebookConfig;
import nl.hsleiden.beans.mixin.FacebookPostsMixin;
import nl.hsleiden.componentsinfo.FacebookPostsInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = FacebookPostsInfo.class)
public class FacebookPosts extends AjaxEnabledComponent {

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(FacebookPosts.class);
    private final FacebookConfig config = HstServices.getComponentManager().getComponent(FacebookConfig.class);

    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        try {
            Map<String, Object> model = new HashMap<String, Object>();
            FacebookPostsInfo info = getConfiguration(request);
            if (StringUtils.isNotBlank(info.getAccount())) {
                String token = getOauthToken();
                if (StringUtils.isNotBlank(token)) {
                    JsonNode json = getPosts(token, info);
                    model.put("json", json);
                } else {
                    LOG.error("failed to retrieve an Oauth token.");
                }
            } else {
                LOG.debug("no facebook account has been configured.");
            }
            model.put("info", info);
            return model;
        } catch (RepositoryException | IOException e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

    private JsonNode getPosts(String token, FacebookPostsInfo info) throws IOException {
        WebClient postClient = WebClient.create("https://graph.facebook.com").path(info.getAccount() + "/posts")
                .query("access_token", token).query("limit", info.getLimit());
        String content = postClient.get(String.class);
        return objectMapper.readTree(content);
    }

    private String getOauthToken() {
        String result = null;
        WebClient webClient = WebClient.create("https://graph.facebook.com").path("/oauth/access_token")
                .query("client_id", config.getClientId()).query("client_secret", config.getClientSecret())
                .query("grant_type", "client_credentials");

        String stringToken = webClient.get(String.class);
        if (stringToken != null && stringToken.indexOf('=') > 0) {
            result = stringToken.substring(stringToken.indexOf('=') + 1);
        }
        return result;
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
