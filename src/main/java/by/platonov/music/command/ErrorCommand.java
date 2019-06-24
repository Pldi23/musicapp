package by.platonov.music.command;

import by.platonov.music.command.page.PageConstant;

import java.util.Map;

/**
 * @author dzmitryplatonov on 2019-06-23.
 * @version 0.0.1
 */
public class ErrorCommand implements Command {

    private Exception exception;

    @Override
    public CommandResult execute(RequestContent content) {
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE,
                Map.of("exception", exception));
    }
}
