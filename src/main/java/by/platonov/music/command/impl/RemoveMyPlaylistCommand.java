package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-17.
 * @version 0.0.1
 */
@Log4j2
public class RemoveMyPlaylistCommand implements Command {

    private CommonService commonService;

    public RemoveMyPlaylistCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        String playlistId = content.getRequestParameter(RequestConstant.ID)[0];
        User user = (User) content.getSessionAttribute(RequestConstant.USER);
        String locale = (String) content.getSessionAttribute(RequestConstant.LOCALE);
        List<Playlist> playlists;
        Map<Playlist, List<String>> playlistWithStatistics = new HashMap<>();
        try {
            String result = commonService.removePlaylistFromUser(user, playlistId) ?
                    MessageManager.getMessage("removed", locale) : MessageManager.getMessage("failed", locale);
            playlists = commonService.searchUserPlaylists(user);
            for (Playlist playlist : playlists) {
                playlistWithStatistics.put(playlist,
                        List.of(commonService.countPlaylistLength(playlist), commonService.countPlaylistSize(playlist)));
            }
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.USER_PLAYLISTS_PAGE,
                    Map.of(RequestConstant.PROCESS, result, RequestConstant.PLAYLISTS, playlists,
                            RequestConstant.PLAYLISTS_STATISTIC, playlistWithStatistics));
        } catch (ServiceException e) {
            log.error("command can't remove playlist from " + user + " playlists", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}
