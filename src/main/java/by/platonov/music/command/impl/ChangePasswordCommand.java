package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;
import by.platonov.music.validator.PasswordValidator;
import by.platonov.music.validator.Violation;
import com.lambdaworks.crypto.SCryptUtil;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * to change {@link User}'s password
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

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that
     * forward to {@link PageConstant}.PROFILE_PAGE with violations if it was found
     * forward to {@link PageConstant}.PROFILE_PAGE with login.failed-message if entered password incorrect
     * forward to {@link PageConstant}.PROFILE_PAGE with message.password.equals if entered password is not equal to re-entered password
     * forward to {@link PageConstant}.PROFILE_PAGE with label.updated-message if command was executed successfully
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {

        Set<Violation> violations =
                new PasswordValidator(PASSWORD_OLD,
                        new PasswordValidator(PASSWORD_NEW,
                                new PasswordValidator(PASSWORD_NEW_CHECK, null))).apply(content);

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
                return new ErrorCommand(e).execute(content);
            }

        } else {
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PROFILE_PAGE,
                    Map.of(VALIDATOR_RESULT, violations));
        }
    }
}
