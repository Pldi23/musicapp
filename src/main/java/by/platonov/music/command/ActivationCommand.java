package by.platonov.music.command;

import by.platonov.music.controller.page.PageConstant;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.service.UserService;
import lombok.extern.log4j.Log4j2;

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
        CommandResult commandResult;
        String email = content.getRequestParameter(RequestConstant.EMAIL)[0];
        String hash = content.getRequestParameter(RequestConstant.HASH)[0];

        service = new UserService();
        try {
            commandResult = service.activate(email, hash) ?
                    new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.LOGIN_PAGE) :
                    new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
        } catch (RepositoryException e) {
            log.error("Broken repository", e);
            commandResult = new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
        }
        return commandResult;
    }
}