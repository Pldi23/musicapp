package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
@Log4j2
public class ToUserLibraryCommand implements Command {

    private UserService userService;

    public ToUserLibraryCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContent content) {

        int limit = Integer.parseInt(ResourceBundle.getBundle("app").getString("app.list.limit"));
        boolean nextUnavailable = false;
        boolean previousUnavailable = false;
        boolean next = !content.getRequestParameters().containsKey(DIRECTION)
                || content.getRequestParameter(DIRECTION)[0].equals(NEXT);
        long offset;
        try {
            long size = userService.searchAllUsers(Integer.MAX_VALUE, 0).size();

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
            List<User> users = userService.searchAllUsers(limit, offset);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_LIBRARY_PAGE,
                    Map.of(ENTITIES, users, PREVIOUS_UNAVAILABLE, previousUnavailable, NEXT_UNAVAILABLE, nextUnavailable,
                            PAGE_COMMAND, USER_LIBRARY),
                    Map.of(NEXT_OFFSET, offset + limit, PREVIOUS_OFFSET, offset - limit));
        } catch (ServiceException e) {
            log.error("command couldn't provide users", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}

