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

    public CommandResult(ResponseType responseType, String page, Map<String, Object> attributes) {
        this.responseType = responseType;
        this.page = page;
        this.attributes = attributes;
    }

    public CommandResult(ResponseType responseType, String page) {
        this.responseType = responseType;
        this.page = page;
        this.attributes = new HashMap<>();
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

}
