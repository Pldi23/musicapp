package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
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

    public FilterUserCommand(UserService userService) {
        this.userService = userService;
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

            List<User> users;
            String login = content.getRequestParameter(LOGIN)[0];
            Boolean role = !content.getRequestParameter(ROLE)[0].isBlank() ?
                    Boolean.parseBoolean(content.getRequestParameter(ROLE)[0]) : null;
            String firstname = content.getRequestParameter(FIRSTNAME)[0];
            String lastname = content.getRequestParameter(LASTNAME)[0];
            String email = content.getRequestParameter(EMAIL)[0];
            LocalDate birthdateFrom = !content.getRequestParameter(BIRTH_FROM)[0].isBlank() ?
                    LocalDate.parse(content.getRequestParameter(BIRTH_FROM)[0]) : LocalDate.of(1900, 1, 1);
            LocalDate birthdateTo = !content.getRequestParameter(BIRTH_TO)[0].isBlank() ?
                    LocalDate.parse(content.getRequestParameter(BIRTH_TO)[0]) : LocalDate.now();
            LocalDate registrationFrom = !content.getRequestParameter(REGISTRATION_FROM)[0].isBlank() ?
                    LocalDate.parse(content.getRequestParameter(REGISTRATION_FROM)[0]) : LocalDate.EPOCH;
            LocalDate regisrationTo = !content.getRequestParameter(REGISTRATION_TO)[0].isBlank() ?
                    LocalDate.parse(content.getRequestParameter(REGISTRATION_TO)[0]) : LocalDate.now();

            int limit = Integer.parseInt(ResourceBundle.getBundle("app").getString("app.list.limit"));
            boolean nextUnavailable = false;
            boolean previousUnavailable = false;
            boolean next = !content.getRequestParameters().containsKey(DIRECTION)
                    || content.getRequestParameter(DIRECTION)[0].equals(NEXT);
            long offset;
            Map<String, Object> attributes = new HashMap<>();
            try {
                long size = userService.searchUserByFilter(login, role, firstname, lastname, email, birthdateFrom,
                        birthdateTo, registrationFrom, regisrationTo, Integer.MAX_VALUE, 0).size();

                if (content.getRequestParameters().containsKey(OFFSET)) {
                    offset = 0;
                    previousUnavailable = true;
                    nextUnavailable = size <= limit;
                } else {
                    if (next) {
                        offset = (long) content.getSessionAttribute(NEXT_OFFSET);
                        nextUnavailable = offset + limit >= size;
                    } else {
                        offset = (long) content.getSessionAttribute(PREVIOUS_OFFSET);
                        previousUnavailable = offset - limit < 0;
                    }
                }
                users = userService.searchUserByFilter(login, role, firstname, lastname, email, birthdateFrom,
                        birthdateTo, registrationFrom, regisrationTo, limit, offset);
                attributes.put(ENTITIES, users);
                attributes.put(PREVIOUS_UNAVAILABLE, previousUnavailable);
                attributes.put(NEXT_UNAVAILABLE, nextUnavailable);
                attributes.put(LOGIN, login);
                attributes.put(FIRSTNAME, firstname);
                attributes.put(LASTNAME, lastname);
                attributes.put(BIRTH_FROM, birthdateFrom);
                attributes.put(BIRTH_TO, birthdateTo);
                attributes.put(REGISTRATION_FROM, registrationFrom);
                attributes.put(REGISTRATION_TO, regisrationTo);
                attributes.put(EMAIL, email);
                attributes.put(PAGE_COMMAND, FILTER_USER);
            } catch (ServiceException e) {
                log.error("command could't provide users list", e);
                return new ErrorCommand(e).execute(content);
            }
            log.debug("command provided users: " + users);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_LIBRARY_PAGE, attributes,
                    Map.of(NEXT_OFFSET, offset + limit, PREVIOUS_OFFSET, offset - limit));
        } else {
            log.info("filter failed because of validator violation");
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_LIBRARY_PAGE,
                    Map.of(VALIDATOR_RESULT, violations));
        }
    }
}
