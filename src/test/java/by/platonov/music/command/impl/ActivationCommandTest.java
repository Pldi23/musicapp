package by.platonov.music.command.impl;

import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.db.DatabaseSetupExtension;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.message.MessageManager;
import by.platonov.music.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static by.platonov.music.constant.PageConstant.INDEX_PAGE;
import static by.platonov.music.constant.RequestConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Dzmitry Platonov on 2019-08-08.
 * @version 0.0.1
 */
@ExtendWith(DatabaseSetupExtension.class)
class ActivationCommandTest {

    private RequestContent content;
    private UserService userService;
    private ActivationCommand command;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        Map<String, String[]> params = new HashMap<>();
        Map<String, Object> sessionAttrs = new HashMap<>();
        params.put(EMAIL, new String[]{"pldi@mail.ru"});
        params.put(HASH, new String[]{"1"});
        sessionAttrs.put(LOCALE, "ru_RU");

        content = new RequestContent.Builder().withRequestParameters(params).withSessionAttributes(sessionAttrs).build();
        command = new ActivationCommand(userService);
    }

    @AfterEach
    void tearDown() {
        command = null;
        content = null;
        userService = null;
    }

    @Test
    void execute() throws ServiceException {
        when(userService.activate("pldi@mail.ru", "1")).thenReturn(true);
        CommandResult actual = command.execute(content);
        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, INDEX_PAGE, Map.of(PROCESS,
                MessageManager.getMessage("message.present", (String) content.getSessionAttribute(LOCALE))));

        assertEquals(expected, actual);
    }

    @Test
    void executeException() throws ServiceException {
        ServiceException exception = new ServiceException();
        when(userService.activate("pldi@mail.ru", "1")).thenThrow(exception);

        CommandResult actual = command.execute(content);
        CommandResult expected = new ErrorCommand(exception).execute(content);

        assertEquals(expected, actual);
    }
}