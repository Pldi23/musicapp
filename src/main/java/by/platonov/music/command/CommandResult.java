package by.platonov.music.command;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class CommandResult {
    public enum ResponseType {
        FORWARD,
        REDIRECT
    }

    private ResponseType responseType;
    private String page;
    private Map<String, Object> attributes;
    private Map<String, Object> sessionAttributes;
    private boolean invalidated;

    public CommandResult(ResponseType responseType, String page, Map<String, Object> attributes, Map<String, Object> sessionAttributes) {
        this.responseType = responseType;
        this.page = page;
        this.attributes = attributes;
        this.sessionAttributes = sessionAttributes;
    }

    public CommandResult(ResponseType responseType, String page, Map<String, Object> attributes) {
        this.responseType = responseType;
        this.page = page;
        this.attributes = attributes;
        this.sessionAttributes = new HashMap<>();
    }

    public CommandResult(ResponseType responseType, String page) {
        this.responseType = responseType;
        this.page = page;
        this.attributes = new HashMap<>();
        this.sessionAttributes = new HashMap<>();
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getPage() {
        return page;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }
}
