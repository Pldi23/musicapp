package by.platonov.music.command;

import by.platonov.music.command.impl.ErrorCommand;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import by.platonov.music.service.UserService;
import by.platonov.music.validator.AbstractValidator;
import by.platonov.music.validator.Violation;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-06.
 * @version 0.0.1
 */
@Log4j2
public class CommandHandler<T> {

//    public CommandResult sorte(RequestContent content, String sortOrderMarker, String page,
//                                CountCommandExecutor countCommandExecutor, SortCommandExecutor<T> sortCommandExecutor) {
//        List<T> entities;
//        boolean sortOrder = !content.getSessionAttributes().containsKey(sortOrderMarker) ||
//                (boolean) content.getSessionAttribute(sortOrderMarker);
//        int limit = Integer.parseInt(ResourceBundle.getBundle("app").getString("app.list.limit"));
//        boolean nextUnavailable = false;
//        boolean previousUnavailable = false;
//        boolean next = !content.getRequestParameters().containsKey(DIRECTION)
//                || content.getRequestParameter(DIRECTION)[0].equals(NEXT);
//        long offset;
//
//        try {
//            long size = countCommandExecutor.count();
//
//            if (content.getRequestParameters().containsKey(OFFSET)) {
//                offset = 0;
//                previousUnavailable = true;
//                nextUnavailable = size <= limit;
//            } else {
//                if (next) {
//                    offset = (long) content.getSessionAttribute(NEXT_OFFSET);
//                    if (offset + limit >= size) {
//                        nextUnavailable = true;
//                    }
//                } else {
//                    offset = (long) content.getSessionAttribute(PREVIOUS_OFFSET);
//                    if (offset - limit < 0) {
//                        previousUnavailable = true;
//                    }
//                }
//            }
//            entities = sortCommandExecutor.sort(sortOrder, limit, offset);
//        } catch (ServiceException e) {
//            log.error("command couldn't provide sorted tracklist", e);
//            return new ErrorCommand(e).execute(content);
//        }
//        log.debug("command provide sorted track list: " + entities);
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put(ENTITIES, entities);
//        attributes.put(PREVIOUS_UNAVAILABLE, previousUnavailable);
//        attributes.put(NEXT_UNAVAILABLE, nextUnavailable);
//
//        if (page.contains(MUSICIAN)) {
//            wrapWithStatistics(attributes, content);
//        }
//
//        return new CommandResult(CommandResult.ResponseType.FORWARD, page, attributes,
//                Map.of(sortOrderMarker, sortOrder, NEXT_OFFSET, offset + limit,
//                        PREVIOUS_OFFSET, offset - limit));
//
//    }
    public CommandResult sorted(RequestContent content, String sortOrderMarker, String page, CountCommandExecutor countCommandExecutor,
                              SortCommandExecutor<T> sortCommandExecutor) {

        List<T> entities;
        int current = detectCurrentPage(content);
        int limit = Integer.parseInt(ResourceBundle.getBundle("app").getString("app.list.limit"));
        long offset = detectOffset(current, limit);
        boolean sortOrder = !content.getSessionAttributes().containsKey(sortOrderMarker) ||
                (boolean) content.getSessionAttribute(sortOrderMarker);
        boolean nextUnavailable;
        boolean previousUnavailable = current == 1;
        int pageQuantity;
        List<Integer> pages = new ArrayList<>();
        try {
            long size = countCommandExecutor.count();
            pageQuantity = (int) (size / limit + 1);
            nextUnavailable = current == pageQuantity;
            for (int i = 1; i <= pageQuantity; i++) {
                pages.add(i);
            }
            entities = sortCommandExecutor.sort(sortOrder, limit, offset);
        } catch (ServiceException e) {
            return new ErrorCommand(e).execute(content);
        }
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(ENTITIES, entities);
        attributes.put(CURRENT, current);
        attributes.put(PREVIOUS_UNAVAILABLE, previousUnavailable);
        attributes.put(NEXT_UNAVAILABLE, nextUnavailable);
        attributes.put(SIZE, pages);

        if (page.contains(MUSICIAN)) {
            wrapWithStatistics(attributes, content);
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, page, attributes);
    }

