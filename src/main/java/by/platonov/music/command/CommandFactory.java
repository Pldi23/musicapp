package by.platonov.music.command;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class CommandFactory {
    private static final CommandFactory instance = new CommandFactory();

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(RequestContent content) {
        String command = content.getRequestParameter(RequestConstant.COMMAND)[0];
        CommandType type = CommandType.valueOf(command.toUpperCase());
        return type.getCommand();
    }
}
