package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.handler.CommandHandler;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.User;
import by.platonov.music.entity.filter.UserFilter;
import by.platonov.music.service.UserService;
import by.platonov.music.validator.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.*;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
@Log4j2
public class FilterUserCommand implements Command {

    private UserService userService;
    private CommandHandler<User> handler;

    public FilterUserCommand(UserService userService, CommandHandler<User> handler) {
        this.userService = userService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {

        Set<Violation> violations =
                new FilterFieldValidator(LOGIN,
                        new FilterFieldValidator(FIRSTNAME,
                                new FilterFieldValidator(LASTNAME,
                                        new FilterFieldValidator(EMAIL,
                                                new FilterDateValidator(BIRTH_FROM, BIRTH_TO,
                                                        new FilterDateValidator(REGISTRATION_FROM, REGISTRATION_TO, null))))))
                        .apply(content);

        if (violations.isEmpty()) {

            UserFilter userFilter = UserFilter.builder()
                    .login(content.getRequestParameter(LOGIN)[0])
                    .role(content.getRequestParameter(ROLE)[0].isBlank() ? null :
                            Boolean.parseBoolean(content.getRequestParameter(ROLE)[0]))
                    .firstname(content.getRequestParameter(FIRSTNAME)[0])
                    .lastname(content.getRequestParameter(LASTNAME)[0])
                    .email(content.getRequestParameter(EMAIL)[0])
                    .birthdateFrom(!content.getRequestParameter(BIRTH_FROM)[0].isBlank() ?
                            LocalDate.parse(content.getRequestParameter(BIRTH_FROM)[0]) : LocalDate.of(1900, 1, 1))
                    .birthdateTo(!content.getRequestParameter(BIRTH_TO)[0].isBlank() ?
                            LocalDate.parse(content.getRequestParameter(BIRTH_TO)[0]) : LocalDate.now())
                    .registrationFrom(!content.getRequestParameter(REGISTRATION_FROM)[0].isBlank() ?
                            LocalDate.parse(content.getRequestParameter(REGISTRATION_FROM)[0]) : LocalDate.EPOCH)
                    .regisrationTo(!content.getRequestParameter(REGISTRATION_TO)[0].isBlank() ?
                            LocalDate.parse(content.getRequestParameter(REGISTRATION_TO)[0]) : LocalDate.now())
                    .build();

            log.debug("role: " + userFilter.getRole());

            return handler.filter(content, PageConstant.USER_LIBRARY_PAGE,
                    ((limit, offset) -> userService.searchUserByFilter(userFilter, limit, offset)),
                    Map.of(FILTER, userFilter, PAGE_COMMAND, FILTER_USER));

        } else {
            log.info("filter failed because of validator violation");
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_LIBRARY_PAGE,
                    Map.of(VALIDATOR_RESULT, violations));
        }
    }
}
