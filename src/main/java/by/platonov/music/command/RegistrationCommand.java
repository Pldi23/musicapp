package by.platonov.music.command;

import static by.platonov.music.command.constant.RequestConstant.*;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.validator.*;
import by.platonov.music.entity.User;
import by.platonov.music.service.UserService;
import by.platonov.music.util.HashGenerator;
import by.platonov.music.util.VerificationMailSender;
import com.lambdaworks.crypto.SCryptUtil;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Executors;

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
                                                        new EmailValidator(
                                                                new GenderValidator(null))))))).apply(content);

        if (violations.isEmpty()) {
            String login = content.getRequestParameter(LOGIN)[0];
            String password = content.getRequestParameter(PASSWORD)[0];
            String firstname = content.getRequestParameter(FIRSTNAME)[0];
            String lastname = content.getRequestParameter(LASTNAME)[0];
            String birthdate = content.getRequestParameter(BIRTHDATE)[0];
            String email = content.getRequestParameter(EMAIL)[0];
            String gender = content.getRequestParameter(GENDER)[0];

            HashGenerator generator = new HashGenerator();
            String hash = generator.generateHash();

            try {

                User user = User.builder()
                        .login(login)
                        .password(SCryptUtil.scrypt(password, 16, 16, 16))
                        .admin(false)
                        .firstname(firstname)
                        .lastname(lastname)
                        .email(email)
                        .birthDate(LocalDate.parse(birthdate))
                        .gender(User.Gender.valueOf(gender.toUpperCase()))
                        .playlists(new HashSet<>())
                        .active(false)
                        .verificationUuid(hash)
                        .photoPath(ResourceBundle.getBundle("app").getString("default.ava"))
                        .build();

                if (userService.register(user)) {
                    VerificationMailSender task = new VerificationMailSender(content.getServerName(), content.getServerPort(),
                            content.getContextPath(), email, hash);
                    Executors.newSingleThreadExecutor().submit(task);
                    commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.VERIFICATION_PAGE,
                            Map.of(HASH, user.getVerificationUuid(), EMAIL, user.getEmail()));
                } else {
                    commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.REGISTRATION_PAGE,
                            Map.of(VALIDATOR_RESULT,
                                    Set.of(new Violation(MessageManager.getMessage("message.user", (String) content.getSessionAttribute(LOCALE)) +
                                            user.getLogin() + MessageManager.getMessage("exist", (String) content.getSessionAttribute(LOCALE))))));
                }
            } catch (ServiceException e) {
                log.error("Service provide an exception for registration command ", e);
                commandResult = new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
            }
        } else {
            log.info("Registration failed because of validator violation");
            String result = "\u2718";
            for (Violation violation : violations) {
                result = result.concat(violation.getMessage());
            }
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.REGISTRATION_PAGE,
                    Map.of(VALIDATOR_RESULT, result));
        }
        return commandResult;
    }
}
