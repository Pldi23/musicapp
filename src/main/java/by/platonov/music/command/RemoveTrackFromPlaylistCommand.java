package by.platonov.music.command;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import static by.platonov.music.command.constant.RequestConstant.*;
/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-15.
 * @version 0.0.1
 */
@Log4j2
public class RemoveTrackFromPlaylistCommand implements Command {

    private CommonService commonService;

    public RemoveTrackFromPlaylistCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        String trackId = content.getRequestParameter(TRACK_ID)[0];
        String playlistId = content.getRequestParameter(PLAYLIST_ID)[0];
        try {
            String result = commonService.removeTrackFromPlaylist(trackId, playlistId) ?
                    MessageManager.getMessage("removed", (String) content.getSessionAttribute(LOCALE)) :
                    MessageManager.getMessage("failed", (String) content.getSessionAttribute(LOCALE));
            Playlist playlist = commonService.searchPlaylistByIdWithTracks(playlistId).get(0);
            String length = commonService.countPlaylistLength(playlist);
            String size = commonService.countPlaylistSize(playlist);
                    return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PLAYLIST_PAGE,
                            Map.of(PLAYLIST, playlist, PROCESS, result, RequestConstant.LENGTH, length,
                                    RequestConstant.SIZE, size));
        } catch (ServiceException e) {
            log.error("command could't add track to playlist", e);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ERROR_REDIRECT_PAGE);
        }
    }
}
