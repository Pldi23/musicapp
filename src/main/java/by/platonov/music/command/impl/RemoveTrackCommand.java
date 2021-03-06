package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.EntityParameterNotFoundException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.AdminService;
import by.platonov.music.service.CommonService;
import by.platonov.music.service.FileService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
@Log4j2
public class RemoveTrackCommand implements Command {

    private AdminService adminService;
    private CommonService commonService;
    private FileService fileService;

    public RemoveTrackCommand(AdminService adminService, CommonService commonService, FileService fileService) {
        this.adminService = adminService;
        this.commonService = commonService;
        this.fileService = fileService;
    }

    /**
     * to remove {@link Track} from application
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.ENTITY_REMOVED_PAGE with result-message
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        String locale = (String) content.getSessionAttribute(LOCALE);
        try {
            return removeEntity(content) ?
                    new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                            Map.of(PROCESS, MessageManager.getMessage("removed", locale)))
                    : new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                    Map.of(PROCESS, MessageManager.getMessage("message.already.removed", locale)));
        } catch (ServiceException | EntityParameterNotFoundException e) {
            log.error("command could not remove track", e);
            return new ErrorCommand(e).execute(content);
        }
    }

    /**
     * helper method to remove {@link by.platonov.music.entity.Entity} depending on it's type
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return true if object was removed successfully and false if not
     * @throws ServiceException when {@link by.platonov.music.repository.Repository} throws repository exception
     * @throws EntityParameterNotFoundException when entity parameter not found in request
     */
    private boolean removeEntity(RequestContent content) throws ServiceException, EntityParameterNotFoundException {
        boolean result;
        String entityType = content.getRequestParameter(RequestConstant.ENTITY_TYPE)[0];
        String id = content.getRequestParameter(ID)[0];
        switch (entityType) {
            case RequestConstant.TRACK:
                List<Track> tracks = commonService.searchTrackById(id);
                if (!tracks.isEmpty()) {
                    Track track = tracks.get(0);
                    fileService.deleteFile(track.getUuid());
                    result = adminService.removeTrack(track);
                } else {
                    result = false;
                }
                break;
            case RequestConstant.PLAYLIST:
                List<Playlist> playlists = commonService.searchPlaylistById(id);
                if (!playlists.isEmpty()) {
                    Playlist playlist = playlists.get(0);
                    result = adminService.removePlaylist(playlist);
                } else {
                    result = false;
                }
                break;
            case RequestConstant.MUSICIAN:
                List<Musician> musicians = commonService.searchMusicianById(id);
                if (!musicians.isEmpty()) {
                    Musician musician = musicians.get(0);
                    result = adminService.removeMusician(musician);
                } else {
                    result = false;
                }
                break;
            case RequestConstant.GENRE:
                List<Genre> genres = commonService.searchGenreById(id);
                if (!genres.isEmpty()) {
                    Genre genre = genres.get(0);
                    result = adminService.removeGenre(genre);
                } else {
                    result = false;
                }
                break;
            default:
                throw new EntityParameterNotFoundException("could not detect entity");
        }
        return result;
    }
}
