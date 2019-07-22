package by.platonov.music.command;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import by.platonov.music.service.UserService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import static by.platonov.music.command.constant.RequestConstant.LOCALE;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-20.
 * @version 0.0.1
 */
@Log4j2
public class ChangePlaylistAccess implements Command {

    private UserService userService;
    private CommonService commonService;

    public ChangePlaylistAccess(UserService userService, CommonService commonService) {
        this.userService = userService;
        this.commonService = commonService;
    }

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
            return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
        }
    }
}
