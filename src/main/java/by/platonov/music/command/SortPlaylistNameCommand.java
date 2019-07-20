package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.User;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
@Log4j2
public class SortPlaylistNameCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Playlist> handler;

    public SortPlaylistNameCommand(CommonService commonService, CommandHandler<Playlist> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        User user = (User) content.getSessionAttribute(RequestConstant.USER);
        return handler.sorted(content, RequestConstant.SORT_PLAYLIST_NAME_ORDER, PageConstant.SORT_NAME_PLAYLIST_LIBRARY_PAGE,
                () -> commonService.countPlaylists(user.isAdmin()),
                (isAscending, limit, offset) -> commonService.sortedPlaylistsName(isAscending, limit, offset, user.isAdmin()));
    }
}
