package by.platonov.music.command.impl;

import by.platonov.music.command.Command;
import by.platonov.music.command.handler.CommandHandler;
import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.PageConstant;
import by.platonov.music.entity.User;
import by.platonov.music.service.UserService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import static by.platonov.music.constant.RequestConstant.*;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-21.
 * @version 0.0.1
 */
@Log4j2
public class ToUserLibraryCommand implements Command {

    private UserService userService;
    private CommandHandler<User> handler;

    public ToUserLibraryCommand(UserService userService, CommandHandler<User> handler) {
        this.userService = userService;
        this.handler = handler;
    }

    @Override
    public CommandResult execute(RequestContent content) {

        return handler.filter(content, PageConstant.USER_LIBRARY_PAGE,
                (limit, offset) -> userService.searchAllUsers(limit, offset),
                Map.of(PAGE_COMMAND, USER_LIBRARY));
    }
}

