package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.handler.CommandHandler;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.User;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

/**
 * to sort playlists by id
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
@Log4j2
public class SortPlaylistIdCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Playlist> handler;

    public SortPlaylistIdCommand(CommonService commonService, CommandHandler<Playlist> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that forward to {@link PageConstant}.SORT_ID_PLAYLIST_LIBRARY_PAGE
     * with sorted list of playlists
     */
    @Override
    public CommandResult execute(RequestContent content) {
        User user = (User) content.getSessionAttribute(RequestConstant.USER);
        return handler.sorted(content, RequestConstant.SORT_PLAYLIST_ID_ORDER, PageConstant.SORT_ID_PLAYLIST_LIBRARY_PAGE,
                () -> commonService.countPlaylists(user.isAdmin()), commonService::sortedPlaylistsId);
    }
}
