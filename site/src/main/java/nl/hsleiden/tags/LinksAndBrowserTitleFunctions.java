package nl.hsleiden.tags;

import hslbeans.ExternalLink;
import hslbeans.InternalLink;
import hslbeans.WebPage;

import javax.servlet.http.HttpServletRequest;

import nl.hsleiden.beans.interfaces.LinkInterface;
import nl.hsleiden.beans.interfaces.TeasersInterface;
import nl.hsleiden.utils.Constants.Values;

import org.hippoecm.hst.configuration.sitemap.HstSiteMapItem;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;

import com.tdclighthouse.prototype.utils.BeanUtils;

public class LinksAndBrowserTitleFunctions {
    
    private LinksAndBrowserTitleFunctions() {
    }
    
    public static String getConfiguredLink(HippoBean bean) {
        InternalLink internallink = null;
        ExternalLink externallink = null;
        if(bean instanceof TeasersInterface){
            internallink = ((TeasersInterface) bean).getLink();
            externallink = ((TeasersInterface) bean).getExternallink();
        } 
        return getLinkType(internallink, externallink);
    }

    public static String getLinkAlt(HippoBean linkBean) {
        String result = "";
        if (linkBean instanceof LinkInterface) {
            LinkInterface link = (LinkInterface) linkBean;
            if (link.getAlt() != null && !link.getAlt().isEmpty()) {
                result = link.getAlt();
            } else {
                result = link.getLinkTitle();
            }
        }
        return result;
    }
    
    public static String getBrowserTitle(HttpServletRequest request, HippoBean document) {
        String result = "";
        if (document instanceof WebPage) {
            result = composeBrowserTitle((HstRequest) request, (WebPage) document);
        } else {
            result = Functions.getWebsitePropertyList(request).getDefaultBrowserTitle();
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
            result = title + Values.SEPARATOR + parentTitle;
        } else {
            result = Functions.getWebsitePropertyList(request).getDefaultBrowserTitle() + Values.SEPARATOR + title;
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
                if (!parentTitle.equals(actualPageTitle)) {
                    result = parentTitle;
                }
            }
        }
        return result;
    }

    private static String getLinkType(InternalLink internallink, ExternalLink externallink) {
        String result = "";
        if (internallink != null && internallink.getLink() != null && !internallink.getLinkTitle().isEmpty()) {
            result = "int";
        }else if (externallink != null && !externallink.getLinkUrl().isEmpty() && !externallink.getLinkTitle().isEmpty()) {
            result = "ext";
        }
        return result;
    }
}
