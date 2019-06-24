package by.platonov.music.command;

import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;
import lombok.extern.log4j.Log4j2;

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
        String email = content.getRequestParameter(RequestConstant.EMAIL)[0];
        String hash = content.getRequestParameter(RequestConstant.HASH)[0];

        service = new UserService();

        try {
            return service.activate(email, hash) ?
                    new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.LOGIN_PAGE) :
                    new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
        } catch (ServiceException e) {
            log.error("Service provide an exception for activation command ", e);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.INFORMATION_PAGE,
                    Map.of("process", "account activation"));
        }
    }
}
