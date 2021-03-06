package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import static by.platonov.music.constant.RequestConstant.*;
/**
 * to remove {@link by.platonov.music.entity.Track} from {@link Playlist}
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

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.PLAYLIST_PAGE with result message
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        String trackId = content.getRequestParameter(TRACK_ID)[0];
        String playlistId = content.getRequestParameter(PLAYLIST_ID)[0];
        try {
            String result = commonService.removeTrackFromPlaylist(trackId, playlistId) ?
                    MessageManager.getMessage("removed", (String) content.getSessionAttribute(LOCALE)) :
                    MessageManager.getMessage("failed", (String) content.getSessionAttribute(LOCALE));
            Playlist playlist = commonService.searchPlaylistByIdWithTracks(playlistId).get(0);
            String size = commonService.countPlaylistSize(playlist);
                    return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PLAYLIST_PAGE,
                            Map.of(PLAYLIST, playlist, PROCESS, result,
                                    RequestConstant.SIZE, size));
        } catch (ServiceException e) {
            log.error("command could't add track to playlist", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}
