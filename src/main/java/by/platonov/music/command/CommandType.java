package by.platonov.music.command;

import by.platonov.music.service.*;

/**
 * @author dzmitryplatonov on 2019-06-15.
 * @version 0.0.1
 */
public enum CommandType {


    LOGIN(new LoginCommand(new UserService())),
    LOGOUT(new LogoutCommand()),
    REGISTER(new RegistrationCommand(new UserService())),
    TOREGISTR(new ToRegistrationCommand()),
    ACTIVATION(new ActivationCommand(new UserService())),
    ERROR(new ErrorCommand()),
    SEARCH(new SearchCommand(new MusicianService(), new TrackService(), new PlaylistService()));

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }
     public Command getCommand() {
        return command;
     }
}
