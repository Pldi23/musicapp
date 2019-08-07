package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

/**
 * to transfer data to entry page
 *
 * @author Dzmitry Platonov on 2019-07-19.
 * @version 0.0.1
 */
@Log4j2
public class EntryCommand implements Command {

    private CommonService commonService;

    public EntryCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that
     * forward to {@link PageConstant}.LOGIN_PAGE with list of tracks in attributes
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        try {
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.LOGIN_PAGE,
                    Map.of(RequestConstant.TRACKS, commonService.getTracksLastAdded()));
        } catch (ServiceException e) {
            log.error("command could't provide track list", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}
