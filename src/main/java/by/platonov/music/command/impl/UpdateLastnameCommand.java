package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.handler.CommandHandler;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
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
