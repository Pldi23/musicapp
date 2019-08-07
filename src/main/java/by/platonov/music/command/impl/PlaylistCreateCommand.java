package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import by.platonov.music.validator.PlaylistNameValidator;
import by.platonov.music.validator.Violation;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * to create {@link Playlist}
 *
 * @author Dzmitry Platonov on 2019-07-14.
 * @version 0.0.1
 */
@Log4j2
public class PlaylistCreateCommand implements Command {

    private CommonService commonService;

    public PlaylistCreateCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.USER_PLAYLISTS_PAGE with violations if it was found
     * forward to {@link PageConstant}.USER_PLAYLISTS_PAGE with message failed/added
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult;
        Set<Violation> violations = new PlaylistNameValidator(null).apply(content);
        String result;
        List<Playlist> playlists;
        String name = content.getRequestParameter(RequestConstant.NAME)[0];
        User user = (User) content.getSessionAttribute(RequestConstant.USER);
        if (violations.isEmpty()) {
            try {
                String locale = (String) content.getSessionAttribute(RequestConstant.LOCALE);
                if (!user.isAdmin()) {
                    result = commonService.createPlaylist(user, true, name) ?
                            MessageManager.getMessage("added", locale) :
                            MessageManager.getMessage("failed", locale);

                } else {
                    String access = content.getRequestParameter(RequestConstant.ACCESS)[0];
                    result = commonService.createPlaylist(user, Boolean.parseBoolean(access), name) ?
                            MessageManager.getMessage("added", locale) :
                            MessageManager.getMessage("failed", locale);
                }

                playlists = commonService.searchUserPlaylists(user);
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_PLAYLISTS_PAGE,
                        Map.of(RequestConstant.PROCESS, result, RequestConstant.PLAYLISTS, playlists));
            } catch (ServiceException e) {
                log.error("command can't add playlist ", e);
                return new ErrorCommand(e).execute(content);
            }

        } else {
            try {
                playlists = commonService.searchUserPlaylists(user);
            } catch (ServiceException e) {
                log.error("command can't add playlist ", e);
                return new ErrorCommand(e).execute(content);
            }
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_PLAYLISTS_PAGE,
                    Map.of(RequestConstant.VALIDATOR_RESULT, violations, RequestConstant.PLAYLISTS, playlists));
        }
        return commandResult;
    }
}
