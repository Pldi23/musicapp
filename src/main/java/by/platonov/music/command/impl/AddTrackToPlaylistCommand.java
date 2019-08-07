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
 * to add {@link Track} to {@link by.platonov.music.entity.Playlist}
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

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that
     * forward to ENTITY_REMOVED_PAGE if required track not found
     * forward to page from which the addition occurred if executes successfully
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
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
            String result = commonService.addTrackToPLaylist(trackId, playlistId) ?
                    track.getName() + " " + MessageManager.getMessage("added", locale) :
                    MessageManager.getMessage("failed", locale);
            CommandResult commandResult = (CommandResult) content.getSessionAttribute(BACKUP);
            commandResult.getAttributes().put(PROCESS, result);
            commandResult.getAttributes().put(TRACK, track);
            return commandResult;
        } catch (ServiceException e) {
            log.error("command could't add track to playlist", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}
