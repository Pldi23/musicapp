package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import by.platonov.music.service.UserService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import static by.platonov.music.constant.RequestConstant.LOCALE;

/**
 * to change {@link by.platonov.music.entity.Playlist} access level
 *
 * @author Dzmitry Platonov on 2019-07-20.
 * @version 0.0.1
 */
@Log4j2
public class ChangePlaylistAccessCommand implements Command {

    private UserService userService;
    private CommonService commonService;

    public ChangePlaylistAccessCommand(UserService userService, CommonService commonService) {
        this.userService = userService;
        this.commonService = commonService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that
     * forward to {@link PageConstant}.USER_PLAYLISTS_PAGE with updated-message if command was executed successfully
     * forward to {@link PageConstant}.USER_PLAYLISTS_PAGE with failed-message if command was failed
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        String playlistId = content.getRequestParameter(RequestConstant.ID)[0];
        User user = (User) content.getSessionAttribute(RequestConstant.USER);
        try {
            String result = userService.updatePlaylistAccess(playlistId) ?
                    MessageManager.getMessage("updated", (String) content.getSessionAttribute(LOCALE)) :
                    MessageManager.getMessage("failed", (String) content.getSessionAttribute(LOCALE));

            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_PLAYLISTS_PAGE,
                    Map.of(RequestConstant.PROCESS, result,
                            RequestConstant.PLAYLISTS, commonService.searchUserPlaylists(user)));
        } catch (ServiceException e) {
            log.error("couldn't change playlist level access", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}
