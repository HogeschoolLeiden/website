package nl.hsleiden.components.catalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import nl.hsleiden.beans.TweetStatus;
import nl.hsleiden.beans.mixin.TwitterMixin;
import nl.hsleiden.componentsinfo.TwitterFeedInfo;
import nl.hsleiden.utils.Constants;
import nl.hsleiden.utils.HslUtils;
import nl.hsleiden.utils.TwitterUtils;
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
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.tdclighthouse.prototype.components.AjaxEnabledComponent;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = TwitterFeedInfo.class)
public class TwitterFeed extends AjaxEnabledComponent {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterFeed.class);
    private Twitter twitter = TwitterFactory.getSingleton();

    @Override
    public Map<String, Object> getModel(HstRequest request, HstResponse response) {

        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TwitterFeedInfo parametersInfo = getConfiguration(request);
            model.put(Constants.Attributes.PARAM_INFO, parametersInfo);

            String from = parametersInfo.getFrom();
            String searchQuery = parametersInfo.getQuery();

            if (parametersInfo.getLimit() != 0) {

                if (from != null && !from.isEmpty()) {
                    ResponseList<Status> userTimeline = twitter.timelines().getUserTimeline(from);
                    model.put("tweets", populateTweetsList(userTimeline, parametersInfo));
                } else if (searchQuery != null && !searchQuery.isEmpty()) {
                    model.put("tweets", getTweetsByQuery(searchQuery, parametersInfo, request));
                }
            } else {
                request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.0.tweets.message");
            }

            return model;
        } catch (RepositoryException e) {
            throw new HstComponentException(e.getMessage(), e);
        } catch (TwitterException e) {
            throw new HstComponentException("Error while retrieving Tweets.", e);
        }
    }

    private List<TweetStatus> getTweetsByQuery(String completeQuery, TwitterFeedInfo info, HstRequest request)
            throws TwitterException {

        List<TweetStatus> result = null;

        if (!completeQuery.isEmpty()) {
            Query query = new Query(completeQuery);
            LOG.debug("Twitter feed query: " + completeQuery);
            query.setCount(info.getLimit());
            QueryResult queryResult = null;
            queryResult = twitter.search(query);
            List<Status> statuses = queryResult.getTweets();
            result = populateTweetsList(statuses, info);

        } else {
            request.setAttribute(WidgetConstants.WEB_MASTER_MESSAGE, "webmaster.notweets.message");
        }
        return result;
    }

    private List<TweetStatus> populateTweetsList(List<Status> statuses, TwitterFeedInfo info) {
        List<TweetStatus> result = new ArrayList<TweetStatus>();
        for (int i = 0; i < statuses.size() && result.size() < info.getLimit(); i++) {
            TweetStatus ts = new TweetStatus();
            Status status = statuses.get(i);
            ts.setStatus(status);
            ts.setText(TwitterUtils.convertMessage(status.getText(), status, info.getShowImages()));
            result.add(ts);
        }
        return result;
    }

    private TwitterFeedInfo getConfiguration(HstRequest request) throws RepositoryException {
        TwitterFeedInfo paramInfo = this.<TwitterFeedInfo> getComponentParametersInfo(request);
        if (paramInfo.getUseMixin() != null && paramInfo.getUseMixin()
                && request.getRequestContext().getContentBean() != null) {

            HippoBean contentBean = HslUtils.getBean(request);
            HippoBean proxy = BeanUtils.getMixinProxy(contentBean);
            if (proxy instanceof TwitterMixin) {
                paramInfo = ((TwitterMixin) proxy).getTwitterCompoundMixin();
            }
        }
        return paramInfo;
    }
}
