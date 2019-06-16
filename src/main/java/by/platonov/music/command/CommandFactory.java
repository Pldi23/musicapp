package by.platonov.music.command;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public final class CommandFactory {
    private static final CommandFactory instance = new CommandFactory();

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(HttpServletRequest request) {
        String command = request.getParameter("command");
        CommandType type = CommandType.valueOf(command.toUpperCase());
        return type.getCommand();
    }
}
