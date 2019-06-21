package by.platonov.music.command;

import by.platonov.music.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-06-21.
 * @version 0.0.1
 */
class LoginCommandTest {

    private LoginCommand command;

    @Test
    void execute() {
        command = new LoginCommand(new UserService());
    }
}