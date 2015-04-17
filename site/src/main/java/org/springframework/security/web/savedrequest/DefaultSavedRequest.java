/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.web.savedrequest;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import com.google.common.base.Strings;

public class DefaultSavedRequest implements SavedRequest {

    private static final Pattern CONTEXT_PATTERN = Pattern.compile("/site/");

    private static final Logger logger = LoggerFactory.getLogger(DefaultSavedRequest.class);
    /**
     * @deprecated Use the value in {@link WebAttributes} directly.
     */
    @Deprecated
    public static final String SPRING_SECURITY_SAVED_REQUEST_KEY = WebAttributes.SAVED_REQUEST;

    private static final String HEADER_IF_NONE_MATCH = "If-None-Match";
    private static final String HEADER_IF_MODIFIED_SINCE = "If-Modified-Since";
    private static final long serialVersionUID = -6834315181718856668L;

    //~ Instance fields ================================================================================================

    private final ArrayList<SavedCookie> cookies = new ArrayList<SavedCookie>();
    private final ArrayList<Locale> locales = new ArrayList<Locale>();
    private final Map<String, List<String>> headers = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
    private final Map<String, String[]> parameters = new TreeMap<String, String[]>(String.CASE_INSENSITIVE_ORDER);
    private final String contextPath;
    private final String method;
    private final String pathInfo;
    private final String queryString;
    private final String requestURI;
    private final String requestURL;
    private final String scheme;
    private final String serverName;
    private final String servletPath;
    private final int serverPort;

    //~ Constructors ===================================================================================================

