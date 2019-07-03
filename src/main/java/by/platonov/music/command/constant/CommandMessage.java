package by.platonov.music.command.constant;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
public class CommandMessage {

    private CommandMessage() {
    }

    public static final String SEARCH_REQUEST_MESSAGE = "search request";
    public static final String ACCOUNT_ACTIVATION_MESSAGE = "account activation";
    public static final String LOGIN_OPERATION_MESSAGE = "login operation";
    public static final String COMMAND_FAILED_MESSAGE = "Command not exist or have illegal parameter";
    public static final String ERROR_LOGIN_PASS_MESSAGE = "Incorrect login or password";
    public static final String SUCCESSFULLY_ADDED_MESSAGE = " successfully added";
    public static final String ALREADY_EXIST_MESSAGE = " already exist";
    public static final String SUCCESSFULLY_MESSAGE = "successfully";
    public static final String FAILED_MESSAGE = "failed";
}
