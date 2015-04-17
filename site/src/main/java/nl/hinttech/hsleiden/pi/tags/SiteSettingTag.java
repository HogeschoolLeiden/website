package nl.hinttech.hsleiden.pi.tags;

import static nl.hinttech.hsleiden.pi.components.BaseComponent.ATTRIBUTE_SITE_SETTINGS;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import nl.hinttech.hsleiden.pi.beans.SiteSettings;

import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.util.HstRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tag for getting a setting from the site settings document.
 * NOTE: this tag relies on the BaseComponent to put the {@link SiteSettings} document
 * on the request as an attribute.
 *
 */
public class SiteSettingTag extends SimpleTagSupport {

    public static final Logger log = LoggerFactory.getLogger(SiteSettingTag.class);
    
    private String key;

    @Override
    public void doTag() throws JspException, IOException {

        PageContext pageContext = (PageContext) getJspContext();
        
        HttpServletRequest servletRequest = (HttpServletRequest) pageContext.getRequest();
        HstRequestContext requestContext = HstRequestUtils.getHstRequestContext(servletRequest);
        
        JspWriter out = pageContext.getOut();
        SiteSettings settings = (SiteSettings) requestContext.getAttribute(ATTRIBUTE_SITE_SETTINGS);
        
        String value = "";
        if (settings != null) {       
            value =settings.getSetting(key);
            if (value == null) {
                log.info("No value was set for site setting key {}", key);
            }
        } 
        out.print(value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
   
}
