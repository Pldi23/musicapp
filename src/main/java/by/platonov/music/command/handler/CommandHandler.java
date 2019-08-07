package by.platonov.music.command.handler;

import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.command.impl.ErrorCommand;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Track;
import by.platonov.music.entity.filter.EntityFilter;
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
 *
 * Encapsulates common functionality across Command Layer
 *
 * @author Dzmitry Platonov on 2019-07-06.
 * @version 0.0.1
 */
@Log4j2
public class CommandHandler<T> {

    /**
     * Service method for sorting
     * @param content DTO containing all data received with HttpRequest
     * @param sortOrderMarker request parameter of order //currently not implemented
     * @param targetPage target page for request forwarding after command is completed. {@link PageConstant}
     * @param countCommandExecutor instance of actual {@link CountCommandExecutor}
     * @param sortCommandExecutor instance of actual {@link SortCommandExecutor}
     * @return Result of command execution as {@link CommandResult}
     */
    public CommandResult sorted(RequestContent content, String sortOrderMarker, String targetPage,
                                CountCommandExecutor countCommandExecutor, SortCommandExecutor<T> sortCommandExecutor) {

        List<T> entities;
        int current = detectCurrentPage(content);
        int limit = Integer.parseInt(ResourceBundle.getBundle("app").getString("app.list.limit"));
        long offset = detectOffset(current, limit);
        boolean sortOrder = !content.getSessionAttributes().containsKey(sortOrderMarker) ||
                (boolean) content.getSessionAttribute(sortOrderMarker); //currently not implemented
        boolean nextUnavailable;
        boolean previousUnavailable = current == 1; //non previous page available if current page number is 1
        int pageQuantity;
        List<Integer> pages = new ArrayList<>();
        try {
            long size = countCommandExecutor.count();
            pageQuantity = size % limit == 0 ? (int) size / limit : (int) (size / limit + 1); //total number of pages
            nextUnavailable = current == pageQuantity; // non next page available if current page is equal to last page
            for (int i = 1; i <= pageQuantity; i++) { // fill in the list with page numbers to design the pagination form
                pages.add(i);
            }
            entities = sortCommandExecutor.sort(sortOrder, limit, offset);
        } catch (ServiceException e) {
            log.error("can't provide entities list", e);
            return new ErrorCommand(e).execute(content);
        }
        Map<String, Object> attributes = new HashMap<>();
        putAttributes(attributes, entities, current, previousUnavailable, nextUnavailable, pages);

        if (targetPage.contains(MUSICIAN)) {
            wrapWithStatistics(attributes, content);
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, targetPage, attributes);
    }

    /**
     * helper method to add to {@link RequestContent} attributes with musicians favorite genre and number of tracks in repository
     * @param attributes requestAttributes to fill
     * @param content DTO containing all data received with HttpRequest
     */
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
     * Service method for filtering entities
     * @param content DTO containing all data received with HttpRequest
     * @param page target page for request forwarding after command is completed. {@link PageConstant}
     * @param filterCommandExecutor instance of actual {@link FilterCommandExecutor}
     * @param filterAttributes to build instance of subclass {@link EntityFilter}
     * @return Result of command execution as {@link CommandResult}
     */
    public CommandResult filter(RequestContent content, String page,
                                FilterCommandExecutor<T> filterCommandExecutor,
                                Map<String, Object> filterAttributes) {

        List<T> entities;
        int current = detectCurrentPage(content);
        int limit = Integer.parseInt(ResourceBundle.getBundle("app").getString("app.list.limit"));
        long offset = detectOffset(current, limit);
        boolean nextUnavailable;
        boolean previousUnavailable = current == 1;
        int pageQuantity;
        List<Integer> pages = new ArrayList<>();

        try {
            long size = filterCommandExecutor.filter(Integer.MAX_VALUE, 0).size();
            pageQuantity = size % limit == 0 ? (int) size / limit : (int) (size / limit + 1);
            nextUnavailable = current == pageQuantity;
            for (int i = 1; i <= pageQuantity; i++) {
                pages.add(i);
            }
            entities = filterCommandExecutor.filter(limit, offset);

        } catch (ServiceException e) {
            log.error("command could't provide entities list", e);
            return new ErrorCommand(e).execute(content);
        }
        Map<String, Object> attributes = new HashMap<>();
        putAttributes(attributes, entities, current, previousUnavailable, nextUnavailable, pages);
        attributes.putAll(filterAttributes);
        log.debug("command provide entities: " + entities);
        return new CommandResult(CommandResult.ResponseType.FORWARD, page, attributes);
    }

    /**
     * helper method to add attributes
     * @param attributes to fill
     * @param entities list of entities
     * @param current page number
     * @param previousUnavailable is unavailable
     * @param nextUnavailable is unavailable
     * @param pages list of pages
     */
    private void putAttributes(Map<String, Object> attributes, List<T> entities, int current, boolean previousUnavailable,
                               boolean nextUnavailable, List<Integer> pages) {
        attributes.put(ENTITIES, entities);
        attributes.put(CURRENT, current);
        attributes.put(PREVIOUS_UNAVAILABLE, previousUnavailable);
        attributes.put(NEXT_UNAVAILABLE, nextUnavailable);
        attributes.put(SIZE, pages);
    }

    /**
     * helper method to determine the offset
     * @param currentPage number
     * @param limit application pagination page limit
     * @return offset
     */
    private long detectOffset(int currentPage, int limit) {
        return (long) (currentPage - 1) * limit;
    }

    /**
     * helper method to detect number of current page
     * @param content DTO containing all data received with HttpRequest
     * @return page number
     */
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

    /**
     * Service method for transfer parameters
     * @param content DTO containing all data received with HttpRequest
     * @param page target page for request forwarding after command is completed. {@link PageConstant}
     * @param commandExecutor instance of actual {@link TransferCommandExecutor}
     * @return Result of command execution as {@link CommandResult}
     */
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

    /**
     * service method to update user
     * @param userService instance of {@link UserService}
     * @param content DTO containing all data received with HttpRequest
     * @param updatedParameter value of input parameter
     * @param validator instance of actual input parameter {@link AbstractValidator}
     * @param updateCommandExecutor instance of actual {@link UpdateCommandExecutor}
     * @return Result of command execution as {@link CommandResult}
     */
    public CommandResult update(UserService userService, RequestContent content, String updatedParameter,
                                AbstractValidator validator, UpdateCommandExecutor updateCommandExecutor) {
        Set<Violation> violations = validator.apply(content);
        String result;

        if (violations.isEmpty()) {
            String parameter = content.getRequestParameter(updatedParameter)[0];
            User user = (User) content.getSessionAttribute(USER);
            updateCommandExecutor.update(user, parameter);
            try {
                result = userService.updateUser(user) ?
                        MessageManager.getMessage("label.updated", (String) content.getSessionAttribute(LOCALE)) :
                        MessageManager.getMessage("failed", (String) content.getSessionAttribute(LOCALE));
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


}
