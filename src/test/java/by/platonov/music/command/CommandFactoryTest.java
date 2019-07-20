package by.platonov.music.command;

import by.platonov.music.command.constant.CommandMessage;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.service.CommonService;
import by.platonov.music.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandFactoryTest {

    private RequestContent content = mock(RequestContent.class);
    private CommandFactory factory = CommandFactory.getInstance();

    @Test
    void getCommandWhenRequestLoginShouldReturnLoginCommand() {
        when(content.getRequestParameters()).thenReturn(Map.of(RequestConstant.COMMAND, new String[]{RequestConstant.LOGIN}));
        when(content.getRequestParameter(RequestConstant.COMMAND)).thenReturn(new String[]{RequestConstant.LOGIN});
        Command actual = factory.getCommand(content);
        Command expected = new LoginCommand(new UserService(), new CommonService());
        assertEquals(expected, actual);
    }

    @Test
    void getCommandWhenRequestDontHaveHiddenCommandShouldReturnErrorCommand() {
        when(content.getRequestParameters()).thenReturn(Map.of(RequestConstant.LOGIN, new String[]{RequestConstant.LOGIN}));
        when(content.getRequestParameter(RequestConstant.LOGIN)).thenReturn(new String[]{RequestConstant.LOGIN});
        Command actual = factory.getCommand(content);
        Command expected = new ErrorCommand();
        assertEquals(expected, actual);
    }

    @Test
    void getCommandWhenRequestParameterIllegalCommandShouldReturnErrorCommand() {
        when(content.getRequestParameters()).thenReturn(Map.of(RequestConstant.COMMAND, new String[]{"log"}));
        when(content.getRequestParameter(RequestConstant.COMMAND)).thenReturn(new String[]{"log"});
        Command actual = factory.getCommand(content);
        Command expected = new ErrorCommand(CommandMessage.COMMAND_FAILED_MESSAGE);
        assertEquals(expected, actual);
    }
}