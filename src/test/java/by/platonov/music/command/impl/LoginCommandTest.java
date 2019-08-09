package by.platonov.music.command.impl;

import by.platonov.music.command.CommandResult;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.entity.User;
import by.platonov.music.exception.ServiceException;
import by.platonov.music.message.MessageManager;
import by.platonov.music.service.CommonService;
import by.platonov.music.service.UserService;
import by.platonov.music.validator.Violation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static by.platonov.music.constant.PageConstant.*;
import static by.platonov.music.constant.RequestConstant.*;
import static by.platonov.music.constant.RequestConstant.LOCALE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Dzmitry Platonov on 2019-08-09.
 * @version 0.0.1
 */
class LoginCommandTest {

    private RequestContent content;
    private LoginCommand command;
    private UserService userService;
    private CommonService commonService;

    @BeforeEach
    void setUp() {
        HashMap<String, String[]> params = new HashMap<>();
        HashMap<String, Object> attrs = new HashMap<>();
        attrs.put(RequestConstant.LOCALE, "ru_RU");

        content = new RequestContent.Builder().withRequestParameters(params).withSessionAttributes(attrs).build();
        commonService = mock(CommonService.class);
        userService = mock(UserService.class);
        command = new LoginCommand(userService, commonService);
    }

    @AfterEach
    void tearDown() {
        command = null;
        content = null;
        commonService = null;
        userService = null;
    }

    @Test
    void executeUserSuccessful() throws ServiceException {
        content.getRequestParameters().put(RequestConstant.LOGIN, new String[]{"pldi"});
        content.getRequestParameters().put(RequestConstant.PASSWORD, new String[]{"Qwertyui1!"});

        List<User> users = List.of(User.builder()
                .login("superman23")
                .password("$s0$41010$y3c+KozGAfgmmLwMDTSUvQ==$so3uTOkNQRqqqeJ3vVhg0/PBEmAUatd6dAudwGbYQG0=")
                .admin(false)
                .firstname("Dima")
                .lastname("Platonov")
                .email("platonovd32@gmail.com")
                .birthDate(LocalDate.of(2008, 1, 1))
                .gender(User.Gender.FEMALE)
                .active(true)
                .verificationUuid(null)
                .photoPath("0c01941e-db72-4cde-81ea-d7ed2326f3b7.jpg")
                .registrationDate(LocalDate.of(2019, 7, 15))
                .playlists(new LinkedHashSet<>())
                .payments(new LinkedHashSet<>())
                .paidPeriod(LocalDateTime.of(2019, 10, 6, 0, 0))
                .build());

        when(userService.searchUserByLogin("pldi")).thenReturn(users);
        when(commonService.getRandomTen()).thenReturn(List.of());

        CommandResult actual = command.execute(content);
        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, MAIN_PAGE,
                Map.of(TRACKS, commonService.getRandomTen()), Map.of(USER, users.get(0)));