    public DefaultSavedRequest(HttpServletRequest request, PortResolver portResolver) {
        Assert.notNull(request, "Request required");
        Assert.notNull(portResolver, "PortResolver required");

        // Cookies
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                this.addCookie(cookies[i]);
            }
        }

        // Headers
        Enumeration<String> names = request.getHeaderNames();

        while (names.hasMoreElements()) {
            String name = names.nextElement();
            // Skip If-Modified-Since and If-None-Match header. SEC-1412, SEC-1624.
            if (HEADER_IF_MODIFIED_SINCE.equalsIgnoreCase(name) || HEADER_IF_NONE_MATCH.equalsIgnoreCase(name)) {
                continue;
            }
            Enumeration<String> values = request.getHeaders(name);

            while (values.hasMoreElements()) {
                this.addHeader(name, values.nextElement());
            }
        }

        // Locales
        Enumeration<Locale> locales = request.getLocales();

        while (locales.hasMoreElements()) {
            Locale locale = (Locale) locales.nextElement();
            this.addLocale(locale);
        }

        // Parameters
        Map<String, String[]> parameters = request.getParameterMap();

        for (String paramName : parameters.keySet()) {
            String[] paramValues = parameters.get(paramName);
            this.addParameter(paramName, (String[]) paramValues);
        }

        // Primitives
        this.method = request.getMethod();
        this.pathInfo = request.getPathInfo();
        this.queryString = request.getQueryString();

        this.serverPort = portResolver.getServerPort(request);

        this.scheme = request.getScheme();
        this.serverName = request.getServerName();
        if(serverName != null && serverName.equals("localhost")){
            this.requestURI = request.getRequestURI();
            this.requestURL = request.getRequestURL().toString();
            this.contextPath = request.getContextPath();
        }else{
            this.requestURI = CONTEXT_PATTERN.matcher(request.getRequestURI()).replaceFirst("/");
            this.requestURL = CONTEXT_PATTERN.matcher(CONTEXT_PATTERN.matcher(request.getRequestURI()).replaceFirst("/")).replaceFirst("/");;
            this.contextPath = "/";
        }
        this.servletPath = request.getServletPath();
    }

    //~ Methods ========================================================================================================

    private void addCookie(Cookie cookie) {
        cookies.add(new SavedCookie(cookie));
    }

    private void addHeader(String name, String value) {
        List<String> values = headers.get(name);

        if (values == null) {
            values = new ArrayList<String>();
            headers.put(name, values);
        }

        values.add(value);
    }

    private void addLocale(Locale locale) {
        locales.add(locale);
    }

    private void addParameter(String name, String[] values) {
        parameters.put(name, values);
    }

    /**
     * Determines if the current request matches the <code>DefaultSavedRequest</code>.
     * <p/>
     * All URL arguments are considered but not cookies, locales, headers or parameters.
     * <p/>
     */
    public boolean doesRequestMatch(HttpServletRequest request, PortResolver portResolver) {

        if (!propertyEquals("pathInfo", this.pathInfo, request.getPathInfo())) {
            return false;
        }

        if (!propertyEquals("queryString", this.queryString, request.getQueryString())) {
            return false;
        }

        String comparedUri = request.getRequestURI();
        if(!Strings.isNullOrEmpty(comparedUri)){
            comparedUri =  CONTEXT_PATTERN.matcher(comparedUri).replaceFirst("/");
        }
        if (!propertyEquals("requestURI", this.requestURI, comparedUri)) {
            return false;
        }

        if (!"GET".equals(request.getMethod()) && "GET".equals(method)) {
            // A save GET should not match an incoming non-GET method
            return false;
        }

        if (!propertyEquals("serverPort", new Integer(this.serverPort), new Integer(portResolver.getServerPort(request)))) {
            return false;
        }
        String comparedURL = request.getRequestURL().toString();
        if (!Strings.isNullOrEmpty(comparedURL)) {
            comparedURL = CONTEXT_PATTERN.matcher(comparedURL).replaceFirst("/");
        }
        if (!propertyEquals("requestURL", this.requestURL, comparedURL)) {
            return false;
        }

        if (!propertyEquals("scheme", this.scheme, request.getScheme())) {
            return false;
        }

        if (!propertyEquals("serverName", this.serverName, request.getServerName())) {
            return false;
        }

        if (!propertyEquals("contextPath", this.contextPath, request.getContextPath())) {
            return false;
        }

        if (!propertyEquals("servletPath", this.servletPath, request.getServletPath())) {
            return false;
        }
        return true;
    }

    public String getContextPath() {
        return contextPath;
    }

    public List<Cookie> getCookies() {
        List<Cookie> cookieList = new ArrayList<Cookie>(cookies.size());

        for (SavedCookie savedCookie : cookies) {
            cookieList.add(savedCookie.getCookie());
        }

        return cookieList;
    }

    /**
     * Indicates the URL that the user agent used for this request.
     *
     * @return the full URL of this request
     */
    public String getRedirectUrl() {
        return UrlUtils.buildFullRequestUrl(scheme, serverName, serverPort, requestURI, queryString);
    }

    public Collection<String> getHeaderNames() {
        return headers.keySet();
    }

    public List<String> getHeaderValues(String name) {
        List<String> values = headers.get(name);

        if (values == null) {
            return Collections.emptyList();
        }

        return values;
    }

    public List<Locale> getLocales() {
        return locales;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String[]> getParameterMap() {
        return parameters;
    }

    public Collection<String> getParameterNames() {
        return parameters.keySet();
    }

    public String[] getParameterValues(String name) {
        return ((String[]) parameters.get(name));
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public String getQueryString() {
        return (this.queryString);
    }

    public String getRequestURI() {
        return (this.requestURI);
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getScheme() {
        return scheme;
    }

    public String getServerName() {
        return serverName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServletPath() {
        return servletPath;
    }

    private boolean propertyEquals(String log, Object arg1, Object arg2) {
        if ((arg1 == null) && (arg2 == null)) {
            if (logger.isDebugEnabled()) {
                logger.debug(log + ": both null (property equals)");
            }

            return true;
        }

        if (((arg1 == null) && (arg2 != null)) || ((arg1 != null) && (arg2 == null))) {
            if (logger.isDebugEnabled()) {
                logger.debug(log + ": arg1=" + arg1 + "; arg2=" + arg2 + " (property not equals)");
            }

            return false;
        }

        if (arg1.equals(arg2)) {
            if (logger.isDebugEnabled()) {
                logger.debug(log + ": arg1=" + arg1 + "; arg2=" + arg2 + " (property equals)");
            }

            return true;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug(log + ": arg1=" + arg1 + "; arg2=" + arg2 + " (property not equals)");
            }

            return false;
        }
    }

    public String toString() {
        return "DefaultSavedRequest[" + getRedirectUrl() + "]";
    }
}
