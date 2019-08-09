package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.EntityParameterNotFoundException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.message.MessageManager;
import by.platonov.music.service.AdminService;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.*;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * to change {@link Track} order in {@link Playlist}
 *
 * @author Dzmitry Platonov on 2019-08-03.
 * @version 0.0.1
 */
@Log4j2
public class UpdateTracksOrderCommand implements Command {

    private CommonService commonService;
    private AdminService adminService;

    public UpdateTracksOrderCommand(CommonService commonService, AdminService adminService) {
        this.commonService = commonService;
        this.adminService = adminService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.ENTITY_REMOVED_PAGE if playlist was not found
     * forward to {@link PageConstant}.PLAYLIST_PAGE with result message and required attributes
     * executes {@link ErrorCommand} if {@link ServiceException} or {@link EntityParameterNotFoundException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        String moveType = content.getRequestParameter(MOVE)[0];
        String playlistId = content.getRequestParameter(ID)[0];
        String index = content.getRequestParameter(INDEX)[0];
        String locale = (String) content.getSessionAttribute(LOCALE);

        try {
            List<Playlist> playlists = commonService.searchPlaylistByIdWithTracks(playlistId);
            if (playlists.isEmpty()) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                        Map.of(PROCESS, MessageManager.getMessage("failed", locale)));
            }
            Playlist playlist = playlists.get(0);
            List<Track> tracks = playlist.getTracks();
            moveTracks(tracks, moveType, Integer.parseInt(index));
            playlist.setTracks(tracks);
            String result = adminService.updatePlaylist(playlist) ? MessageManager.getMessage("updated", locale) :
                    MessageManager.getMessage("failed", locale);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PLAYLIST_PAGE,
                    Map.of(PLAYLIST, commonService.searchPlaylistByIdWithTracks(playlistId).get(0), PROCESS, result,
                            SIZE, playlist.getSize()));
        } catch (ServiceException | EntityParameterNotFoundException e) {
            log.error("can't provide playlist", e);
            return new ErrorCommand(e).execute(content);
        }
    }

    /**
     * handles changes in track order
     * @param tracks list of tracks to change
     * @param moveType move type/direction
     * @param trackIndex index of the required track
     * @throws EntityParameterNotFoundException if parameter not found in request
     */
    private void moveTracks(List<Track> tracks, String moveType, int trackIndex) throws EntityParameterNotFoundException {
        switch (moveType) {
            case DOWN:
                Collections.swap(tracks, trackIndex, trackIndex + 1);
                break;
            case UP:
                Collections.swap(tracks, trackIndex, trackIndex - 1);
                break;
            case TOP:
                Collections.rotate(tracks.subList(0, trackIndex + 1), 1);
                break;
            case BOTTOM:
                Collections.rotate(tracks.subList(trackIndex, tracks.size()), -1);
                break;
            case SHUFFLE:
                Collections.shuffle(tracks);
                break;
            case REVERSE:
                Collections.reverse(tracks);
                break;
            default:
                throw new EntityParameterNotFoundException("move parameter for tracks order not found");
        }
    }
}
