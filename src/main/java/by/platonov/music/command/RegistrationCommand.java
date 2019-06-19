package by.platonov.music.command;

import by.platonov.music.command.validator.*;
import by.platonov.music.entity.Gender;
import by.platonov.music.entity.User;
import by.platonov.music.exception.RepositoryException;
import by.platonov.music.service.UserService;
import com.lambdaworks.crypto.SCryptUtil;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
@Log4j2
public class RegistrationCommand implements Command {

    private UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult;
        Set<Violation> violations =
                new LoginValidator(
                        new PasswordValidator(
                                new BirthDateValidator(
                                        new FirstnameValidator(
                                                new LastnameValidator(
                                                        new EmailValidator(null)))))).apply(content);
        if (violations.isEmpty()) {
            String login = content.getRequestParameter(RequestConstant.LOGIN)[0];
            String password = content.getRequestParameter(RequestConstant.PASSWORD)[0];
            String firstname = content.getRequestParameter(RequestConstant.FIRSTNAME)[0];
            String lastname = content.getRequestParameter(RequestConstant.LASTNAME)[0];
            String birthdate = content.getRequestParameter(RequestConstant.BIRTHDATE)[0];
            String email = content.getRequestParameter(RequestConstant.EMAIL)[0];
            String gender = content.getRequestParameter(RequestConstant.GENDER)[0];

            User user = User.builder()
                    .login(login)
                    .password(SCryptUtil.scrypt(password, 16, 16, 16))
                    .admin(false)
                    .firstname(firstname)
                    .lastname(lastname)
                    .email(email)
                    .birthDate(LocalDate.parse(birthdate))
                    .gender(Gender.valueOf(gender.toUpperCase()))
                    .playlists(new HashSet<>())
                    .build();

            boolean result;
            try {
                result = userService.register(user);
            } catch (RepositoryException e) {
                log.error("Broken repository", e);
                return new CommandResult(CommandResult.ResponseType.REDIRECT, "error.jsp");
            }
            commandResult = result ? new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/main.jsp",
                    Map.of("user", user.getFirstname())) :
                    new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/registration.jsp",
                            Map.of("errorRegistrationFormMessage", "User: " + user.getLogin() + " already exists"));
        } else {
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, "/jsp/registration.jsp",
                    Map.of("errorRegistrationFormMessage", violations));
        }
        return commandResult;
    }
}
