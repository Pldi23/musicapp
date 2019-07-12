package by.platonov.music.command;

import by.platonov.music.command.constant.CommandMessage;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import static by.platonov.music.command.constant.RequestConstant.*;

import java.util.List;
import java.util.Map;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-06-25.
 * @version 0.0.1
 */
@Log4j2
public class SearchCommand implements Command {

    private CommonService commonService;

    public SearchCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {

        //todo validation

        String searchRequest = content.getRequestParameter(SEARCH_REQUEST)[0];
        List<Musician> musicians;
        List<Track> tracks;
        List<Playlist> playlists;

        try {
            musicians = commonService.searchMusician(searchRequest);
            playlists = commonService.searchPlaylist(searchRequest);
            tracks = commonService.searchTrackByName(searchRequest);
        } catch (ServiceException e) {
            log.error("Service provide an exception for search command ", e);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.INFORMATION_PAGE,
                    Map.of(PROCESS, CommandMessage.SEARCH_REQUEST_MESSAGE));
        }

        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.SEARCH_PAGE,
                Map.of(MUSICIANS_ATTRIBUTE, musicians, TRACKS_ATTRIBUTE, tracks, PLAYLISTS_ATTRIBUTE, playlists));
    }
}
