package by.platonov.music.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class RequestContent {

    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;

    public RequestContent(HttpServletRequest request) {
        requestParameters = request.getParameterMap();
        sessionAttributes = new HashMap<>();
        requestAttributes = new HashMap<>();

        Enumeration<String> requestAttributeNames = request.getAttributeNames();
        while (requestAttributeNames.hasMoreElements()) {
            String attributeName = requestAttributeNames.nextElement();
            requestAttributes.put(attributeName, request.getAttribute(attributeName));
        }

        HttpSession currentSession = request.getSession();
        Enumeration<String> sessionAttributeNames = currentSession.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String sessionAttributeName = sessionAttributeNames.nextElement();
            sessionAttributes.put(sessionAttributeName, currentSession.getAttribute(sessionAttributeName));
        }
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
