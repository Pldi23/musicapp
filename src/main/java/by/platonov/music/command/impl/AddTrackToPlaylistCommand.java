package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

import static by.platonov.music.constant.RequestConstant.*;

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
        String locale = (String) content.getSessionAttribute(LOCALE);
        try {
            List<Track> tracks = commonService.searchTrackById(trackId);
            if (tracks.isEmpty()) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                        Map.of(RequestConstant.PROCESS,
                                MessageManager.getMessage("message.entity.not.available", locale)));
            }
            Track track = tracks.get(0);
            return commonService.addTrackToPLaylist(trackId, playlistId) ?
                    new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.TRACK_PAGE,
                            Map.of(PROCESS, MessageManager.getMessage("added", locale),
                                    TRACK, track)) :
                    new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.TRACK_PAGE,
                            Map.of(PROCESS, MessageManager.getMessage("failed", locale),
                                    TRACK, track));
        } catch (ServiceException e) {
            log.error("command could't add track to playlist", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}
