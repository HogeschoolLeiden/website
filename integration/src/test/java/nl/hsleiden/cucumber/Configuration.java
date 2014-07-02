package nl.hsleiden.cucumber;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

public class Configuration {
    private static final String BUNDLE_NAME = "nl.hsleiden.cucumber.configuration";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private Configuration() {
    }

    public static String getString(String key) {
        String result;
        try {
            result = RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            result = '!' + key + '!';
        }
        return result;
    }

    public static String getString(String key, String defaultValue) {
        String result;
        try {
            result = RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            result = defaultValue;
        }
        return result;
    }

    public static String getNotEmptyString(String key, String defaultValue) {
        String result = defaultValue;
        try {
            String resource = RESOURCE_BUNDLE.getString(key);
            if (StringUtils.isNotBlank(resource)) {
                result = resource;
            }
        } catch (MissingResourceException e) {
            // ignore
        }
        return result;
    }

    public static String getSiteBaseUrl() {
        String hostname = getNotEmptyString("integration.server.host", "localhost");
        String port = getNotEmptyString("integration.server.port", "9208");
        String protocl = getNotEmptyString("integration.server.protocol", "http");
        String contextPath = getNotEmptyString("site.context.path", "/site");
        return createUrl(hostname, port, protocl, contextPath);

    }

    public static String getCmsBaseUrl() {
        String hostname = getNotEmptyString("integration.server.host", "localhost");
        String port = getNotEmptyString("integration.server.port", "9208");
        String protocl = getNotEmptyString("integration.server.protocol", "http");
        String contextPath = getNotEmptyString("cms.context.path", "/cms");
        return createUrl(hostname, port, protocl, contextPath);
    }

    private static String createUrl(String hostname, String port, String protocl, String contextPath) {
        return new StringBuilder(protocl).append("://").append(hostname).append(":").append(port).append(contextPath)
                .append("/").toString();
    }

}