    public CommandResult transfer(RequestContent content, String page, TransferCommandExecutor<T> commandExecutor) {
        String parameterValue = content.getRequestParameter(ID)[0];
        String entityType = content.getRequestParameters().containsKey(ENTITY_TYPE) ?
                content.getRequestParameter(ENTITY_TYPE)[0] : "empty";
        T t;
        try {
            List<T> entities = commandExecutor.transfer(parameterValue);
            if (entities.isEmpty()) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                        Map.of(RequestConstant.PROCESS,
                                MessageManager.getMessage("message.entity.not.available",
                                        (String) content.getSessionAttribute(RequestConstant.LOCALE))));
            }
            t = entities.get(0);
        } catch (ServiceException e) {
            log.error("command couldn't search track");
            return new ErrorCommand(e).execute(content);
        }

        log.debug("command successfully provide " + t + " to the next page");
        return new CommandResult(CommandResult.ResponseType.FORWARD, page, Map.of(ENTITY, t, ENTITY_TYPE, entityType));
    }

    public CommandResult update(UserService userService, RequestContent content, String updatedParameter,
                                AbstractValidator validator, UpdateCommandExecutor updateCommandExecutor) {
        Set<Violation> violations = validator.apply(content);
        String result;

        if (violations.isEmpty()) {
            String parameter = content.getRequestParameter(updatedParameter)[0];
            User user = (User) content.getSessionAttribute(USER);
            updateCommandExecutor.update(user, parameter);
            try {
                String locale = (String) content.getSessionAttribute(LOCALE);
                result = userService.updateUser(user) ? MessageManager.getMessage("label.updated", locale) :
                        MessageManager.getMessage("failed", locale);
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PROFILE_PAGE,
                        Map.of(PROCESS, result), Map.of(USER, user));
            } catch (ServiceException e) {
                log.error("couldn't update birth date", e);
                return new ErrorCommand(e).execute(content);
            }
        } else {
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PROFILE_PAGE,
                    Map.of(VALIDATOR_RESULT, violations));
        }
    }

    private void wrapWithStatistics(Map<String, Object> attributes, RequestContent content) {
        CommonService commonService = new CommonService();
        if (attributes.containsKey(ENTITIES)) {
            List<Musician> musicians = (List<Musician>) attributes.get(RequestConstant.ENTITIES);
            Map<Long, String> genres = new HashMap<>(musicians.size());
            Map<Long, Integer> tracksQuantity = new HashMap<>(musicians.size());
            musicians.forEach(musician -> {
                try {
                    List<Track> tracks = commonService.searchTracksByMusician(musician.getId());
                    tracksQuantity.put(musician.getId(), tracks.size());
                    genres.put(musician.getId(), tracks.stream()
                            .map(Track::getGenre)
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                            .entrySet()
                            .stream()
                            .max(Comparator.comparing(Map.Entry::getValue))
                            .map(genreLongEntry -> genreLongEntry.getKey().getTitle())
                            .orElse(""));
                } catch (ServiceException e) {
                    log.error("Can't provide statistic for musicians", e);
                    new ErrorCommand(e).execute(content);
                }
            });
            attributes.put(RequestConstant.TRACKS_SIZE, tracksQuantity);
            attributes.put(RequestConstant.GENRE, genres);
        }
    }


    /**
     * //1 = 0   //1 = 0
     * //2 = 8   //2 = 5
     * //3 = 16  //3 = 10
     * //4 = 24  //4 = 15
     * //5 = 32  //5 = 20
     * //6 = 40  //6 = 25
     */
    private long detectOffset(int currentPage, int limit) {
        return (long) (currentPage - 1) * limit;

    }

    private int detectCurrentPage(RequestContent content) {
        int current = Integer.parseInt(content.getRequestParameter(CURRENT)[0]);
        String direction = content.getRequestParameters().containsKey(DIRECTION) ?
                content.getRequestParameter(DIRECTION)[0] : null;
        if (direction == null) {
            current = 1;
        } else if (direction.equals(NEXT)) {
            current++;
        } else if (direction.equals(PREVIOUS)) {
            current--;
        }
        return current;
    }

}
