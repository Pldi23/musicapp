package by.platonov.music.command;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;
import by.platonov.music.validator.PasswordValidator;
import by.platonov.music.validator.Violation;
import com.lambdaworks.crypto.SCryptUtil;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Set;

import static by.platonov.music.command.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-15.
 * @version 0.0.1
 */
@Log4j2
public class ChangePasswordCommand implements Command {

    private UserService userService;

    public ChangePasswordCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContent content) {

        Set<Violation> violations =
                new PasswordValidator(PASSWORD_OLD,
                        new PasswordValidator(PASSWORD_NEW,
                                new PasswordValidator(PASSWORD_NEW_CHECK, null))).apply(content);

        String result;

        if (violations.isEmpty()) {
            String oldPassword = content.getRequestParameter(PASSWORD_OLD)[0];
            User user = (User) content.getSessionAttribute(USER);

            String locale = (String) content.getSessionAttribute(LOCALE);
            if (!SCryptUtil.check(oldPassword, user.getPassword())) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PROFILE_PAGE,
                        Map.of(PROCESS, MessageManager.getMessage("login.failed", locale)));
            }

            String newpassword = content.getRequestParameter(PASSWORD_NEW)[0];
            String newpasswordCheck = content.getRequestParameter(PASSWORD_NEW_CHECK)[0];
            if (!newpassword.equals(newpasswordCheck)) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PROFILE_PAGE,
                        Map.of(PROCESS, MessageManager.getMessage("message.password.equals", locale)));
            }

            user.setPassword(SCryptUtil.scrypt(newpassword, 16, 16, 16));
            try {
                userService.updateUser(user);
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PROFILE_PAGE,
                        Map.of(PROCESS, MessageManager.getMessage("label.updated", locale)),
                        Map.of(USER, user));
            } catch (ServiceException e) {
                log.error("couldn't update password", e);
                return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
            }

        } else {
            result = "\u2718";
            for (Violation violation : violations) {
                result = result.concat(violation.getMessage());
            }
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PROFILE_PAGE,
                    Map.of(PROCESS, result));
        }
    }
}
