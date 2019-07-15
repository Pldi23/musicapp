package by.platonov.music.command;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.Track;
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
public class AddTrackToPlaylistCommand implements Command {

    private CommonService commonService;

    public AddTrackToPlaylistCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {

        String trackId = content.getRequestParameter(ID)[0];
        String playlistId = content.getRequestParameter(PLAYLIST_ID)[0];
        try {
            Track track = commonService.searchTrackById(trackId);
            return commonService.addTrackToPLaylist(trackId, playlistId) ?
                    new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.TRACK_PAGE,
                            Map.of(PROCESS, MessageManager.getMessage("added",
                                    (String) content.getSessionAttribute(LOCALE)),
                                    TRACK, track)) :
                    new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.TRACK_PAGE,
                            Map.of(PROCESS, MessageManager.getMessage("failed",
                                    (String) content.getSessionAttribute(LOCALE)),
                                    TRACK, track));
        } catch (ServiceException e) {
            log.error("command could't add track to playlist", e);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ERROR_REDIRECT_PAGE);
        }
    }
}
