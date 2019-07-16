package by.platonov.music.command;

import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.User;
import by.platonov.music.service.UserService;
import by.platonov.music.validator.LastnameValidator;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-16.
 * @version 0.0.1
 */
public class UpdateLastnameCommand implements Command {

    private UserService userService;
    private CommandHandler handler;

    public UpdateLastnameCommand(UserService userService, CommandHandler commandHandler) {
        this.userService = userService;
        this.handler = commandHandler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return handler.update(userService, content, RequestConstant.LASTNAME, new LastnameValidator(null),
                User::setLastname);
    }
}
