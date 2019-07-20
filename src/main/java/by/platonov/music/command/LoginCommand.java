package by.platonov.music.command;

import static by.platonov.music.command.constant.RequestConstant.*;
import static by.platonov.music.command.constant.PageConstant.*;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.CommandMessage;
import by.platonov.music.entity.Track;
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
import java.util.stream.Collectors;

/**
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

    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult;

        Set<Violation> violations =
                new LoginValidator(
                        new PasswordValidator(null)).apply(content);
        String result;

        if (violations.isEmpty()) {
            String login = content.getRequestParameter(LOGIN)[0];
            String password = content.getRequestParameter(PASSWORD)[0];
            List<User> users;
            List<Track> tracksSuccessfulLogin;
            List<Track> tracksUnsuccessfullLogin;
            try {
                users = userService.login(login);
                tracksSuccessfulLogin = commonService.getRandomTen();
                tracksUnsuccessfullLogin = commonService.getTracksLastAdded();
            } catch (ServiceException e) {
                log.error("Service provide an exception for login command ", e);
                return new CommandResult(CommandResult.ResponseType.FORWARD, INFORMATION_PAGE,
                        Map.of(PROCESS, CommandMessage.LOGIN_OPERATION_MESSAGE));
            }

            if (!users.isEmpty()
                    && SCryptUtil.check(password, users.get(0).getPassword())
                    && users.get(0).isActive()
                    && !users.get(0).isAdmin()) {

                log.debug(users.get(0) + " logged in as user");
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, MAIN_PAGE,
                        Map.of(TRACKS, tracksSuccessfulLogin), Map.of(USER, users.get(0)));
            } else if (!users.isEmpty()
                    && SCryptUtil.check(password, users.get(0).getPassword())
                    && users.get(0).isActive()
                    && users.get(0).isAdmin()) {

                log.debug(users.get(0) + " logged in as admin");
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, ADMIN_PAGE,
                        Map.of(TRACKS, tracksSuccessfulLogin), Map.of(USER, users.get(0)));
            } else {
                log.debug("login not successful");
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, LOGIN_PAGE,
                        Map.of(ERROR_LOGIN_PASS_ATTRIBUTE, MessageManager.getMessage("login.failed",
                                (String) content.getSessionAttribute(LOCALE)), TRACKS, tracksUnsuccessfullLogin));
            }
            return commandResult;
        } else {
            result = "\u2718" + violations.stream().map(Violation::getMessage).collect(Collectors.joining("\u2718"));
            log.debug("login not successful");
            try {
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, LOGIN_PAGE,
                        Map.of(VALIDATOR_MESSAGE_ATTRIBUTE, result, TRACKS, commonService.getRandomTen()));
            } catch (ServiceException e) {
                log.error("Service provide an exception for login command ", e);
                return new CommandResult(CommandResult.ResponseType.FORWARD, INFORMATION_PAGE,
                        Map.of(PROCESS, CommandMessage.LOGIN_OPERATION_MESSAGE));
            }
        }
        return commandResult;
    }
}
