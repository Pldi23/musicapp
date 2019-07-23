package by.platonov.music.command;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Genre;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.EntityParameterNotFoundException;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.AdminService;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static by.platonov.music.command.constant.RequestConstant.*;

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

    public RemoveTrackCommand(AdminService adminService, CommonService commonService) {
        this.adminService = adminService;
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        String locale = (String) content.getSessionAttribute(LOCALE);
        try {
            return removeEntity(content) ?
                    new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                            Map.of(PROCESS, MessageManager.getMessage("removed", locale)))
                    : new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                    Map.of(PROCESS, MessageManager.getMessage("message.already.removed", locale)));
        } catch (ServiceException | IOException | EntityParameterNotFoundException e) {
            log.error("command could not remove track", e);
//            return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
            return new ErrorCommand(e).execute(content);
        }
    }

    private void deleteFile(String uuid) throws IOException {
        Files.delete(Path.of(ResourceBundle.getBundle("app").getString("app.music.uploads") + File.separator + uuid));
    }

    private boolean removeEntity(RequestContent content) throws ServiceException, IOException, EntityParameterNotFoundException {
        boolean result;
        String entityType = content.getRequestParameter(RequestConstant.ENTITY_TYPE)[0];
        String id = content.getRequestParameter(ID)[0];
        switch (entityType) {
            case RequestConstant.TRACK:
                List<Track> tracks = commonService.searchTrackById(id);
                if (!tracks.isEmpty()) {
                    Track track = tracks.get(0);
                    deleteFile(track.getUuid());
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
