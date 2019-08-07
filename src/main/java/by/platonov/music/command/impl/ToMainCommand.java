package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

/**
 * forwards to user's "main" page after clicking on application icon in navigation bar
 *
 * @author Dzmitry Platonov on 2019-07-30.
 * @version 0.0.1
 */
@Log4j2
public class ToMainCommand implements Command {

    private CommonService commonService;

    public ToMainCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.LOGIN_PAGE if user is guest
     * forward to {@link PageConstant}.ADMIN_PAGE if user is admin
     * forward to {@link PageConstant}.MAIN_PAGE if user is regular registered user
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        User user = (User) content.getSessionAttribute(RequestConstant.USER);
        CommandResult commandResult;
        try {
            if (user == null) {
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.LOGIN_PAGE,
                        Map.of(RequestConstant.TRACKS, commonService.getTracksLastAdded()));
            } else if (user.isAdmin()) {
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ADMIN_PAGE,
                        Map.of(RequestConstant.TRACKS, commonService.getRandomTen()));
            } else {
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.MAIN_PAGE,
                        Map.of(RequestConstant.TRACKS, commonService.getRandomTen()));
            }
        } catch (ServiceException e) {
            log.error("can't provide tracks", e);
           return new ErrorCommand(e).execute(content);
        }
        return commandResult;
    }
}
