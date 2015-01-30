package nl.hsleiden.tags;

import hslbeans.ArticlePage;
import hslbeans.BachelorPage;
import hslbeans.ExternalLink;
import hslbeans.Image;
import hslbeans.ImageTeaser;
import hslbeans.InfoBlock;
import hslbeans.InternalLink;
import hslbeans.WebPage;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import nl.hsleiden.beans.EventPageBean;
import nl.hsleiden.channels.WebsiteInfo;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoResource;
import org.hippoecm.hst.core.component.HstRequest;

public class Functions {

    private Functions() {
    }

    public static WebsiteInfo getWebsitePropertyList(HttpServletRequest request) {
        WebsiteInfo result = null;
        HstRequest req = (HstRequest) request;
        Mount mount = req.getRequestContext().getResolvedMount().getMount();
        result = mount.getChannelInfo();        
        return result;
    }

    public static boolean isSubclassOfWebPage(HippoBean hippoBean) {
        boolean result = false;
        if (hippoBean instanceof WebPage) {
            result = true;
        }
        return result;
    }

    public static String getAssetTitle(HippoResource asset) {
        String result = asset.getParentBean().getPath();
        result = result.substring(result.lastIndexOf("/") + 1);
        return result;
    }

    public static HippoBean getFirstFlexibleBlockImage(WebPage bean) {
        HippoBean result = null;
        if (bean instanceof ArticlePage) {
            ArticlePage article = (ArticlePage) bean;
            List<HippoBean> flexibleblock = article.getFlexibleblock();
            for (HippoBean hippoBean : flexibleblock) {
                if (hippoBean instanceof Image && ((Image) hippoBean).getImage() != null) {
                    result = hippoBean;
                }
            }
        }
        return result;
    }
        
    public static Integer indexOf(List<String> list, String item) {
        return list.indexOf(item);
    }
        
    public static String getConfiguredLink(ImageTeaser imgTeaser){
        String result = "";
        InternalLink internallink = imgTeaser.getInternallink();
        ExternalLink externallink = imgTeaser.getExternallink();

        if(internallink !=null && internallink.getLink()!=null && !internallink.getLinkTitle().isEmpty()){
            result = "int";
        }

        if(result.isEmpty()){
            result = checkExternalLink(externallink);
        }

        return result;
    }

    private static String checkExternalLink(ExternalLink externallink) {
        String result = "";
        if(externallink !=null && !externallink.getLinkUrl().isEmpty() && !externallink.getLinkTitle().isEmpty()){
            result = "ext";
        }
        return result;
    }
    
    public static Boolean isInfoBlockDisplayable(HippoBean document){
        boolean result = false;
        
        if(document instanceof EventPageBean){
            EventPageBean event = (EventPageBean) document;
            result = checkInfoBlock(event.getInfoBlock());
        }else if (document instanceof BachelorPage){
            BachelorPage bachelor = (BachelorPage) document;
            result = checkInfoBlock(bachelor.getInfoBlock());
        }
        
        return result;
    }

    private static boolean checkInfoBlock(InfoBlock infoBlock) {
        boolean result = false;
        if(infoBlock!=null && 
           (!infoBlock.getInfoText().isEmpty() || infoBlock.getInfoLink()!=null || !infoBlock.getInfoLines().isEmpty())){
           result = true; 
        }
        return result;
    }    
    
    public static Boolean isCookiesRefused(HttpServletRequest request) {
        boolean result = false;
        Cookie[] cookies = request.getCookies();
        
        if(cookies!=null){            
            for (Cookie cookie : cookies) {
                if("refuseCookies".equalsIgnoreCase(cookie.getName())){
                    result = true;
                }
            }
        }

        return result;
    }
}
