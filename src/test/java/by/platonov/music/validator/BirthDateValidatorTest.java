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
class BirthDateValidatorTest {

    private BirthDateValidator validator = new BirthDateValidator(null);
    private RequestContent content = mock(RequestContent.class);

    @ParameterizedTest
    @ValueSource(strings = {"1986-02-07", "2013-06-15"})
    void applyPositive(String input) {

        when(content.getRequestParameters()).thenReturn(Map.of("birthdate", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.BIRTHDATE)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2016-07-02", "2014-08-30"})
    void applyNegative(String input) {

        when(content.getRequestParameters()).thenReturn(Map.of("birthdate", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.BIRTHDATE)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(MessageManager.getMessage("violation.birthdate", "ru_RU")));

        assertEquals(expected, actual);
    }

}