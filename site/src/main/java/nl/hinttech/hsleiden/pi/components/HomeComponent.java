package nl.hinttech.hsleiden.pi.components;

import nl.hinttech.hsleiden.pi.util.HSLeiden;

import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

/**
 * Component for rendering the home page content.
 *
 */
public class HomeComponent extends BaseComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        
        // The text that is displayed on the Home page is configured in the Website Texts document
        // in the admin folder.
        HippoHtml welcomeText = getHtmlText(HSLeiden.KEY_HOME_WELCOME);

        request.setAttribute("welcomeText", welcomeText);
        
        HSLeiden.rememberActiveMenu(request);
        
        // No breadcrumb must be visible on the home page
        clearBreadcrumb(request);
    }

}
