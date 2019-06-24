package by.platonov.music.command;

/**
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
public class ToRegistrationCommand implements Command {
    @Override
    public CommandResult execute(RequestContent content) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.REGISTRATION_PAGE);
    }
}
