package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.constant.RequestConstant;
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
class PasswordValidatorTest {

    private PasswordValidator validator = new PasswordValidator(null);
    private RequestContent content = mock(RequestContent.class);


    @ParameterizedTest
    @ValueSource(strings = {"Qwertyu1@", "ZXCSDFADDa1!", "12345678910111213!Qq"})
    void applyPositive(String input) {
        when(content.getRequestParameters()).thenReturn(Map.of("password", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.PASSWORD)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "qwertyu1", "Qwertyuq", "QWERTYU1", "Qw1", "123456789101112131415Qq!", "Qwertyu1 @",
            "Qwertyu1 "})
    void applyNegative(String input) {
        when(content.getRequestParameters()).thenReturn(Map.of("password", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.PASSWORD)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(MessageManager.getMessage("violation.password", "en_US")));

        assertEquals(expected, actual);
    }
}