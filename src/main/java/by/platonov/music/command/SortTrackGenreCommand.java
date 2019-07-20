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
public class SortTrackGenreCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Track> handler;

    public SortTrackGenreCommand(CommonService commonService, CommandHandler<Track> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return handler.sorted(content, SORT_TRACK_GENRE_ORDER, PageConstant.SORT_GENRE_TRACK_LIBRARY_PAGE,
                commonService::countTracks, commonService::sortedTracksGenre);


//        boolean sortTrackGenreOrder = !content.getSessionAttributes().containsKey(SORT_TRACK_GENRE_ORDER) ||
//                (boolean) content.getSessionAttribute(SORT_TRACK_GENRE_ORDER);
//        List<Track> tracks;
//        try {
//            tracks = commonService.sortedTracksGenre(sortTrackGenreOrder);
////            sortTrackGenreOrder = !sortTrackGenreOrder;
//            for (Track track : tracks) {
//                track.getSingers().addAll(commonService.getTrackSingers(track.getId()));
//                track.getAuthors().addAll(commonService.getTrackAuthors(track.getId()));
//            }
//        } catch (ServiceException e) {
//            log.error("command couldn't provide sorted tracklist", e);
//            return new CommandResult(CommandResult.ResponseType.REDIRECT, PageConstant.ERROR_REDIRECT_PAGE);
//        }
//        log.debug("command provide sorted track list: " + tracks);
//        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.TRACK_LIBRARY_PAGE,
//                Map.of(TRACKS, tracks), Map.of(SORT_TRACK_GENRE_ORDER, sortTrackGenreOrder));
//    }
    }
}
