package nl.hinttech.hsleiden.pi.util;

import nl.hinttech.hsleiden.pi.beans.ContentDocument;
import nl.hinttech.hsleiden.pi.beans.ImageGalleryDocument;
import nl.hinttech.hsleiden.pi.beans.MinutesDocument;

import org.hippoecm.hst.content.beans.standard.HippoAsset;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class HSLeiden {

    public static final Logger log = LoggerFactory.getLogger(HSLeiden.class);
    
    public static final String NAMESPACE = "intranet";
    public static final String NAMESPACE_PREFIX = NAMESPACE + ":";

    public static final String TYPE_STUDENT = "student";
    public static final String TYPE_EMPLOYEE = "medewerker";
    public static final String TYPE_SERVICE = "service";

    public static final String METADATA_LABEL_INFO_FOR = "Informatie voor";
    public static final String METADATA_LABEL_TRAINING = "Opleiding";
    public static final String METADATA_LABEL_DEPARTMENT = "Afdeling";

    public static final String VALUELIST_DEPARTMENTS = "afdelingen";
    public static final String VALUELIST_TRAININGS = "opleidingen";

    public static final String PAGE_NOT_FOUND = "/pagenotfound";
    
    public static final String KEY_HOME_WELCOME = "home.welkom";
    
    
    public static final String PATH_WEBSITE_TEXTS_DOCUMENT = "admin/website-teksten-en-labels";
    public static final String PATH_WEBSITE_SETTINGS_DOCUMENT = "admin/instellingen";
    
    public static final String KEY_GOOGLE_ANALYTICS_ACCOUNTID = "google.analytics.accountid";
    public static final String KEY_CMS_URL = "cms.url";
    
    private static Class<? extends HippoBean>[] searchableClasses;
    private static Class<? extends HippoBean>[] browsableClasses;
    
    
    static {

        searchableClasses = new Class[4];
        searchableClasses[0] = ContentDocument.class;
        searchableClasses[1] = MinutesDocument.class;
        searchableClasses[2] = ImageGalleryDocument.class;
        searchableClasses[3] = HippoAsset.class;
        
        browsableClasses = new Class[3];
        browsableClasses[0] = ContentDocument.class;
        browsableClasses[1] = MinutesDocument.class;
        browsableClasses[2] = ImageGalleryDocument.class;
    }
    

    public static Class<? extends HippoBean>[] getSearchableClasses() {
        return searchableClasses;
    }
    

    public static Class<? extends HippoBean>[] getBrowsableClasses() {      
        return browsableClasses;
    }

    public static void rememberActiveMenu(HstRequest request) {
        request.getSession().setAttribute("activeMenu", getActiveMenu(request));
    }
    
    public static HstSiteMenuItem getActiveMenu(HstRequest request) {
        HstSiteMenu mainMenu = request.getRequestContext().getHstSiteMenus().getSiteMenu("main");
        return mainMenu.getSelectSiteMenuItem();
    }
    
    public static HstSiteMenuItem getRememberedActiveMenu(HstRequest request) {
        return  (HstSiteMenuItem) request.getSession().getAttribute("activeMenu");
    }
 
}
