package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-08.
 * @version 0.0.1
 */
@Log4j2
public class SetLocaleCommand implements Command {

    private CommonService commonService;

    public SetLocaleCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        String locale = content.getRequestParameter(RequestConstant.LOCALE)[0];
        String page = content.getRequestParameters().getOrDefault(RequestConstant.PAGE, new String[]{PageConstant.INDEX_PAGE})[0];
        page = page.equals(PageConstant.LOGIN_PAGE) ? PageConstant.INDEX_PAGE : page;
        Map<String, Object> attributes = new HashMap<>();
        if (page.equals(PageConstant.MAIN_PAGE) || page.equals(PageConstant.ADMIN_PAGE)) {
            try {
                attributes.put(RequestConstant.TRACKS, commonService.getRandomTen());
            } catch (ServiceException e) {
                log.error("command could't provide tracks", e);
                return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ERROR_REDIRECT_PAGE);
            }
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, page, attributes, Map.of(RequestConstant.LOCALE, locale));
//        return new CommandResult(CommandResult.ResponseType.FORWARD,
//                (String) content.getSessionAttribute(RequestConstant.REFERER), Map.of(),
//                Map.of(RequestConstant.LOCALE, locale));
    }
}
