package nl.hsleiden.beans;

import hslbeans.YoutubePlayerParameters;
import hslbeans.YoutubeUrlParameters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hippoecm.hst.content.beans.Node;

import com.tdclighthouse.prototype.utils.URL;

@Node(jcrType = "hsl:YoutubeCompound")
public class YoutubeCompound extends hslbeans.YoutubeCompound {

    private static final String YOUTUBE_REGEX = 
            "https?://www.youtube.com/.*(\\?|&)(video_id=|/|v=)([^(&\\\r\\\n\\?)]*).*";
    private static final String YOUTUBE_START = "http://www.youtube.com/v/";
    
    public static String ALLOW_FULL_SCREEN = "allowFullScreen";
    public static String AUTOPLAY = "autoplay";
    public static String DISABLEKB = "disablekb";
    public static String SHOWINFO = "showinfo";
    public static String THEME = "theme";
    public static String REL = "rel";

    private String url;
    
    public String getUrl(){
        if (this.url == null) {
            YoutubeUrlParameters urlParams = getYoutubeUrlParameters();
            YoutubePlayerParameters playerParams = getYoutubePlayerParameters();
            String videoId = getVideoId(urlParams.getYoutubeUrl());
            
            if(!videoId.isEmpty()){         
                String videoURL = YOUTUBE_START + videoId;
                URL configuredUrl = new URL(videoURL);
                configuredUrl.setQueryParameter(AUTOPLAY, booelanToString(playerParams.getAutoplay()));
                configuredUrl.setQueryParameter(DISABLEKB, booelanToString(playerParams.getDisablekb()));
                configuredUrl.setQueryParameter(SHOWINFO, booelanToString(playerParams.getShowinfo()));
                configuredUrl.setQueryParameter(REL, booelanToString(playerParams.getRel()));                    
                configuredUrl.setQueryParameter(THEME, urlParams.getTheme());
                this.url = configuredUrl.toString();
            }
        }
        return this.url;
        
    }
    
    
    private String getVideoId(String videoURL) {
        String result = "";
        Pattern pat = Pattern.compile(YOUTUBE_REGEX);
        Matcher mSumm = pat.matcher(videoURL);
        if (mSumm.find()) {
            result = mSumm.group(3);
        }
        return result;
    }
       
    private String booelanToString(boolean value) {
        return value ? "1" : "0";
    }

}