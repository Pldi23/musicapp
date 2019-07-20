package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import static by.platonov.music.command.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-05.
 * @version 0.0.1
 */
@Log4j2
public class SortTrackIdCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Track> handler;

    public SortTrackIdCommand(CommonService commonService, CommandHandler<Track> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return handler.sorted(content, SORT_TRACK_ID_ORDER, PageConstant.SORT_ID_TRACK_LIBRARY_PAGE,
                commonService::countTracks, commonService::sortedTracksId);
    }
//        List<Track> trackList;
//        boolean sortTrackIdOrder = !content.getSessionAttributes().containsKey(SORT_TRACK_ID_ORDER) ||
//                (boolean) content.getSessionAttribute(SORT_TRACK_ID_ORDER);
//        int limit = Integer.parseInt(ResourceBundle.getBundle("app").getString("app.list.limit"));
//        boolean nextUnavailable = false;
//        boolean previousUnavailable = false;
//        boolean next = !content.getRequestParameters().containsKey(DIRECTION) || content.getRequestParameter(DIRECTION)[0].equals(NEXT);
//        long offset;
//
//        try {
//            long totalAmountTracks = commonService.searchTracks(Long.MAX_VALUE, 0).size();
//            if (content.getRequestParameters().containsKey(OFFSET)) {
//                offset = 0;
//                previousUnavailable = true;
//            } else {
//                if (next) {
//                    offset = (long) content.getSessionAttribute(NEXT_OFFSET);
//                    if (offset + limit >= totalAmountTracks) {
//                        nextUnavailable = true;
//                    }
//                } else {
//                    offset = (long) content.getSessionAttribute(PREVIOUS_OFFSET);
//                    if (offset - limit < 0) {
//                        previousUnavailable = true;
//                    }
//                }
//            }
//
//            trackList = commonService.sortedTracksId(sortTrackIdOrder, limit, offset);
////            sortTrackIdOrder = content.getRequestParameters().containsKey(ORDER) ? !sortTrackIdOrder : sortTrackIdOrder;
//            for (Track track : trackList) {
//                track.getSingers().addAll(commonService.getTrackSingers(track.getId()));
//                track.getAuthors().addAll(commonService.getTrackAuthors(track.getId()));
//            }
//        } catch (ServiceException e) {
//            log.error("command couldn't provide sorted tracklist", e);
//            return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
//        }
//        log.debug("command provide sorted track list: " + trackList);
//        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.SORT_ID_TRACK_LIBRARY_PAGE,
//                Map.of(TRACKS, trackList,  PREVIOUS_UNAVAILABLE, previousUnavailable,
//                        NEXT_UNAVAILABLE, nextUnavailable),
//                Map.of(SORT_TRACK_ID_ORDER, sortTrackIdOrder, NEXT_OFFSET, offset + limit,
//                        PREVIOUS_OFFSET, offset - limit));
//    }
}
