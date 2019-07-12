package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.exception.ServiceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static by.platonov.music.command.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-06.
 * @version 0.0.1
 */
@Log4j2
class CommandHandler<T> {

    CommandResult sorted(RequestContent content, String sortOrderMarker, String page,
                         CountCommandExecutor countCommandExecutor, SortCommandExecutor<T> sortCommandExecutor) {
        List<T> entities;
        boolean sortOrder = !content.getSessionAttributes().containsKey(sortOrderMarker) ||
                (boolean) content.getSessionAttribute(sortOrderMarker);
        int limit = Integer.parseInt(ResourceBundle.getBundle("app").getString("app.list.limit"));
        boolean nextUnavailable = false;
        boolean previousUnavailable = false;
        boolean next = !content.getRequestParameters().containsKey(DIRECTION)
                || content.getRequestParameter(DIRECTION)[0].equals(NEXT);
        long offset;

        try {
            long size = countCommandExecutor.count();
            if (content.getRequestParameters().containsKey(OFFSET)) {
                offset = 0;
                previousUnavailable = true;
                nextUnavailable = size <= limit;
            } else {
                if (next) {
                    offset = (long) content.getSessionAttribute(NEXT_OFFSET);
                    if (offset + limit >= size) {
                        nextUnavailable = true;
                    }
                } else {
                    offset = (long) content.getSessionAttribute(PREVIOUS_OFFSET);
                    if (offset - limit < 0) {
                        previousUnavailable = true;
                    }
                }
            }
            entities = sortCommandExecutor.sort(sortOrder, limit, offset);
        } catch (ServiceException e) {
            log.error("command couldn't provide sorted tracklist", e);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
        }
        log.debug("command provide sorted track list: " + entities);
        return new CommandResult(CommandResult.ResponseType.FORWARD, page,
                Map.of(ENTITIES, entities, PREVIOUS_UNAVAILABLE, previousUnavailable,
                        NEXT_UNAVAILABLE, nextUnavailable),
                Map.of(sortOrderMarker, sortOrder, NEXT_OFFSET, offset + limit,
                        PREVIOUS_OFFSET, offset - limit));
    }

    CommandResult transfer(RequestContent content, String page, TransferCommandExecutor<T> commandExecutor) {
        String parameterValue = content.getRequestParameter(ID)[0];
        T t;
        try {
            t = commandExecutor.transfer(parameterValue);
        } catch (ServiceException e) {
            log.error("command couldn't search track");
            return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
        }

        log.debug("command successfully provide " + t + " to the next page");
        return new CommandResult(CommandResult.ResponseType.FORWARD, page, Map.of(RequestConstant.ENTITY, t));
    }
}