package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.AdminService;
import by.platonov.music.service.CommonService;
import by.platonov.music.validator.PlaylistNameValidator;
import by.platonov.music.validator.Violation;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * to update {@link Playlist}
 *
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
@Log4j2
public class UpdatePlaylistCommand implements Command {

    private AdminService adminService;
    private CommonService commonService;

    public UpdatePlaylistCommand(AdminService adminService, CommonService commonService) {
        this.adminService = adminService;
        this.commonService = commonService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.UPDATE_PLAYLIST_PAGE with violations if it was found
     * forward to {@link PageConstant}.UPDATE_PLAYLIST_PAGE with result message
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        try {
            Set<Violation> violations = new PlaylistNameValidator(null).apply(content);
            String playlistId = content.getRequestParameter(RequestConstant.ID)[0];
            String playlistName = content.getRequestParameter(RequestConstant.NAME)[0];
            List<Playlist> playlists = commonService.searchPlaylistByIdWithTracks(playlistId);
            String locale = (String) content.getSessionAttribute(RequestConstant.LOCALE);
            if (playlists.isEmpty()) {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ENTITY_REMOVED_PAGE,
                        Map.of(RequestConstant.PROCESS, MessageManager.getMessage("failed", locale)));
            }
            Playlist playlist = playlists.get(0);
            playlist.setName(playlistName);

            if (violations.isEmpty()) {
                String result = adminService.updatePlaylist(playlist) ?
                        MessageManager.getMessage("updated", locale) : MessageManager.getMessage("failed", locale);
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.UPDATE_PLAYLIST_PAGE,
                        Map.of(RequestConstant.PROCESS, result, RequestConstant.ENTITY, playlist));
            } else {
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.UPDATE_PLAYLIST_PAGE,
                        Map.of(RequestConstant.VALIDATOR_RESULT, violations, RequestConstant.ENTITY, playlist));
            }
        } catch (ServiceException e) {
            log.error("command couldn't update musician", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}
