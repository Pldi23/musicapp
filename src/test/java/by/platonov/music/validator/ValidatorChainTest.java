package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.RequestConstant;
import by.platonov.music.command.RequestContent;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
class ValidatorChainTest {

    private RequestContent content = mock(RequestContent.class);

    @Test
    void testApplyPositive() {
        String login = "pldi";
        String password = "Password1!";
        String birthdate = "1986-02-07";
        String firstname = "Дима";
        String lastname = "Платонов";
        String email = "p@mail.ru";

        when(content.getRequestParameters()).thenReturn(Map.of(
                "login", new String[]{login},
                "password", new String[]{password},
                "birthdate", new String[]{birthdate},
                "firstname", new String[]{firstname},
                "lastname", new String[]{lastname},
                "email", new String[]{email}));
        when(content.getRequestParameter(RequestConstant.LOGIN)).thenReturn(new String[]{login});
        when(content.getRequestParameter(RequestConstant.PASSWORD)).thenReturn(new String[]{password});
        when(content.getRequestParameter(RequestConstant.BIRTHDATE)).thenReturn(new String[]{birthdate});
        when(content.getRequestParameter(RequestConstant.FIRSTNAME)).thenReturn(new String[]{firstname});
        when(content.getRequestParameter(RequestConstant.LASTNAME)).thenReturn(new String[]{lastname});
        when(content.getRequestParameter(RequestConstant.EMAIL)).thenReturn(new String[]{email});

        Set<Violation> actual = new LoginValidator(new PasswordValidator(new BirthDateValidator(
                new FirstnameValidator(new LastnameValidator(new EmailValidator(null)))))).apply(content);

        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @Test
    void testApplyNegative() {
        String login = "p";
        String password = "Password";
        String birthdate = "2016-02-07";
        String firstname = " Dima";
        String lastname = " Platonov";
        String email = "p@mail";


        when(content.getRequestParameters()).thenReturn(Map.of(
                "login", new String[]{login},
                "password", new String[]{password},
                "birthdate", new String[]{birthdate},
                "firstname", new String[]{firstname},
                "lastname", new String[]{lastname},
                "email", new String[]{email}));
        when(content.getRequestParameter(RequestConstant.LOGIN)).thenReturn(new String[]{login});
        when(content.getRequestParameter(RequestConstant.PASSWORD)).thenReturn(new String[]{password});
        when(content.getRequestParameter(RequestConstant.BIRTHDATE)).thenReturn(new String[]{birthdate});
        when(content.getRequestParameter(RequestConstant.FIRSTNAME)).thenReturn(new String[]{firstname});
        when(content.getRequestParameter(RequestConstant.LASTNAME)).thenReturn(new String[]{lastname});
        when(content.getRequestParameter(RequestConstant.EMAIL)).thenReturn(new String[]{email});

        Set<Violation> actual = new LoginValidator(new PasswordValidator(new BirthDateValidator(
                new FirstnameValidator(new LastnameValidator(new EmailValidator(null)))))).apply(content);

        Set<Violation> expected = Set.of(new Violation(MessageManager.getMessage("violation.login", "ru_RU")),
                new Violation(MessageManager.getMessage("violation.password", "ru_RU")),
                new Violation(MessageManager.getMessage("violation.birthdate", "ru_RU")),
                new Violation(MessageManager.getMessage("violation.firstname", "ru_RU")),
                new Violation(MessageManager.getMessage("violation.lastname", "ru_RU")),
                new Violation(MessageManager.getMessage("violation.email", "ru_RU")));

        assertEquals(expected, actual);
    }
}
