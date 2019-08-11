package by.platonov.music.command;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

/**
 * the class encapsulates in itself all the data coming from the {@link HttpServletRequest}
 *
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class RequestContent {

    /**
     * {@link HttpServletRequest} attributes data
     */
    private Map<String, Object> requestAttributes;

    /**
     * {@link HttpServletRequest}  parameters data
     */
    private Map<String, String[]> requestParameters;

    /**
     * {@link HttpSession} attributes data
     */
    private Map<String, Object> sessionAttributes;

    /**
     * {@link Cookie} data
     */
    private Map<String, String> cookies;

    /**
     * multipart {@link Part} data
     */
    private List<Part> requestParts;

    /**
     * server name
     */
    private String serverName;

    /**
     * server port
     */
    private int serverPort;

    /**
     * context path
     */
    private String contextPath;

    private RequestContent() {
    }

    public static class Builder {
        private RequestContent content;

        public Builder() {
            content = new RequestContent();
        }

        public Builder withRequestAttributes(Map<String, Object> attributes) {
            content.requestAttributes = attributes;
            return this;
        }

        public Builder withRequestParameters(Map<String, String[]> parameters) {
            content.requestParameters = parameters;
            return this;
        }

        public Builder withSessionAttributes(Map<String, Object> sessionAttrs) {
            content.sessionAttributes = sessionAttrs;
            return this;
        }

        public Builder withRequestParts(List<Part> parts) {
            content.requestParts = parts;
            return this;
        }

        public Builder withCookies(Map<String, String> cookies) {
            content.cookies = cookies;
            return this;
        }

        public Builder withServerName(String serverName) {
            content.serverName = serverName;
            return this;
        }

        public Builder withServerPort(int serverPort) {
            content.serverPort = serverPort;
            return this;
        }

        public Builder withContextPath(String contextPath) {
            content.contextPath = contextPath;
            return this;
        }

        public Builder fromRequest(HttpServletRequest request) throws IOException, ServletException {
            content.requestAttributes = transferRequestAttributes(request);
            content.requestParameters = transferRequestParameters(request);
            content.sessionAttributes = transferSessionAttributes(request);
            content.requestParts = transferRequestParts(request);
            content.cookies = transferCookies(request);
            content.serverName = request.getServerName();
            content.serverPort = request.getServerPort();
            content.contextPath = request.getContextPath();
            return this;
        }

        private static Map<String, Object> transferRequestAttributes(HttpServletRequest request) {
            Map<String, Object> result = new HashMap<>();
            Enumeration<String> requestAttributeNames = request.getAttributeNames();
            while (requestAttributeNames.hasMoreElements()) {
                String attributeName = requestAttributeNames.nextElement();
                result.put(attributeName, request.getAttribute(attributeName));
            }
            return result;
        }

        private static Map<String, String[]> transferRequestParameters(HttpServletRequest request) {
            return new HashMap<>(request.getParameterMap());
        }

        private static Map<String, Object> transferSessionAttributes(HttpServletRequest request) {
            Map<String, Object> result = new HashMap<>();
            HttpSession currentSession = request.getSession(true);
            Enumeration<String> sessionAttributeNames = currentSession.getAttributeNames();
            while (sessionAttributeNames.hasMoreElements()) {
                String sessionAttributeName = sessionAttributeNames.nextElement();
                result.put(sessionAttributeName, currentSession.getAttribute(sessionAttributeName));
            }
            return result;
        }

        private static List<Part> transferRequestParts(HttpServletRequest request) throws IOException, ServletException {
            List<Part> result = new ArrayList<>();
            if (request.getContentType() != null && request.getContentType().toLowerCase().contains("multipart/form-data")) {
                result.addAll(request.getParts());
            }
            return result;
        }

        private static Map<String, String> transferCookies(HttpServletRequest request) {
            Map<String, String> result = new HashMap<>();
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length != 0) {
                Arrays.stream(cookies).forEach(cookie -> result.put(cookie.getName(), cookie.getValue()));
            }
            return result;
        }

        public RequestContent build() {
            return content;
        }
    }

    public String getServerName() {
        return serverName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getContextPath() {
        return contextPath;
    }

    public List<Part> getRequestParts() {
        return requestParts;
    }

    public Optional<Part> getPart(String partName) {
        return requestParts.stream().filter(part -> partName.equals(part.getName())).findFirst();
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Object getRequestAttribute(String key) {
        return requestAttributes.get(key);
    }

    public String[] getRequestParameter(String key) {
        return requestParameters.get(key);
    }

    public Object getSessionAttribute(String key) {
        return sessionAttributes.get(key);
    }

    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public Map<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

}
