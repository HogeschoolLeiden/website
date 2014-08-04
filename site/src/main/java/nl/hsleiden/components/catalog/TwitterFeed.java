package nl.hsleiden.components.catalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.mixin.TwitterMixin;
import nl.hsleiden.componentsinfo.TwitterFeedInfo;
import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.Constants.WidgetConstants;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = TwitterFeedInfo.class)
public class TwitterFeed extends AjaxEnabledComponent {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterFeed.class);

    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TwitterFeedInfo parametersInfo = getConfiguration(request);
            model.put(Constants.Attributes.PARAM_INFO, parametersInfo);
    
            String from = parametersInfo.getFrom();
            String searchQuery = parametersInfo.getQuery();
            String completeQuery = "";
    
            if (from != null && !from.isEmpty()) {
                completeQuery += "from:" + from + " ";
            }
            if (searchQuery != null && !searchQuery.isEmpty()) {
                completeQuery += searchQuery;
            }
    
            model.put("tweets", getTweets(completeQuery, parametersInfo, request));
            
            return model;
        } catch (RepositoryException e) {
            LOG.error(e.getMessage(), e);
            throw new HstComponentException(e.getMessage(), e);
        }
    }
    
    private List<Status> getTweets(String completeQuery, TwitterFeedInfo info, HstRequest request) {
        
        List<Status> result = new ArrayList<Status>();
        
        if(!completeQuery.isEmpty()){
            Twitter twitter = TwitterFactory.getSingleton();
            Query query = new Query(completeQuery);
            LOG.debug("Twitter feed query: " + completeQuery);
            query.setCount(info.getLimit());
            QueryResult queryResult = null;
            try {
                queryResult = twitter.search(query);
                result = queryResult.getTweets();
                
            } catch (TwitterException e) {
                LOG.error("Error while retrieving Tweets.", e);
            }
        }else{
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.notweets.message");
        }
        return result;
    }
    
    private TwitterFeedInfo getConfiguration(HstRequest request) throws RepositoryException {
        TwitterFeedInfo paramInfo = this.<TwitterFeedInfo> getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && paramInfo.getUseMixin()) {
            HippoBean proxy = BeanUtils.getMixinProxy(request.getRequestContext().getContentBean());
            if (proxy instanceof TwitterMixin) {
                paramInfo = ((TwitterMixin) proxy).getTwitterCompoundMixin();
            }
        }
        return paramInfo;
    }
}
