package by.platonov.music.command;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class RequestContent {

    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;
    private Map<String, String> cookies;
    private List<Part> requestParts;
    private String serverName;
    private int serverPort;
    private String contextPath;

    private RequestContent() {
    }


    public static RequestContent createWithAttributes(HttpServletRequest request) throws IOException, ServletException {
        RequestContent content = new RequestContent();
        content.setRequestAttributes(request);
        content.setRequestParameters(request);
        content.setSessionAttributes(request);
        content.setRequestParts(request);
        content.setServerName(request);
        content.setServerPort(request);
        content.setContextPath(request);
        content.setCookies(request);
        return content;
    }

    private void setRequestAttributes(HttpServletRequest request) {
        requestAttributes = new HashMap<>();
        Enumeration<String> requestAttributeNames = request.getAttributeNames();
        while (requestAttributeNames.hasMoreElements()) {
            String attributeName = requestAttributeNames.nextElement();
            requestAttributes.put(attributeName, request.getAttribute(attributeName));
        }
    }

    private void setRequestParameters(HttpServletRequest request) {
        requestParameters = new HashMap<>();
        requestParameters.putAll(request.getParameterMap());
    }

    private void setSessionAttributes(HttpServletRequest request) {
        sessionAttributes = new HashMap<>();
        HttpSession currentSession = request.getSession(true);
        Enumeration<String> sessionAttributeNames = currentSession.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String sessionAttributeName = sessionAttributeNames.nextElement();
            sessionAttributes.put(sessionAttributeName, currentSession.getAttribute(sessionAttributeName));
        }
    }

    private void setRequestParts(HttpServletRequest request) throws IOException, ServletException {
        requestParts = new ArrayList<>();
        if (request.getContentType() != null && request.getContentType().toLowerCase().contains("multipart/form-data")) {
            requestParts.addAll(request.getParts());
        }
    }

    private void setCookies(HttpServletRequest request) {
        cookies = new HashMap<>();
        Arrays.stream(request.getCookies()).forEach(cookie -> cookies.put(cookie.getName(), cookie.getValue()));
    }

    private void setServerName(HttpServletRequest request) {
        serverName = request.getServerName();
    }

    private void setServerPort(HttpServletRequest request) {
        serverPort = request.getServerPort();
    }

    private void setContextPath(HttpServletRequest request) {
        contextPath = request.getContextPath();
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
