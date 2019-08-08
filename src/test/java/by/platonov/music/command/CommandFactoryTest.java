package by.platonov.music.command;

import by.platonov.music.command.impl.ErrorCommand;
import by.platonov.music.command.impl.LoginCommand;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.service.CommonService;
import by.platonov.music.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    private RequestContent content;
    private CommandFactory factory = CommandFactory.getInstance();

    @BeforeEach
    void setUp() {
        Map<String, String[]> params = new HashMap<>();
        params.put(RequestConstant.COMMAND, new String[]{RequestConstant.LOGIN});
        content = new RequestContent.Builder().withRequestParameters(params).build();
    }

    @AfterEach
    void tearDown() {
        content = null;
    }

    @Test
    void getCommandWhenRequestLoginShouldReturnLoginCommand() {
        Command actual = factory.getCommand(content);
        Command expected = new LoginCommand(new UserService(), new CommonService());
        assertEquals(expected, actual);
    }

    @Test
    void getCommandWhenRequestDontHaveHiddenCommandShouldReturnErrorCommand() {
        content.getRequestParameters().remove(RequestConstant.COMMAND);
        Command actual = factory.getCommand(content);
        Command expected = new ErrorCommand();
        assertEquals(expected, actual);
    }

    @Test
    void getCommandWhenRequestParameterIllegalCommandShouldReturnErrorCommand() {
        content.getRequestParameters().put(RequestConstant.COMMAND, new String[]{"log"});
        Command actual = factory.getCommand(content);
        Command expected = new ErrorCommand(new IllegalArgumentException());
        assertEquals(expected, actual);
    }
}