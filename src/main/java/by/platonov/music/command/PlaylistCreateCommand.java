package by.platonov.music.command;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import by.platonov.music.validator.PlaylistNameValidator;
import by.platonov.music.validator.Violation;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-14.
 * @version 0.0.1
 */
@Log4j2
public class PlaylistCreateCommand implements Command {

    private CommonService commonService;

    public PlaylistCreateCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        CommandResult commandResult;
        Set<Violation> violations = new PlaylistNameValidator(null).apply(content);
        String result;
        List<Playlist> playlists;
        Map<Playlist, List<String>> playlistWithStatistics = new HashMap();
        String name = content.getRequestParameter(RequestConstant.NAME)[0];
        User user = (User) content.getSessionAttribute(RequestConstant.USER);
        if (violations.isEmpty()) {
            try {
                result = commonService.createPlaylist(user, name) ? MessageManager.getMessage("added", (String) content.getSessionAttribute(RequestConstant.LOCALE)) :
                        MessageManager.getMessage("failed", (String) content.getSessionAttribute(RequestConstant.LOCALE));
                playlists = commonService.searchUserPlaylists(user);
                for (Playlist playlist : playlists) {
                    playlistWithStatistics.put(playlist,
                            List.of(commonService.countPlaylistLength(playlist), commonService.countPlaylistSize(playlist)));
                }
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_PLAYLISTS_PAGE,
                        Map.of(RequestConstant.PROCESS, result, RequestConstant.PLAYLISTS_ATTRIBUTE, playlists,
                                RequestConstant.PLAYLISTS_STATISTIC, playlistWithStatistics));
            } catch (ServiceException e) {
                log.error("command can't add playlist ", e);
                return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
            }

        } else {
            try {
                playlists = commonService.searchUserPlaylists(user);
                for (Playlist playlist : playlists) {
                    playlistWithStatistics.put(playlist,
                            List.of(commonService.countPlaylistLength(playlist), commonService.countPlaylistSize(playlist)));
                }
            } catch (ServiceException e) {
                log.error("command can't add playlist ", e);
                return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
            }
            result = "\u2718" + violations.stream().map(Violation::getMessage).collect(Collectors.joining("\u2718"));
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_PLAYLISTS_PAGE,
                    Map.of(RequestConstant.PROCESS, result, RequestConstant.PLAYLISTS_ATTRIBUTE, playlists,
                            RequestConstant.PLAYLISTS_STATISTIC, playlistWithStatistics));
        }
        return commandResult;
    }
}
