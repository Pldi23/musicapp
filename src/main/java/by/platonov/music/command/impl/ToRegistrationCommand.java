package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;

/**
 * forwards to {@link PageConstant}.REGISTRATION_PAGE
 *
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
public class ToRegistrationCommand implements Command {
    @Override
    public CommandResult execute(RequestContent content) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.REGISTRATION_PAGE);
    }
}
