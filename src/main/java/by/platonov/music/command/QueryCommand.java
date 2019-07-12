package by.platonov.music.command;

import by.platonov.music.command.constant.CommandMessage;
import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.Track;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

import static by.platonov.music.command.constant.RequestConstant.PROCESS;
import static by.platonov.music.command.constant.RequestConstant.TRACKS_ATTRIBUTE;

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
                    Map.of(TRACKS_ATTRIBUTE, tracks));
        } catch (ServiceException e) {
            log.error("Service provide an exception for query command ", e);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.INFORMATION_PAGE,
                    Map.of(PROCESS, CommandMessage.SEARCH_REQUEST_MESSAGE));
        }

    }
}
