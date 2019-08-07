package by.platonov.music.command.impl;


import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

/**
 * to show {@link User}'s {@link Playlist}s
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

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.USER_PLAYLISTS_PAGE with list of playlists in attributes
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
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
