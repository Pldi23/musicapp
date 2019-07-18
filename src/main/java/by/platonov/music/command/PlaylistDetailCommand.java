package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-13.
 * @version 0.0.1
 */
@Log4j2
public class PlaylistDetailCommand implements Command {

    private CommonService commonService;

    public PlaylistDetailCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        String playlistId = content.getRequestParameter(RequestConstant.ID)[0];
        Playlist playlist;
        String length;
        String size;
        try {
            playlist = commonService.searchPlaylistByIdWitTracks(playlistId);
            length = commonService.countPlaylistLength(playlist);
            size = commonService.countPlaylistSize(playlist);
        } catch (ServiceException e) {
            log.error("command could't provide playlist", e);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ERROR_REDIRECT_PAGE);
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PLAYLIST_PAGE,
                Map.of(RequestConstant.PLAYLIST, playlist, RequestConstant.LENGTH, length, RequestConstant.SIZE, size));
    }
}
