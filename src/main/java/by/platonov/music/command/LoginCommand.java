package by.platonov.music.command;

import by.platonov.music.command.validator.*;
import by.platonov.music.controller.page.PageConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.service.UserService;
import com.lambdaworks.crypto.SCryptUtil;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author dzmitryplatonov on 2019-06-18.
 * @version 0.0.1
 */
@Log4j2
public class LoginCommand implements Command {

    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult;
        Set<Violation> violations = new LoginValidator(new PasswordValidator(null)).apply(content);
        if (violations.isEmpty()) {
            String login = content.getRequestParameter(RequestConstant.LOGIN)[0];
            String password = content.getRequestParameter(RequestConstant.PASSWORD)[0];
            List<User> users;
            try {
                users = userService.login(login);
            } catch (RepositoryException e) {
                log.error("Broken repository", e);
                return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
            }
            if (!users.isEmpty()
                    && SCryptUtil.check(password, users.get(0).getPassword())
                    && users.get(0).isActive()
                    && !users.get(0).isAdmin()) {
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.MAIN_PAGE,
                        Map.of("user", users.get(0).getFirstname()));
            } else if (!users.isEmpty()
                    && SCryptUtil.check(password, users.get(0).getPassword())
                    && users.get(0).isActive()
                    && users.get(0).isAdmin()) {
//                HttpSession session = content.getSessionAttributes();
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ADMIN_PAGE,
                        Map.of("adminName", users.get(0).getFirstname()));
            } else {
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.LOGIN_PAGE,
                        Map.of("errorLoginPassMessage", "Incorrect login or password"));
            }
            return commandResult;
        } else {
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.LOGIN_PAGE,
                    Map.of("validatorMessage", violations));
        }
        return commandResult;
    }
}
