package by.platonov.music.validator;

import by.platonov.music.MessageManager;
import by.platonov.music.command.constant.RequestConstant;
import by.platonov.music.command.RequestContent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author dzmitryplatonov on 2019-06-19.
 * @version 0.0.1
 */
class LoginValidatorTest {

    private LoginValidator validator = new LoginValidator(null);
    private RequestContent content = mock(RequestContent.class);

    @ParameterizedTest
    @ValueSource(strings = {"pldi", "pldi32", "pldi-32", "pldi_32", "____", "Pldi", "PLDI", "1234"})
    void applyPositive(String input) {

        when(content.getRequestParameters()).thenReturn(Map.of("login", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.LOGIN)).thenReturn(new String[] {input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "логинрусский", "", "pldi32й", "1234567891011121314151617181920", "qwe", "M@ks",
            "!pldi", "pl di"})
    void applyNegative(String input) {
        Locale.setDefault(new Locale("en_US"));
        when(content.getRequestParameters()).thenReturn(Map.of("login", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.LOGIN)).thenReturn(new String[] {input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(MessageManager.getMessage("violation.login", "en_US")));

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnViolationAsNoLoginParameterInRequest() {
        when(content.getRequestAttribute(RequestConstant.LOGIN)).thenReturn(null);
        Locale.setDefault(new Locale("en_US"));
        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(MessageManager.getMessage("violation.login", "en_US")));

        assertEquals(expected, actual);
    }
}