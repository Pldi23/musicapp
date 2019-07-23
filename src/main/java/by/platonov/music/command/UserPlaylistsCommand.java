package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-14.
 * @version 0.0.1
 */
@Log4j2
public class UserPlaylistsCommand implements Command {

    private CommonService commonService;

    public UserPlaylistsCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        User user = (User) content.getSessionAttribute(RequestConstant.USER);
        List<Playlist> playlists;
        try {
            playlists = commonService.searchUserPlaylists(user);
        } catch (ServiceException e) {
            log.error("command could't count users playlists", e);
            return new ErrorCommand(e).execute(content);
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_PLAYLISTS_PAGE,
                Map.of(RequestConstant.SIZE, playlists.size(), RequestConstant.PLAYLISTS, playlists));
    }
}
