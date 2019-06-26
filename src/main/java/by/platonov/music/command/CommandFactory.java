package by.platonov.music.command;

import static by.platonov.music.command.constant.RequestConstant.*;

import by.platonov.music.command.constant.CommandMessage;
import lombok.extern.log4j.Log4j2;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
@Log4j2
public class CommandFactory {
    private static final CommandFactory instance = new CommandFactory();

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(RequestContent content) {

        String command = content.getRequestParameters().containsKey(COMMAND) ?
                content.getRequestParameter(COMMAND)[0] : ERROR;

        try {
            return CommandType.valueOf(command.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            log.error("Enum Command Type not present for " + command, e);
            return new ErrorCommand(CommandMessage.COMMAND_FAILED_MESSAGE);
        }
    }
}
