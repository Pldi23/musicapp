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
 * forwards to {@link PageConstant}.ADMIN_PAGE
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
@Log4j2
public class ToAdminCommand implements Command {

    private CommonService commonService;

    public ToAdminCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that forward to {@link PageConstant}.ADMIN_PAGE
     * executes {@link ErrorCommand} if {@link ServiceException} was caught
     */
    @Override
    public CommandResult execute(RequestContent content) {
        try {
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ADMIN_PAGE,
                    Map.of(RequestConstant.TRACKS, commonService.getRandomTen()));
        } catch (ServiceException e) {
            log.error("Could not provide tracks to admin main page ", e);
            return new ErrorCommand(e).execute(content);
        }
    }
}
