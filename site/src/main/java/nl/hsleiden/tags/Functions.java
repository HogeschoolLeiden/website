package nl.hsleiden.tags;

import hslbeans.ArticlePage;
import hslbeans.BachelorPage;
import hslbeans.ExternalLink;
import hslbeans.Image;
import hslbeans.ImageTeaser;
import hslbeans.InfoBlock;
import hslbeans.InternalLink;
import hslbeans.PersonnelPage;
import hslbeans.WebPage;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import nl.hsleiden.beans.EventPageBean;
import nl.hsleiden.channels.WebsiteInfo;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.configuration.sitemap.HstSiteMapItem;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoResource;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;

import com.tdclighthouse.prototype.utils.BeanUtils;
import com.tdclighthouse.prototype.utils.PaginatorWidget;

public class Functions {

    private static final String SEPARATOR = " - ";

    private Functions() {
    }

    public static WebsiteInfo getWebsitePropertyList(HttpServletRequest request) {
        WebsiteInfo result = null;
        HstRequest req = (HstRequest) request;
        Mount mount = req.getRequestContext().getResolvedMount().getMount();
        result = mount.getChannelInfo();
        return result;
    }

    public static WebsiteInfo getMainWebsitePropertyList(HttpServletRequest request) {
        WebsiteInfo result = null;
        HstRequest req = (HstRequest) request;
        Mount mount = req.getRequestContext().getMount("hsl");
        result = mount.getChannelInfo();
        return result;
    }

    public static boolean hasPrevPage(PaginatorWidget paginator) {
        boolean result = false;
        if (paginator.getPage() > paginator.getFirstShownPage()) {
            result = true;
        }
        return result;
    }

    public static boolean hasNextPage(PaginatorWidget paginator) {
        boolean result = false;
        if (paginator.getPage() < paginator.getLastShownPage()) {
            result = true;
        }
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
        if (bean instanceof PersonnelPage) {
            PersonnelPage personnel = (PersonnelPage) bean;
            List<HippoBean> flexibleblock = personnel.getMedewerkerflexibleblock();
            result = getFirstImage(flexibleblock);
        } else if (bean instanceof ArticlePage) {
            ArticlePage article = (ArticlePage) bean;
            List<HippoBean> flexibleblock = article.getFlexibleblock();
            result = getFirstImage(flexibleblock);
        }
        return result;
    }

    private static HippoBean getFirstImage(List<HippoBean> flexibleblocklist) {
        HippoBean result = null;
        for (HippoBean hippoBean : flexibleblocklist) {
            if (hippoBean instanceof Image && ((Image) hippoBean).getImage() != null) {
                result = hippoBean;
            }
        }
        return result;
    }

    public static Integer indexOf(List<String> list, String item) {
        return list.indexOf(item);
    }

    public static String getConfiguredLink(ImageTeaser imgTeaser) {
        String result = "";
        InternalLink internallink = imgTeaser.getInternallink();
        ExternalLink externallink = imgTeaser.getExternallink();

        if (internallink != null && internallink.getLink() != null && !internallink.getLinkTitle().isEmpty()) {
            result = "int";
        }

        if (result.isEmpty()) {
            result = checkExternalLink(externallink);
        }

        return result;
    }

    private static String checkExternalLink(ExternalLink externallink) {
        String result = "";
        if (externallink != null && !externallink.getLinkUrl().isEmpty() && !externallink.getLinkTitle().isEmpty()) {
            result = "ext";
        }
        return result;
    }

    public static Boolean isInfoBlockDisplayable(HippoBean document) {
        boolean result = false;

        if (document instanceof EventPageBean) {
            EventPageBean event = (EventPageBean) document;
            result = checkInfoBlock(event.getInfoBlock());
        } else if (document instanceof BachelorPage) {
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

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refuseCookies".equalsIgnoreCase(cookie.getName())) {
                    result = true;
                }
            }
        }

        return result;
    }

    public static String getBrowserTitle(HttpServletRequest request, HippoBean document) {
        String result = ""; 
        if (document instanceof WebPage) {
            result = composeBrowserTitle((HstRequest) request, (WebPage) document);
        }else{
            result = getWebsitePropertyList(request).getDefaultBrowserTitle();
        }
        return result;
    }

    private static String composeBrowserTitle(HstRequest request, WebPage webPage) {
 
        String result = "";

        String browserTitle = webPage.getBrowserTitle();
        String title = webPage.getTitle();
        String parentTitle = getParentTitle(request, title);

        if (browserTitle != null && !browserTitle.isEmpty()) {
            result = browserTitle;
        } else if (parentTitle != null && !parentTitle.isEmpty()) {
            result = title + SEPARATOR + parentTitle;
        } else {
            result = getWebsitePropertyList(request).getDefaultBrowserTitle() + SEPARATOR  + title;
        }

        return result;
    }

    private static String getParentTitle(HstRequest request, String actualPageTitle) {
        String result = null;
        
        ResolvedSiteMapItem resolvedSiteMapItem = request.getRequestContext().getResolvedSiteMapItem();
        HstSiteMapItem parentSiteMapItem = resolvedSiteMapItem.getHstSiteMapItem().getParentItem();
        
        if (parentSiteMapItem != null && parentSiteMapItem.getRelativeContentPath() != null) {
            HippoBean parentSiteMapItemBean = BeanUtils.getBean(parentSiteMapItem.getRelativeContentPath());
            if (parentSiteMapItemBean != null && parentSiteMapItemBean instanceof WebPage) {
                String parentTitle = ((WebPage) parentSiteMapItemBean).getTitle();
                if(!parentTitle.equals(actualPageTitle)){
                    result = parentTitle;
                }
            }
        }
        
        return result;
    }
}
