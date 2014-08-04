package nl.hsleiden.components.catalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.hsleiden.componentsinfo.TwitterFeedInfo;

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

@ParametersInfo(type = TwitterFeedInfo.class)
public class TwitterFeed extends AjaxEnabledComponent {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterFeed.class);

    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {
        
        Map<String, Object> model = new HashMap<String, Object>();
        
        TwitterFeedInfo parametersInfo = getComponentParametersInfo(request);
        model.put("info", parametersInfo);

        String from = parametersInfo.getFrom();
        String searchQuery = parametersInfo.getQuery();
        String completeQuery = "";

        if (from != null && !from.isEmpty()) {
            completeQuery += "from:" + from + " ";
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            completeQuery += searchQuery;
        }

        model.put("tweets", getTweets(completeQuery, parametersInfo));
        
        return model;
    }
    
    private List<Status> getTweets(String completeQuery, TwitterFeedInfo info) {
        
        List<Status> result = new ArrayList<Status>();
        
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
        return result;
    }
}
