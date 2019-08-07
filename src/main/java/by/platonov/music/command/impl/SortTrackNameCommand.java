package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.handler.CommandHandler;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * to sort {@link Track} by name
 *
 * @author Dzmitry Platonov on 2019-07-05.
 * @version 0.0.1
 */
@Log4j2
public class SortTrackNameCommand implements Command {

    private CommonService commonService;
    private CommandHandler<Track> handler;

    public SortTrackNameCommand(CommonService commonService, CommandHandler<Track> handler) {
        this.commonService = commonService;
        this.handler = handler;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that forward to {@link PageConstant}.SORT_NAME_TRACK_LIBRARY_PAGE
     * with sorted list of tracks
     */
    @Override
    public CommandResult execute(RequestContent content) {
        return handler.sorted(content, SORT_TRACK_NAME_ORDER, PageConstant.SORT_NAME_TRACK_LIBRARY_PAGE,
                commonService::countTracks, commonService::sortedTracksName);

    }
}
