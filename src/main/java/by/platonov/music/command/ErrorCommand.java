package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author dzmitryplatonov on 2019-06-23.
 * @version 0.0.1
 */
@EqualsAndHashCode
public class ErrorCommand implements Command {

    private String errorMessage;

    public ErrorCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorCommand() {
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE,
                Map.of(), Map.of(RequestConstant.COMMAND_MESSAGE_FACTORY_ATTRIBUTE, errorMessage));
    }
}
