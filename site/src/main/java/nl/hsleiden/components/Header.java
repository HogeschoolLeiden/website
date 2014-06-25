package nl.hsleiden.components;

import nl.hsleiden.channels.WebsiteInfo;
import nl.hsleiden.utils.Constants.Attributes;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Header {

    private static final Logger LOG = LoggerFactory.getLogger(Header.class);

    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        final Mount mount = request.getRequestContext().getResolvedMount().getMount();
        final WebsiteInfo info = mount.getChannelInfo();

        if (info != null) {
            request.setAttribute(Attributes.HEADER_NAME, info.getHeaderName());
            request.setAttribute(Attributes.LOGO, getLogoBean(request, info));
        } else {
            LOG.warn("No channel info available for website '{}'", mount.getMountPath());
        }
    }

    private Object getLogoBean(HstRequest request, WebsiteInfo info) {
        Object result = null;
        String logoPath = info.getLogoPath();
        try {
            result = request.getRequestContext().getObjectBeanManager().getObject(logoPath);
        } catch (ObjectBeanManagerException e) {
            LOG.info("ImageSet is null, will use the static logo image" , e);
        }
        return result;
    }
}
