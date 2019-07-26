package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

import static by.platonov.music.constant.RequestConstant.TRACKS;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
@Log4j2
public class QueryCommand implements Command {

    private CommonService commonService;

    public QueryCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        String query = content.getRequestParameter(RequestConstant.SEARCH_REQUEST)[0];
        List<Track> tracks;
        try {
            tracks = commonService.searchTrackByName(query);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ADMIN_PAGE,
                    Map.of(TRACKS, tracks));
        } catch (ServiceException e) {
            log.error("Service provide an exception for query command ", e);
            return new ErrorCommand(e).execute(content);
        }

    }
}
