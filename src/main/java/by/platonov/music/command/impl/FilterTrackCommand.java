package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import by.platonov.music.validator.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-12.
 * @version 0.0.1
 */
@Log4j2
public class FilterTrackCommand implements Command {

    private CommonService commonService;

    public FilterTrackCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {

        Set<Violation> violations =
                new TrackNameValidator(true,
                        new SingerValidator(true,
                                new FilterDateValidator(RELEASE_FROM, RELEASE_TO, null))).apply(content);

        if (violations.isEmpty()) {

            List<Track> tracks;
            String trackName = content.getRequestParameter(TRACKNAME)[0];
            String genreName = !content.getRequestParameter(GENRE)[0].isBlank() ? content.getRequestParameter(GENRE)[0] : "";
            String fromDate = !content.getRequestParameter(RELEASE_FROM)[0].isBlank() ?
                    content.getRequestParameter(RELEASE_FROM)[0] : LocalDate.EPOCH.toString();
            String toDate = !content.getRequestParameter(RELEASE_TO)[0].isBlank() ?
                    content.getRequestParameter(RELEASE_TO)[0] : LocalDate.now().toString();
            String singerName = content.getRequestParameter(SINGER)[0];

            int limit = Integer.parseInt(ResourceBundle.getBundle("app").getString("app.list.limit"));
            boolean nextUnavailable = false;
            boolean previousUnavailable = false;
            boolean next = !content.getRequestParameters().containsKey(DIRECTION)
                    || content.getRequestParameter(DIRECTION)[0].equals(NEXT);
            long offset;
            try {
                long size = commonService.searchTrackByFilter(trackName, genreName, LocalDate.parse(fromDate),
                        LocalDate.parse(toDate), singerName, Integer.MAX_VALUE, 0).size();
                if (content.getRequestParameters().containsKey(OFFSET)) {
                    offset = 0;
                    previousUnavailable = true;
                    nextUnavailable = size <= limit;
                } else {
                    if (next) {
                        offset = (long) content.getSessionAttribute(NEXT_OFFSET);
                        nextUnavailable = offset + limit >= size;
                    } else {
                        offset = (long) content.getSessionAttribute(PREVIOUS_OFFSET);
                        previousUnavailable = offset - limit < 0;
                    }
                }
                tracks = commonService.searchTrackByFilter(trackName, genreName, LocalDate.parse(fromDate),
                        LocalDate.parse(toDate), singerName, limit, offset);
            } catch (ServiceException e) {
                log.error("command could't provide track list", e);
                return new ErrorCommand(e).execute(content);
            }
            log.debug("command provided tracks: " + tracks);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.FILTER_PAGE,
                    Map.of(ENTITIES, tracks, PREVIOUS_UNAVAILABLE, previousUnavailable,
                            NEXT_UNAVAILABLE, nextUnavailable, TRACKNAME, trackName, GENRE, genreName, RELEASE_FROM, fromDate,
                            RELEASE_TO, toDate, SINGER, singerName),
                    Map.of(NEXT_OFFSET, offset + limit, PREVIOUS_OFFSET, offset - limit));
        } else {
            log.info("filter failed because of validator violation");
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.FILTER_PAGE,
                    Map.of(VALIDATOR_RESULT, violations));
        }
    }
}
