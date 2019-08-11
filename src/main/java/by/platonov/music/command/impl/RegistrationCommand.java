package by.platonov.music.command.impl;

import static by.platonov.music.constant.RequestConstant.*;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.validator.*;
import by.platonov.music.entity.User;
import by.platonov.music.service.UserService;
import by.platonov.music.util.HashGenerator;
import by.platonov.music.util.VerificationMailSender;
import com.lambdaworks.crypto.SCryptUtil;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;

/**
 * to register {@link User} in application
 *
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
@Log4j2
public class RegistrationCommand implements Command {

    private UserService userService;
    private boolean isAdmin;

    public RegistrationCommand(UserService userService, boolean isAdmin) {
        this.userService = userService;
        this.isAdmin = isAdmin;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.REGISTER_ADMIN_PAGE or REGISTRATION_PAGE according to user's role,
     * with violations if it was found
     * forward to {@link PageConstant}.VERIFICATION_PAGE if command was executed correctly
     * forward to {@link PageConstant}.REGISTER_ADMIN_PAGE or REGISTRATION_PAGE according to user's role,
     * with message-exists if user already exist
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
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

        String targetPage = isAdmin ? PageConstant.REGISTER_ADMIN_PAGE : PageConstant.REGISTRATION_PAGE;

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
                        .admin(isAdmin)
                        .firstname(firstname)
                        .lastname(lastname)
                        .email(email)
                        .birthDate(LocalDate.parse(birthdate))
                        .gender(User.Gender.valueOf(gender.toUpperCase()))
                        .playlists(new HashSet<>())
                        .active(false)
                        .verificationUuid(hash)
                        .photoPath(ResourceBundle.getBundle("app").getString("default.ava"))
                        .paidPeriod(isAdmin ? LocalDateTime.of(2050, 12, 31,12, 0) :
                                LocalDateTime.now().plusMonths(Long.parseLong(ResourceBundle.getBundle("payment")
                                        .getString("trial.period"))))
                        .build();

                if (userService.register(user)) {
                    VerificationMailSender task = new VerificationMailSender(content.getServerName(), content.getServerPort(),
                            content.getContextPath(), email, hash);
                    Executors.newSingleThreadExecutor().submit(task);
                    commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.VERIFICATION_PAGE,
                            Map.of(HASH, user.getVerificationUuid(), EMAIL, user.getEmail()));
                } else {
                        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, targetPage,
                                Map.of(PROCESS, MessageManager.getMessage("message.user",
                                        (String) content.getSessionAttribute(LOCALE)) + user.getLogin() + " " +
                                        MessageManager.getMessage("exist", (String) content.getSessionAttribute(LOCALE))));

                }
            } catch (ServiceException e) {
                log.error("Service provide an exception for registration command ", e);
                return new ErrorCommand(e).execute(content);
            }
        } else {
            log.info("Registration failed because of validator violation");
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, targetPage,
                    Map.of(VALIDATOR_RESULT, violations));
        }
        return commandResult;
    }
}
