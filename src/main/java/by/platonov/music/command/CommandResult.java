package by.platonov.music.command;

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

    public CommandResult() {
    }

    public CommandResult(ResponseType responseType, String page) {
        this.responseType = responseType;
        this.page = page;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
