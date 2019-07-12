package by.platonov.music.validator;

import by.platonov.music.command.RequestContent;
import by.platonov.music.command.constant.RequestConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-03.
 * @version 0.0.1
 */
class IdValidatorTest {

    private static final String EXPECTED_MESSAGE = "Id should be positive";

    private IdValidator validator = new IdValidator(null);
    private RequestContent content = mock(RequestContent.class);

    @ParameterizedTest
    @ValueSource(strings = {"1","101"})
    void applyPositive(String input) {
        when(content.getRequestParameters()).thenReturn(Map.of(RequestConstant.ID, new String[]{input}));
        when(content.getRequestParameter(RequestConstant.ID)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1","-101", "0"})
    void applyNegative(String input) {
        Locale.setDefault(new Locale("en_US"));
        when(content.getRequestParameters()).thenReturn(Map.of(RequestConstant.ID, new String[]{input}));
        when(content.getRequestParameter(RequestConstant.ID)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(EXPECTED_MESSAGE));

        assertEquals(expected, actual);
    }
}