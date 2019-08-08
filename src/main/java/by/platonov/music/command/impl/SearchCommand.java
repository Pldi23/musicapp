package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.Musician;
import by.platonov.music.entity.Playlist;
import by.platonov.music.entity.Track;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import by.platonov.music.validator.SearchValidator;
import by.platonov.music.validator.Violation;
import lombok.extern.log4j.Log4j2;

import static by.platonov.music.constant.RequestConstant.*;

import java.util.*;

/**
 * to search for {@link Track}s, {@link Playlist}s, {@link Musician}s
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

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.SEARCH_PAGE with violations if it was found
     * forward to {@link PageConstant}.SEARCH_PAGE with required entities in attributes if execution is successful
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {

        Set<Violation> violations = new SearchValidator(null).apply(content);

        if (violations.isEmpty()) {
            User user = (User) content.getSessionAttribute(USER);
            String searchRequest = content.getRequestParameter(SEARCH_REQUEST)[0];
            Map<String, Object> attributes = new HashMap<>();
            attributes.put(SEARCH_REQUEST, searchRequest);
            List<Musician> musicians;
            List<Track> tracks;
            List<Playlist> playlists;

            try {
                musicians = commonService.searchMusician(searchRequest, Integer.MAX_VALUE, 0);
                playlists = commonService.searchPlaylistByName(searchRequest, Integer.MAX_VALUE, 0, user);
                tracks = commonService.searchTrack(searchRequest, Integer.MAX_VALUE, 0);

                attributes.put(MUSICIANS_ATTRIBUTE, musicians);
                attributes.put(TRACKS, tracks);
                attributes.put(PLAYLISTS, playlists);
                attributes.put(MUSICIAN_SIZE, musicians.size());
                attributes.put(PLAYLISTS_SIZE, playlists.size());
                attributes.put(TRACKS_SIZE, tracks.size());
            } catch (ServiceException e) {
                log.error("Service provide an exception for search command ", e);
                return new ErrorCommand(e).execute(content);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.SEARCH_PAGE, attributes);
        } else {
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.SEARCH_PAGE,
                    Map.of(VALIDATOR_RESULT, violations));
        }
    }
}
