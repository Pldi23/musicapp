package by.platonov.music.validator;

import by.platonov.music.command.RequestConstant;
import by.platonov.music.command.RequestContent;
import org.junit.jupiter.api.Test;

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

        String loginMessage = "Login must be minimum 4, maximum 20 symbols, and contain only " +
                "latin letter, numbers, and punctuation symbols like '-' and '_'";
        String passwordMessage = "Password must be minimum 8, maximum 20 symbols, and contain at" +
                " least 1 number, 1 latin uppercase letter, 1 latin lowercase letter, 1 punctuation. Only latin letters " +
                "available, spaces are unavailable";
        String birthdateMessage = "User of the application must be older then 6 years";
        String firstnameMessage = "First name must contain minimum 2 and maximum 30 letters";
        String lastnameMessage = "Last name must contain minimum 2 and maximum 30 letters";
        String emailMessage = "E-mail example johndoe@domainsample.com";

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

        Set<Violation> expected = Set.of(new Violation(loginMessage), new Violation(passwordMessage),
                new Violation(birthdateMessage), new Violation(firstnameMessage), new Violation(lastnameMessage),
                new Violation(emailMessage));

        assertEquals(expected, actual);
    }
}
