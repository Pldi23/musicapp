package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;
import by.platonov.music.validator.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import static by.platonov.music.command.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
public class FilterUserCommand implements Command {

    private UserService userService;

    public FilterUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContent content) {

//        Set<Violation> violations = new LoginValidator()
        Set<Violation> violations =
                new TrackNameValidator(true,
                        new SingerValidator(true,
                                new FilterReleaseDateValidator(null))).apply(content);

        if (violations.isEmpty()) {

            List<User> users;
            String login = content.getRequestParameter(LOGIN)[0];
            String role = !content.getRequestParameter(ROLE)[0].isBlank() ? content.getRequestParameter(ROLE)[0] : "";
            String firstname = content.getRequestParameter(FIRSTNAME)[0];
            String lastname = content.getRequestParameter(LASTNAME)[0];
            String email = content.getRequestParameter(EMAIL)[0];
            String birthdateFrom = !content.getRequestParameter(BIRTH_FROM)[0].isBlank() ?
                    content.getRequestParameter(BIRTH_FROM)[0] : LocalDate.EPOCH.toString();
            String birthdateTo = !content.getRequestParameter(BIRTH_TO)[0].isBlank() ?
                    content.getRequestParameter(BIRTH_TO)[0] : LocalDate.now().toString();
            String registrationFrom = !content.getRequestParameter(REGISTRATION_FROM)[0].isBlank() ?
                    content.getRequestParameter(REGISTRATION_FROM)[0] : LocalDate.EPOCH.toString();
            String regisrationTo = !content.getRequestParameter(REGISTRATION_TO)[0].isBlank() ?
                    content.getRequestParameter(REGISTRATION_TO)[0] : LocalDate.now().toString();

            int limit = Integer.parseInt(ResourceBundle.getBundle("app").getString("app.list.limit"));
            boolean nextUnavailable = false;
            boolean previousUnavailable = false;
            boolean next = !content.getRequestParameters().containsKey(DIRECTION)
                    || content.getRequestParameter(DIRECTION)[0].equals(NEXT);
            long offset;
            try {
                long size = userService.searchUserByFilter(login, role, firstname, lastname, User.Gender.valueOf(gender.toUpperCase()),
                        email, LocalDate.parse(birthdateFrom), LocalDate.parse(birthdateTo), LocalDate.parse(registrationFrom),
                        LocalDate.parse(regisrationTo), Integer.MAX_VALUE, 0).size();
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
                tracks = commonService.searchTrackByFilter(trackName, genreName, LocalDate.parse(fromDate),
                        LocalDate.parse(toDate), singerName, limit, offset);
            } catch (ServiceException e) {
                log.error("command could't provide track list", e);
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ERROR_REDIRECT_PAGE);
            }
            log.debug("command provided tracks: " + tracks);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.FILTER_PAGE,
                    Map.of(ENTITIES, tracks, PREVIOUS_UNAVAILABLE, previousUnavailable,
                            NEXT_UNAVAILABLE, nextUnavailable, TRACKNAME, trackName, GENRE, genreName, RELEASE_FROM, fromDate,
                            RELEASE_TO, toDate, SINGER, singerName),
                    Map.of(NEXT_OFFSET, offset + limit, PREVIOUS_OFFSET, offset - limit));
        } else {
            String result = "\u2718" + violations.stream().map(Violation::getMessage).collect(Collectors.joining("\u2718"));
            log.info("filter failed because of validator violation");
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.FILTER_PAGE,
                    Map.of(PROCESS, result));
        }
    }
        return null;
}
}
