package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.service.CommonService;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

/**
 * to store application locale in {@link javax.servlet.http.HttpSession}
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

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that forward to the page from which the request came
     */
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
                return new ErrorCommand(e).execute(content);
            }
        }
        return new CommandResult(CommandResult.ResponseType.FORWARD, page, attributes, Map.of(RequestConstant.LOCALE, locale));
    }
}
