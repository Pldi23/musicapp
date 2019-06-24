package by.platonov.music.validator;

import by.platonov.music.command.RequestConstant;
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
class FirstnameValidatorTest {

    private FirstnameValidator validator = new FirstnameValidator(null);
    private RequestContent content = mock(RequestContent.class);

    @ParameterizedTest
    @ValueSource(strings = {"Дима", "Dima", "dima", "дима", "ДИМА", "ДИ МА", "чу", "Ди-ма",
            "fdulttpbrkvyjghcneawxioqwertyu"})
    void applyPositive(String input) {

        when(content.getRequestParameters()).thenReturn(Map.of("firstname", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.FIRSTNAME)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"C", "chu.", "D&MA", "пла#тонов", "пла3тонов", "fdulttpbrkvyjghcneawxioqwertyuiop",
            "чу?", " Дима", "Дима ", "Дима-", "-Дима", "", " ", "1234"})
    void applyNegative(String input) {
        String message = "First name must contain minimum 2 and maximum 30 letters";

        when(content.getRequestParameters()).thenReturn(Map.of("firstname", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.FIRSTNAME)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(message));

        assertEquals(expected, actual);
    }
}