package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;

/**
 * forwards to {@link PageConstant}.REGISTER_ADMIN_PAGE
 *
 * @author Dzmitry Platonov on 2019-07-22.
 * @version 0.0.1
 */
public class ToAdminRegistrationCommand implements Command {
    @Override
    public CommandResult execute(RequestContent content) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.REGISTER_ADMIN_PAGE);
    }
}
