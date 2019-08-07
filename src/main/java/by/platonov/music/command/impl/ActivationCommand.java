package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.message.MessageManager;
import by.platonov.music.service.UserService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import static by.platonov.music.constant.RequestConstant.*;
import static by.platonov.music.constant.PageConstant.*;

/**
 * to activate {@link by.platonov.music.entity.User} when he click on confirmation link in e-mail
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
@Log4j2
public class ActivationCommand implements Command {

    private UserService service;

    public ActivationCommand(UserService service) {
        this.service = service;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that
     * forward to INDEX_PAGE if success
     * redirect to error page if unsuccessful
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        String email = content.getRequestParameter(EMAIL)[0];
        String hash = content.getRequestParameter(HASH)[0];

        try {

            return service.activate(email, hash) ?
                    new CommandResult(CommandResult.ResponseType.FORWARD, INDEX_PAGE, Map.of(PROCESS,
                            MessageManager.getMessage("message.present", (String) content.getSessionAttribute(LOCALE)))) :
                    new CommandResult(CommandResult.ResponseType.REDIRECT, ERROR_REDIRECT_PAGE);
        } catch (ServiceException e) {
            log.error("Service provide an exception for activation command ", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}
