package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * to forward {@link by.platonov.music.entity.User} to {@link PageConstant}.ERROR_REDIRECT_PAGE if some kind of
 * {@link Exception} have been caught
 * @author dzmitryplatonov on 2019-06-23.
 * @version 0.0.1
 */
@EqualsAndHashCode(exclude = "throwable")
public class ErrorCommand implements Command {

    private Throwable throwable;

    public ErrorCommand(Throwable throwable) {
        this.throwable = throwable;
    }

    public ErrorCommand() {
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that forward to {@link PageConstant}.ERROR_REDIRECT_PAGE with
     * {@link Throwable} in attributes
     */
    @Override
    public CommandResult execute(RequestContent content) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(RequestConstant.THROWABLE, throwable);
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ERROR_REDIRECT_PAGE, attributes);
    }
}
