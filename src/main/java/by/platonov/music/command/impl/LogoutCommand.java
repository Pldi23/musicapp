package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;

/**
 * to log out {@link by.platonov.music.entity.User} from application
 *
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
public class LogoutCommand implements Command {

    /**
     *
     * @param content DTO containing all data received with {@link javax.servlet.http.HttpServletRequest}
     * @return instance of {@link CommandResult} that forward user to {@link PageConstant}.INDEX_PAGE
     *
     * session will be invalidated in {@link by.platonov.music.controller.FrontController}
     */
    @Override
    public CommandResult execute(RequestContent content) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.INDEX_PAGE);
    }
}
