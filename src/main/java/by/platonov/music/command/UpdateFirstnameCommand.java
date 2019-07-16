package by.platonov.music.command;

import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.entity.User;
import by.platonov.music.service.UserService;
import by.platonov.music.validator.FirstnameValidator;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-16.
 * @version 0.0.1
 */
public class UpdateFirstnameCommand implements Command {

    private UserService userService;
    private CommandHandler handler;

    public UpdateFirstnameCommand(UserService userService, CommandHandler handler) {
        this.userService = userService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return handler.update(userService, content, RequestConstant.FIRSTNAME, new FirstnameValidator(null),
                User::setFirstname);
    }
}
