package org.hippoecm.hst.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.component.HstComponent;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.ComponentConfiguration;
import org.hippoecm.hst.core.request.HstRequestContext;

public class ParameterUtils {
    public static final String MY_MOCK_PARAMETER_INFO = "my_mock_parameterinfo";

    @SuppressWarnings("unchecked")
    public static <T> T getParametersInfo(HstComponent component, final ComponentConfiguration componentConfig, final HstRequest request) {
        return (T) request.getAttribute(MY_MOCK_PARAMETER_INFO);
    }
    
    public static void setRequestContext(HstRequestContext requestContext) {
        try {
            Method method = RequestContextProvider.class.getDeclaredMethod("set", HstRequestContext.class);
            method.setAccessible(true);
            method.invoke(null, requestContext);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // ignore not going to be invoked ever
            throw new RuntimeException(e);
        }
    }
}
