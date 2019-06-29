package by.platonov.music.command;

import by.platonov.music.command.constant.CommandMessage;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;
import lombok.extern.log4j.Log4j2;
import static by.platonov.music.command.constant.RequestConstant.*;
import static by.platonov.music.command.constant.PageConstant.*;

import java.util.Map;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
@Log4j2
public class ActivationCommand implements Command {

    private UserService service;

    public ActivationCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        String email = content.getRequestParameter(EMAIL)[0];
        String hash = content.getRequestParameter(HASH)[0];

        try {
            return service.activate(email, hash) ?
                    new CommandResult(CommandResult.ResponseType.FORWARD, LOGIN_PAGE) :
                    new CommandResult(CommandResult.ResponseType.REDIRECT, ERROR_REDIRECT_PAGE);
        } catch (ServiceException e) {
            log.error("Service provide an exception for activation command ", e);
            return new CommandResult(CommandResult.ResponseType.FORWARD, INFORMATION_PAGE,
                    Map.of(PROCESS, CommandMessage.ACCOUNT_ACTIVATION_MESSAGE));
        }
    }
}
