package by.platonov.music.command;

import by.platonov.music.service.UserService;
import by.platonov.music.validator.BirthDateValidator;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

import static by.platonov.music.command.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-16.
 * @version 0.0.1
 */
@Log4j2
public class UpdateBirthDateCommand implements Command {

    private UserService userService;
    private CommandHandler commandHandler;

    public UpdateBirthDateCommand(UserService userService, CommandHandler commandHandler) {
        this.userService = userService;
        this.commandHandler = commandHandler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return commandHandler.update(userService, content, BIRTHDATE, new BirthDateValidator(null),
                (user, parameter) -> user.setBirthDate(LocalDate.parse(parameter)));

//        Set<Violation> violations = new BirthDateValidator(null).apply(content);
//        String result;
//
//        if (violations.isEmpty()) {
//            String birthDate = content.getRequestParameter(BIRTHDATE)[0];
//            User user = (User) content.getSessionAttribute(USER);
//            user.setBirthDate(LocalDate.parse(birthDate));
//            try {
//                String locale = (String) content.getSessionAttribute(LOCALE);
//                result = userService.updateUser(user) ? MessageManager.getMessage("label.updated", locale) :
//                        MessageManager.getMessage("failed", locale);
//                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PROFILE_PAGE,
//                        Map.of(PROCESS, result), Map.of(USER, user));
//            } catch (ServiceException e) {
//                log.error("couldn't update birth date", e);
//                return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
//            }
//        } else {
//            result = "\u2718";
//            for (Violation violation : violations) {
//                result = result.concat(violation.getMessage());
//            }
//            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PROFILE_PAGE,
//                    Map.of(PROCESS, result));
//        }
    }
}
