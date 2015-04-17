package nl.hinttech.hsleiden.pi.components;

import nl.hinttech.hsleiden.pi.beans.Breadcrumb;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Component for rendering the items in the header (for now only the breadcrumb).
 */
public class HeaderComponent extends BaseHstComponent {

    public static final Logger log = LoggerFactory.getLogger(HeaderComponent.class);

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
        
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        request.setAttribute("userId", userId);
        log.debug("UserID : {}", userId);
    
        request.setAttribute("breadcrumb", Breadcrumb.getBreadcrumb(request));
    }
}
