package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-08.
 * @version 0.0.1
 */
@Log4j2
public class SetLocaleCommand implements Command {
    @Override
    public CommandResult execute(RequestContent content) {
        String locale = content.getRequestParameter(RequestConstant.LOCALE)[0];
        String page = content.getRequestParameters().getOrDefault(RequestConstant.PAGE, new String[]{PageConstant.LOGIN_PAGE})[0];
        return new CommandResult(CommandResult.ResponseType.FORWARD, page, Map.of(), Map.of(RequestConstant.LOCALE, locale));
    }
}
