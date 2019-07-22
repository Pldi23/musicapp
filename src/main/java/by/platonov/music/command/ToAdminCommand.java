package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
/**
 * music-app
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

    @Override
    public CommandResult execute(RequestContent content) {
        try {
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ADMIN_PAGE,
                    Map.of(RequestConstant.TRACKS, commonService.getRandomTen()));
        } catch (ServiceException e) {
            log.error("Could not provide tracks to admin main page ", e);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ERROR_REDIRECT_PAGE);
        }
    }
}
