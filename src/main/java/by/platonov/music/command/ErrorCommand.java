package by.platonov.music.command;

import java.util.Map;

/**
 * @author dzmitryplatonov on 2019-06-23.
 * @version 0.0.1
 */
public class ErrorCommand implements Command {

    private Exception exception;

    public ErrorCommand(Exception exception) {
        this.exception = exception;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE,
                Map.of("exception", exception));
    }
}
