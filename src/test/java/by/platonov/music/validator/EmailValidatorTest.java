package by.platonov.music.validator;

import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.command.RequestContent;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
class EmailValidatorTest {

    private EmailValidator validator = new EmailValidator(null);
    private RequestContent content = mock(RequestContent.class);

    @ParameterizedTest
    @ValueSource(strings = {"pldi@mail.ru", "1@icloud.com", "pl_DI@gmail.com", "32@bk.ru", "5674*&^%@c.com"})
    void applyPositive(String input) {

        when(content.getRequestParameters()).thenReturn(Map.of("email", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.EMAIL)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"C", "@chu.com", "D&MA.ru", "пла#тонов", "пла@тонов.com", " Дима@com.com", "", " ", "1234",
            "asdad@com", "p@mail."})
    void applyNegative(String input) {
        String message = "E-mail example johndoe@domainsample.com";

        when(content.getRequestParameters()).thenReturn(Map.of("email", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.EMAIL)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(message));

        assertEquals(expected, actual);
    }
}