        assertEquals(expected, actual);
    }

    @Test
    void executeAdminSuccessful() throws ServiceException {
        content.getRequestParameters().put(RequestConstant.LOGIN, new String[]{"main"});
        content.getRequestParameters().put(RequestConstant.PASSWORD, new String[]{"Qwertyui1!"});

        List<User> users = List.of(User.builder()
                .login("main")
                .password("$s0$41010$ZZrgGEwuGRPjsLGy1+lBgg==$4MYEPcqCPZmzRWqGSP/Ka3EqAPRz9FQ4po09RW5LMO8=")
                .admin(true)
                .firstname("Dima")
                .lastname("Platonov")
                .email("pldi@mail.ru")
                .birthDate(LocalDate.of(1986, 7, 2))
                .gender(User.Gender.MALE)
                .active(true)
                .verificationUuid(null)
                .photoPath("2d974e1a34362e97a92d24973f9ed37a.jpg")
                .registrationDate(LocalDate.of(2019, 6, 22))
                .playlists(new LinkedHashSet<>())
                .payments(new LinkedHashSet<>())
                .paidPeriod(LocalDateTime.of(2020, 3, 31, 0, 0))
                .build());

        when(userService.searchUserByLogin("main")).thenReturn(users);
        when(commonService.getRandomTen()).thenReturn(List.of());

        CommandResult actual = command.execute(content);
        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, ADMIN_PAGE,
                Map.of(TRACKS, commonService.getRandomTen()), Map.of(USER, users.get(0)));

        assertEquals(expected, actual);
    }

    @Test
    void executeViolation() throws ServiceException {
        content.getRequestParameters().put(RequestConstant.LOGIN, new String[]{"main"});
        content.getRequestParameters().put(RequestConstant.PASSWORD, new String[]{"Q"});

        List<User> users = List.of(User.builder()
                .login("main")
                .password("$s0$41010$ZZrgGEwuGRPjsLGy1+lBgg==$4MYEPcqCPZmzRWqGSP/Ka3EqAPRz9FQ4po09RW5LMO8=")
                .admin(true)
                .firstname("Dima")
                .lastname("Platonov")
                .email("pldi@mail.ru")
                .birthDate(LocalDate.of(1986, 7, 2))
                .gender(User.Gender.MALE)
                .active(true)
                .verificationUuid(null)
                .photoPath("2d974e1a34362e97a92d24973f9ed37a.jpg")
                .registrationDate(LocalDate.of(2019, 6, 22))
                .playlists(new LinkedHashSet<>())
                .payments(new LinkedHashSet<>())
                .paidPeriod(LocalDateTime.of(2020, 3, 31, 0, 0))
                .build());

        when(userService.searchUserByLogin("main")).thenReturn(users);
        when(commonService.getRandomTen()).thenReturn(List.of());
        Set<Violation> violations = Set.of(new Violation(MessageManager
                .getMessage("violation.password", (String) content.getSessionAttribute(LOCALE))));

        CommandResult actual = command.execute(content);
        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, LOGIN_PAGE,
                Map.of(VALIDATOR_RESULT, violations, TRACKS, commonService.getTracksLastAdded()));

        assertEquals(expected, actual);
    }

    @Test
    void executeFailed() throws ServiceException {
        content.getRequestParameters().put(RequestConstant.LOGIN, new String[]{"main"});
        content.getRequestParameters().put(RequestConstant.PASSWORD, new String[]{"Qwertyui1!1"});

        List<User> users = List.of(User.builder()
                .login("main")
                .password("$s0$41010$ZZrgGEwuGRPjsLGy1+lBgg==$4MYEPcqCPZmzRWqGSP/Ka3EqAPRz9FQ4po09RW5LMO8=")
                .admin(true)
                .firstname("Dima")
                .lastname("Platonov")
                .email("pldi@mail.ru")
                .birthDate(LocalDate.of(1986, 7, 2))
                .gender(User.Gender.MALE)
                .active(true)
                .verificationUuid(null)
                .photoPath("2d974e1a34362e97a92d24973f9ed37a.jpg")
                .registrationDate(LocalDate.of(2019, 6, 22))
                .playlists(new LinkedHashSet<>())
                .payments(new LinkedHashSet<>())
                .paidPeriod(LocalDateTime.of(2020, 3, 31, 0, 0))
                .build());

        when(userService.searchUserByLogin("main")).thenReturn(users);
        when(commonService.getRandomTen()).thenReturn(List.of());

        CommandResult actual = command.execute(content);
        CommandResult expected = new CommandResult(CommandResult.ResponseType.FORWARD, LOGIN_PAGE,
                Map.of(ERROR_LOGIN_PASS_ATTRIBUTE, MessageManager.getMessage("login.failed",
                        (String) content.getSessionAttribute(LOCALE)), TRACKS, commonService.getTracksLastAdded()));

        assertEquals(expected, actual);
    }

    @Test
    void executeException() throws ServiceException {
        content.getRequestParameters().put(RequestConstant.LOGIN, new String[]{"main"});
        content.getRequestParameters().put(RequestConstant.PASSWORD, new String[]{"Qwertyui1!"});

        ServiceException exception = new ServiceException();
        when(userService.searchUserByLogin("main")).thenThrow(exception);
        when(commonService.getRandomTen()).thenReturn(List.of());

        CommandResult actual = command.execute(content);
        CommandResult expected = new ErrorCommand(exception).execute(content);

        assertEquals(expected, actual);
    }
}