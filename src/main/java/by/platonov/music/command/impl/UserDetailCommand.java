package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.entity.Payment;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * to view details about the {@link User}
 *
 * @author Dzmitry Platonov on 2019-07-22.
 * @version 0.0.1
 */
@Log4j2
public class UserDetailCommand implements Command {

    private UserService userService;

    public UserDetailCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.USER_PAGE if user was not found
     * forward to {@link PageConstant}.USER_PAGE if user was found
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        String userLogin = content.getRequestParameter(RequestConstant.ID)[0];
        User user;
        Set<Payment> payments;
        try {
            List<User> users = userService.searchUserByLogin(userLogin);
            if (users.isEmpty()) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_PAGE,
                        Map.of(RequestConstant.PROCESS, MessageManager.getMessage("message.entity.not.available",
                                (String) content.getSessionAttribute(RequestConstant.LOCALE))));
            }
            payments = userService.getUserPayments(userLogin);
            user = users.get(0);
            user.setPayments(payments);
        } catch (ServiceException e) {
            log.error("command could't provide user details", e);
            return new ErrorCommand(e).execute(content);
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_PAGE,
                Map.of(RequestConstant.ENTITY, user));
    }
}
