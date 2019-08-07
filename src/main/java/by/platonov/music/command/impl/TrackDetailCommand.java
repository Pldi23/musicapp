package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * to view details about the {@link Track}
 *
 * @author Dzmitry Platonov on 2019-07-13.
 * @version 0.0.1
 */
@Log4j2
public class TrackDetailCommand implements Command {

    private CommonService commonService;

    public TrackDetailCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.ENTITY_REMOVED_PAGE if track was not found
     * forward to {@link PageConstant}.TRACK_PAGE if track was found
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        String trackId = content.getRequestParameter(RequestConstant.ID)[0];
        User user = (User) content.getSessionAttribute(RequestConstant.USER);
        Track track;
        List<Playlist> playlists;
        try {
            List<Track> tracks = commonService.searchTrackById(trackId);
            if (tracks.isEmpty()) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                        Map.of(RequestConstant.PROCESS,
                                MessageManager.getMessage("message.entity.not.available",
                                        (String) content.getSessionAttribute(RequestConstant.LOCALE))));
            }
            track = tracks.get(0);

            playlists = user != null ? commonService.searchPlaylistsByTrackAndUser(Long.parseLong(trackId), user)
                    : new ArrayList<>();

        } catch (ServiceException e) {
            log.error("command couldn't provide track", e);
            return new ErrorCommand(e).execute(content);
        }
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(RequestConstant.TRACK, track);
        attributes.put(RequestConstant.PLAYLISTS, playlists);
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.TRACK_PAGE, attributes);
    }
}
