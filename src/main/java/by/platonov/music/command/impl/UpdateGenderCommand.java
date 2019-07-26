package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandHandler;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.User;
import by.platonov.music.service.UserService;
import by.platonov.music.validator.GenderValidator;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-16.
 * @version 0.0.1
 */
public class UpdateGenderCommand implements Command {

    private UserService userService;
    private CommandHandler handler;

    public UpdateGenderCommand(UserService userService, CommandHandler handler) {
        this.userService = userService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return handler.update(userService, content, RequestConstant.GENDER, new GenderValidator(null),
                (user, parameter) -> user.setGender(User.Gender.valueOf(parameter.toUpperCase())));
    }
}
