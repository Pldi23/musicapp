package by.platonov.music.command;

import by.platonov.music.command.constant.PageConstant;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-22.
 * @version 0.0.1
 */
public class ToAdminRegistrationCommand implements Command {
    @Override
    public CommandResult execute(RequestContent content) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.REGISTR_ADMIN_PAGE);
    }
}
