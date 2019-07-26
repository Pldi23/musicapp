package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.CommandHandler;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.service.UserService;
import by.platonov.music.validator.BirthDateValidator;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-16.
 * @version 0.0.1
 */
@Log4j2
public class UpdateBirthDateCommand implements Command {

    private UserService userService;
    private CommandHandler commandHandler;

    public UpdateBirthDateCommand(UserService userService, CommandHandler commandHandler) {
        this.userService = userService;
        this.commandHandler = commandHandler;
    }

    @Override
    public CommandResult execute(RequestContent content) {
        return commandHandler.update(userService, content, BIRTHDATE, new BirthDateValidator(null),
                (user, parameter) -> user.setBirthDate(LocalDate.parse(parameter)));
    }
}
