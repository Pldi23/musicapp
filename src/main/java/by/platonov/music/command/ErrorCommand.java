package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

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
