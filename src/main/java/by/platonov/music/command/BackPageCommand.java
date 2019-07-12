package by.platonov.music.command;

import by.platonov.music.command.constant.RequestConstant;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-07.
 * @version 0.0.1
 */
public class BackPageCommand implements Command {
    @Override
    public CommandResult execute(RequestContent content) {
        return new CommandResult(CommandResult.ResponseType.FORWARD,
                (String) content.getSessionAttribute(RequestConstant.PREVIOUS_PAGE));
    }
}
