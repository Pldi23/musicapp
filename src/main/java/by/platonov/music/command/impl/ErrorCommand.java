package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author dzmitryplatonov on 2019-06-23.
 * @version 0.0.1
 */
@EqualsAndHashCode
public class ErrorCommand implements Command {

    private Throwable throwable;

    public ErrorCommand(Throwable throwable) {
        this.throwable = throwable;
    }

    public ErrorCommand() {
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ERROR_REDIRECT_PAGE,
                Map.of(RequestConstant.THROWABLE, throwable));
    }
}
