package nl.hinttech.hsleiden.pi.components;

import nl.hinttech.hsleiden.pi.beans.Breadcrumb;
import nl.hinttech.hsleiden.pi.beans.SiteSettings;
import nl.hinttech.hsleiden.pi.beans.TextDocument;
import nl.hinttech.hsleiden.pi.beans.WebsiteTexts;
import nl.hinttech.hsleiden.pi.util.HSLeiden;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The abstract base class of all our Components.
 * 
 */
public abstract class BaseComponent extends BaseHstComponent {

    public static final Logger log = LoggerFactory.getLogger(BaseComponent.class);

    private WebsiteTexts websiteTexts;
    private SiteSettings settings;
    
    public static final String ATTRIBUTE_WEBSITE_TEXTS = "websiteTexts";
    public static final String ATTRIBUTE_SITE_SETTINGS = "siteSettings";
    
    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
               
        makeWebsiteTextsAvailable(request);
        makeSiteSettingsAvailable(request);
    }

    /**
     * make the site settings document available to all components.  (do this only once per request)
     */
    private void makeSiteSettingsAvailable(final HstRequest request) {
        settings = (SiteSettings) request.getRequestContext().getAttribute(ATTRIBUTE_SITE_SETTINGS);
        if (settings == null) {
            HippoBean contentRoot = getContentRoot(request);
            settings  = contentRoot.getBean(HSLeiden.PATH_WEBSITE_SETTINGS_DOCUMENT);
            if (settings == null) {
                log.warn("No WebsiteText document found at {}", HSLeiden.PATH_WEBSITE_SETTINGS_DOCUMENT);
            }
            request.getRequestContext().setAttribute(ATTRIBUTE_SITE_SETTINGS, settings);
        }
    }

    /**
     * make the website texts document available to all components. (do this only once per request)
     */
    private void makeWebsiteTextsAvailable(final HstRequest request) {
               
        websiteTexts = (WebsiteTexts) request.getRequestContext().getAttribute(ATTRIBUTE_WEBSITE_TEXTS);
        if (websiteTexts == null) {
            HippoBean contentRoot = getContentRoot(request);
            websiteTexts = contentRoot.getBean(HSLeiden.PATH_WEBSITE_TEXTS_DOCUMENT);
            if (websiteTexts == null) {
                log.warn("No WebsiteText document found at {}", HSLeiden.PATH_WEBSITE_TEXTS_DOCUMENT);
            }
            request.getRequestContext().setAttribute(ATTRIBUTE_WEBSITE_TEXTS, websiteTexts);
        }
    }

    /**
     * Set the breadcrumb for the current request.
     */
    protected  void setBreadcrumb(final HstRequest request) {
        HstSiteMenuItem activeMenu = HSLeiden.getActiveMenu(request);
        Breadcrumb.setBreadcrumb(request, 2, activeMenu.getName(), getCurrentUrl(request));
    }
    
    /**
     * Set the breadcrumb for the current request and document
     * Use this method for detail pages.
     */
    protected  void setBreadcrumb(final HstRequest request, final TextDocument document) {
        Breadcrumb.setBreadcrumb(request, 3, document.getTitle(), getCurrentUrl(request));
    }
    
    /**
     * Clears the breadcrumb.
     */
    protected void clearBreadcrumb(final HstRequest request) {
        Breadcrumb.clear(request);
    }
    
    /**
     * Returns the formatted text specified by its key from the website texts document.
     */
    protected HippoHtml getHtmlText(final String key) {

        if (websiteTexts != null) {
            return websiteTexts.getHtmlText(key);
        }
        return null;
    }
    
    /**
     * Returns a specific setting from the {@link SiteSettings} document.
     */
    protected String getSetting(final String key) {
        if (settings != null) {
            return settings.getSetting(key); 
        }
        return null;
    }
    
    /**
     * Returns the label specified by its key from the website texts document.
     */
    protected String getLabel(final String key) {

        if (websiteTexts != null) {
            return websiteTexts.getLabel(key);
        }
        return "";
    }

    /**
     * Returns the unformatted text specified by its key from the website texts document.
     */
    protected String getText(final String key) {

        if (websiteTexts != null) {
            return websiteTexts.getText(key);
        }
        return "";
    }

    /**
     * Returns the website texts document.
     */
    public WebsiteTexts getWebsiteTexts(final HstRequest request) {
        return websiteTexts;
    }

    /**
     * Returns the content root (root folder) of the repository.
     */
    public HippoBean getContentRoot(final HstRequest request) {
        return request.getRequestContext().getSiteContentBaseBean();
    }

    /**
     * Returns the specified sitemap parameter of the active sitemap.
     */
    protected String getResolvedSiteMapParameter(final HstRequest request, final String parameterName) {
        return getResolvedSiteMapParameter(request, parameterName, null);
    }

    /**
     * Returns the specified sitemap parameter of the active sitemap. Returns the default value if parameter is empty or not configured.
     */
    protected String getResolvedSiteMapParameter(final HstRequest request, final String parameterName, final String defaultValue) {

        String value = request.getRequestContext().getResolvedSiteMapItem().getParameter(parameterName);
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value;
    }
    
    /**
     * Returns the (relative) url of the current request (including the query parameters).
     */
    protected String getCurrentUrl(final HstRequest request) {
        String queryString = request.getQueryString();
        if (StringUtils.isNotBlank(queryString)) {
            return request.getPathInfo() + "?" + queryString;
        }
        return request.getPathInfo();
    }
    
    /**
     * Returns the "department" request parameter from the request.
     */
    protected String getDepartment(final HstRequest request) {
        return getPublicRequestParameter(request, "afdeling");
    }

    /**
     * Returns the "opleiding" request parameter from the request.
     */
    protected String getTraining(final HstRequest request) {
        return getPublicRequestParameter(request, "opleiding");
    }

}