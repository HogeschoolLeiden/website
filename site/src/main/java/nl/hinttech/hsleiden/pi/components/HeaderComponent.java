package nl.hinttech.hsleiden.pi.components;

import nl.hinttech.hsleiden.pi.beans.Breadcrumb;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component for rendering the items in the header (for now only the breadcrumb).
 */
public class HeaderComponent extends BaseHstComponent {

    public static final Logger log = LoggerFactory.getLogger(HeaderComponent.class);

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
        HstSiteMenu siteMenu = request.getRequestContext().getHstSiteMenus().getSiteMenu("appbar");
        request.setAttribute("appbar", siteMenu);
        request.setAttribute("breadcrumb", Breadcrumb.getBreadcrumb(request));
    }

}
