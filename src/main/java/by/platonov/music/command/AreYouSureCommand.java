package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;
import by.platonov.music.command.constant.RequestConstant;

import java.util.Map;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
public class AreYouSureCommand implements Command {
    @Override
    public CommandResult execute(RequestContent content) {
        String name = content.getRequestParameter(RequestConstant.NAME)[0];
        String uuid = content.getRequestParameter(RequestConstant.UUID)[0];

        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.ARE_YOU_SURE_PAGE,
                Map.of(RequestConstant.NAME, name, RequestConstant.UUID, uuid));
    }
}
