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
public class UnsortedAllTrackCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Track> handler;

    public UnsortedAllTrackCommand(CommonService commonService, CommandHandler<Track> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return handler.sorted(content, NO_ORDER, PageConstant.TRACK_LIBRARY_PAGE,
                commonService::countTracks, (b, l, o) -> commonService.searchTracks(l, o));
    }
}
