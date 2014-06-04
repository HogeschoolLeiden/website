package nl.hsleiden.utils;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.core.component.HstRequest;

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
    
}
