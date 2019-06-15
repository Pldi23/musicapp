package by.platonov.music.service;

import by.platonov.music.command.CommandResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public class UserService {

    private static final UserService instance = new UserService();
    public static UserService getInstance() {
        return instance;
    }

    public CommandResult login(HttpServletRequest request) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, "index.jsp");
    }

    public CommandResult logout(HttpServletRequest request) {
        return new CommandResult(CommandResult.ResponseType.REDIRECT, "index.jsp");
    }
}
