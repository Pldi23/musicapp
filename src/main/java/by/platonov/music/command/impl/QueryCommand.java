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
 * to provide tracks to {@link PageConstant}.ADMIN_PAGE
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

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that:
     * forward to {@link PageConstant}.ADMIN_PAGE with list of tracks in attributes
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
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
