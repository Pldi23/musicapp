package by.platonov.music.command;

import by.platonov.music.service.UserService;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public enum CommandType {

    LOGIN(UserService.getInstance()::login),
    LOGOUT(UserService.getInstance()::logout);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }
     public Command getCommand() {
        return command;
     }
}
