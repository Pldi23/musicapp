package by.platonov.music.command.impl;

import static by.platonov.music.constant.RequestConstant.*;
import static by.platonov.music.constant.PageConstant.*;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.message.MessageManager;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import by.platonov.music.validator.*;
import by.platonov.music.entity.User;
import by.platonov.music.service.UserService;
import com.lambdaworks.crypto.SCryptUtil;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * to login {@link User} to application
 *
 * @author dzmitryplatonov on 2019-06-18.
 * @version 0.0.1
 */
@Log4j2
@EqualsAndHashCode
public class LoginCommand implements Command {

    private UserService userService;
    private CommonService commonService;

    public LoginCommand(UserService userService, CommonService commonService) {
        this.userService = userService;
        this.commonService = commonService;
    }

    /**
     *
     * @param content content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that
     * forward to {@link PageConstant}.LOGIN_PAGE with violations if it was found
     * forward to {@link PageConstant}.MAIN_PAGE if user is regular user
     * forward to {@link PageConstant}.ADMIN_PAGE if user is admin
     * forward to {@link PageConstant}.LOGIN_PAGE if login and password incorrect
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult;

        Set<Violation> violations =
                new LoginValidator(
                        new PasswordValidator(null)).apply(content);
        try {

            if (violations.isEmpty()) {
                String login = content.getRequestParameter(LOGIN)[0];
                String password = content.getRequestParameter(PASSWORD)[0];
                List<User> users = userService.searchUserByLogin(login);

                if (!users.isEmpty()
                        && SCryptUtil.check(password, users.get(0).getPassword())
                        && users.get(0).isActive()
                        && !users.get(0).isAdmin()) {

                    log.debug(users.get(0) + " logged in as user");
                    commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, MAIN_PAGE,
                            Map.of(TRACKS, commonService.getRandomTen()), Map.of(USER, users.get(0)));
                } else if (!users.isEmpty()
                        && SCryptUtil.check(password, users.get(0).getPassword())
                        && users.get(0).isActive()
                        && users.get(0).isAdmin()) {

                    log.debug(users.get(0) + " logged in as admin");
                    commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, ADMIN_PAGE,
                            Map.of(TRACKS, commonService.getRandomTen()), Map.of(USER, users.get(0)));
                } else {
                    log.debug("login not successful");
                    commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, LOGIN_PAGE,
                            Map.of(ERROR_LOGIN_PASS_ATTRIBUTE, MessageManager.getMessage("login.failed",
                                    (String) content.getSessionAttribute(LOCALE)), TRACKS, commonService.getTracksLastAdded()));
                }
                return commandResult;
            } else {
                log.debug("login not successful");
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, LOGIN_PAGE,
                        Map.of(VALIDATOR_RESULT, violations, TRACKS, commonService.getTracksLastAdded()));
            }
            return commandResult;
        } catch (ServiceException e) {
            log.error("Could not process login command ", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}
