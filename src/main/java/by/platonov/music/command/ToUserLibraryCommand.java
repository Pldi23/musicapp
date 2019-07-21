package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.UserService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

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

        try {
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_LIBRARY_PAGE,
                    Map.of(RequestConstant.ENTITIES, userService.searchAllUsers()));
        } catch (ServiceException e) {
            log.error("command couldn't provide users", e);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
        }
    }
}
