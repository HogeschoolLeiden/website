package nl.hsleiden.utils;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.HstRequestContext;

public class HslUtils {

    private HslUtils() {
        super();
    }

    public static String getContextPath(HstRequest request) {
        String contextPath = request.getRequestContext().getVirtualHost().getVirtualHosts().getDefaultContextPath();
        boolean contextPathInUrl = request.getRequestContext().getResolvedMount().getMount().isContextPathInUrl();
        if (StringUtils.isBlank(contextPath)) {
            contextPath = request.getContextPath();
        }
        return contextPathInUrl ? contextPath : "";
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCachedCall(Call<T> call, HstRequest request, String attributeName) {
        T result;
        Class<T> type = call.getType();
        HstRequestContext requestContext = request.getRequestContext();
        if (type == null) {
            throw new IllegalArgumentException("Call Type is required.");
        }
        if (StringUtils.isBlank(attributeName)) {
            throw new IllegalArgumentException("attributeName is required.");

        }
        Object attribute = requestContext.getAttribute(attributeName);
        if (attribute != null && type.isAssignableFrom(attribute.getClass())) {
            result = (T) attribute;
        } else {
            result = call.makeCall(request);
            requestContext.setAttribute(attributeName, result);
        }

        return result;
    }

    public static interface Call<T> {
        public T makeCall(HstRequest request);

        public Class<T> getType();
    }

    public static boolean areTheSameNode(HippoBean b1, HippoBean b2) {
        return b1.getCanonicalUUID().equals(b2.getCanonicalUUID());

    }

    public static HstLink createHstLink(HippoBean bean, final HstRequest request) {
        HstRequestContext reqContext = request.getRequestContext();
        return reqContext.getHstLinkCreator().create(bean, reqContext);
    }

}
