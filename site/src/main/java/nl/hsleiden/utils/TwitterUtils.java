package nl.hsleiden.utils;

import java.text.MessageFormat;

import twitter4j.EntitySupport;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

public class TwitterUtils {
    
    private static final String TWEET_URL = "<a href={0} target=_blank>{1}</a>";
    private static final String TWEET_HASH_URL = "<a href=https://twitter.com/#!/search?q=%23{0}&src=typd target=_blank>{1}</a>";
    private static final String TWEET_MENTIONED_URL = "<a href=https://twitter.com/#!/search?q=from:{0}&src=typd target=_blank>{1}</a>";
    private static final String TWEET_IMG = "<img src={0} title={1}></img>";
    
    private TwitterUtils() {
        super();
    }


    public static String convertMessage(String message, EntitySupport entity) {
        MessageFormat urlFormatter = new MessageFormat(TWEET_URL);
        MessageFormat hashFormatter = new MessageFormat(TWEET_HASH_URL);
        MessageFormat userFormatter = new MessageFormat(TWEET_MENTIONED_URL);
        MessageFormat imgFormatter = new MessageFormat(TWEET_IMG);
            
        String formattedMessage = message;
        
        URLEntity[] urlEntities = entity.getURLEntities(); 
        if(urlEntities != null) {
            for(URLEntity urlEntity : urlEntities) {
                String url = urlEntity.getURL();
                formattedMessage = formattedMessage.replace(url, urlFormatter.format(new Object[]{url,url}));
            }
        }
        
        MediaEntity[] mediaEntities = entity.getMediaEntities();
        if(mediaEntities!=null){
            for (MediaEntity mediaEntity : mediaEntities) {
                if(mediaEntity.getType().equals("photo")){
                    String mediaUrl = mediaEntity.getURL();
                    
                    formattedMessage = formattedMessage.replace(mediaUrl, imgFormatter.format(new Object[]{mediaEntity.getMediaURL(),mediaUrl}));  
                }
            }
        }
        
        HashtagEntity[] hashEntities = entity.getHashtagEntities(); 
        if(hashEntities != null) {
            for(HashtagEntity hashEntity : hashEntities) {
                String query = hashEntity.getText();
                String tag = "#" + hashEntity.getText();
                formattedMessage = formattedMessage.replace(tag, hashFormatter.format(new Object[]{query,tag}));
            }
        }
        
        UserMentionEntity[] userEntities = entity.getUserMentionEntities(); 
        if(userEntities != null) {
            for(UserMentionEntity userEntity : userEntities) {
                String query = userEntity.getScreenName();
                String screenName = "@" + userEntity.getScreenName();
                formattedMessage = formattedMessage.replace(screenName, userFormatter.format(new Object[]{query,screenName}));
            }
        }
                        
        return formattedMessage;
    }
    
}
