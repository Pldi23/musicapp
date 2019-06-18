package by.platonov.music.command;

import by.platonov.music.service.CommonService;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public enum CommandType {

    LOGIN(CommonService.getInstance()::login),
    LOGOUT(CommonService.getInstance()::logout),
    REGISTER(CommonService.getInstance()::register),
    TOREGISTR(CommonService.getInstance()::toRegistr);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }
     public Command getCommand() {
        return command;
     }
}
