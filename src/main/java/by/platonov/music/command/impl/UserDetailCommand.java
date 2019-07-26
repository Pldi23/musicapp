package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.command.impl.ErrorCommand;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

/**
 * music-app
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

    @Override
    public CommandResult execute(RequestContent content) {
        String userLogin = content.getRequestParameter(RequestConstant.ID)[0];
        User user;
        try {
            List<User> users = userService.searchUserByLogin(userLogin);
            if (users.isEmpty()) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_PAGE,
                        Map.of(RequestConstant.PROCESS, MessageManager.getMessage("message.entity.not.available",
                                (String) content.getSessionAttribute(RequestConstant.LOCALE))));
            }
            user = users.get(0);
        } catch (ServiceException e) {
            log.error("command could't provide user details", e);
            return new ErrorCommand(e).execute(content);
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_PAGE,
                Map.of(RequestConstant.ENTITY, user));
    }
}
