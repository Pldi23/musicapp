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

import java.util.List;
import java.util.Map;

/**
 * to view details about the {@link Playlist}
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

    /**
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.ENTITY_REMOVED_PAGE if playlist was not found
     * forward to {@link PageConstant}.PLAYLIST_PAGE if playlist was found
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        String playlistId = content.getRequestParameter(RequestConstant.ID)[0];
        Playlist playlist;
        String length;
        String size;
        try {
            List<Playlist> playlists = commonService.searchPlaylistById(playlistId);
            if (playlists.isEmpty()) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                        Map.of(RequestConstant.PROCESS,
                                MessageManager.getMessage("message.entity.not.available",
                                        (String) content.getSessionAttribute(RequestConstant.LOCALE))));
            }
            playlist = playlists.get(0);
            length = commonService.countPlaylistLength(playlist);
            size = commonService.countPlaylistSize(playlist);
        } catch (ServiceException e) {
            log.error("command could't provide playlist", e);
            return new ErrorCommand(e).execute(content);
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.PLAYLIST_PAGE,
                Map.of(RequestConstant.PLAYLIST, playlist, RequestConstant.LENGTH, length, RequestConstant.SIZE, size));
    }
}
