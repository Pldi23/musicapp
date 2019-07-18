package by.platonov.music.command;

import by.platonov.music.command.constant.CommandMessage;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import by.platonov.music.validator.SearchValidator;
import by.platonov.music.validator.Violation;
import lombok.extern.log4j.Log4j2;

import static by.platonov.music.command.constant.RequestConstant.*;

import java.util.*;
import java.util.stream.Collectors;

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

        Set<Violation> violations = new SearchValidator(null).apply(content);

        if (violations.isEmpty()) {
            String searchRequest = content.getRequestParameter(SEARCH_REQUEST)[0];
            int limit = Integer.parseInt(ResourceBundle.getBundle("app").getString("app.list.limit"));
            Map<String, Object> attributes = new HashMap<>();
            attributes.put(SEARCH_REQUEST, searchRequest);
            List<Musician> musicians;
            List<Track> tracks;
            List<Playlist> playlists;
            long musiciansSize;
            long tracksSize;
            long playlistsSize;
            boolean nextUnavailable = false;
            boolean previousUnavailable = false;
            boolean next = !content.getRequestParameters().containsKey(DIRECTION)
                    || content.getRequestParameter(DIRECTION)[0].equals(NEXT);
            long offset = Long.parseLong(content.getRequestParameter(OFFSET)[0]);

            try {
                musiciansSize = commonService.searchMusician(searchRequest, Integer.MAX_VALUE, 0).size();
                tracksSize = commonService.searchTrack(searchRequest, Integer.MAX_VALUE, 0).size();
                playlistsSize = commonService.searchPlaylist(searchRequest, Integer.MAX_VALUE, 0).size();
                musicians = commonService.searchMusician(searchRequest, limit, offset);
                playlists = commonService.searchPlaylist(searchRequest, limit, offset);
                tracks = commonService.searchTrack(searchRequest, limit, offset);
                if (content.getRequestParameters().containsKey("key-musicians")) {
                    attributes.put(MUSICIANS_ATTRIBUTE, musicians);
                    attributes.put(MUSICIAN_SIZE, musiciansSize);
                } else if (content.getRequestParameters().containsKey("key-tracks")) {
                    attributes.put(TRACKS_ATTRIBUTE, tracks);
                    attributes.put(TRACKS_SIZE, tracksSize);
                } else if (content.getRequestParameters().containsKey("key-playlists")) {
                    attributes.put(PLAYLISTS_ATTRIBUTE, playlists);
                    attributes.put(PLAYLISTS_SIZE, playlistsSize);
                } else {
                    attributes.put(MUSICIANS_ATTRIBUTE, musicians);
                    attributes.put(MUSICIAN_SIZE, musiciansSize);
                    attributes.put(TRACKS_ATTRIBUTE, tracks);
                    attributes.put(TRACKS_SIZE, tracksSize);
                    attributes.put(PLAYLISTS_ATTRIBUTE, playlists);
                    attributes.put(PLAYLISTS_SIZE, playlistsSize);
                }
                attributes.put(NEXT_OFFSET, offset + limit);
                attributes.put(PREVIOUS_OFFSET, offset - limit);
                attributes.put(PREVIOUS_UNAVAILABLE, previousUnavailable);
                attributes.put(NEXT_UNAVAILABLE, nextUnavailable);
            } catch (ServiceException e) {
                log.error("Service provide an exception for search command ", e);
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.INFORMATION_PAGE,
                        Map.of(PROCESS, CommandMessage.SEARCH_REQUEST_MESSAGE));
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.SEARCH_PAGE, attributes);
        } else {
            String result = "\u2718" + violations.stream()
                    .map(Violation::getMessage).collect(Collectors.joining("\u2718"));
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.SEARCH_PAGE,
                    Map.of(PROCESS, result));
        }

//                Map.of(SEARCH_REQUEST, searchRequest, MUSICIANS_ATTRIBUTE, musicians, MUSICIAN_SIZE, musiciansSize,
//                        TRACKS_ATTRIBUTE, tracks, TRACKS_SIZE, tracksSize,
//                        PLAYLISTS_ATTRIBUTE, playlists, PLAYLISTS_SIZE, playlistsSize));
    }
}
