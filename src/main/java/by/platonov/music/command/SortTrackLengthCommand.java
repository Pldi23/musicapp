package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
@Log4j2
public class SortTrackLengthCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Track> handler;

    public SortTrackLengthCommand(CommonService commonService, CommandHandler<Track> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return handler.sorted(content, RequestConstant.SORT_TRACK_LENGTH_ORDER, PageConstant.SORT_LENGTH_TRACK_LIBRARY_PAGE,
                commonService::countTracks, commonService::sortedTracksLength);
    }
}
