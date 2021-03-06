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
class LastnameValidatorTest {

    private LastnameValidator validator = new LastnameValidator(null);
    private RequestContent content = mock(RequestContent.class);

    @ParameterizedTest
    @ValueSource(strings = {"Platonov", "Chu", "chu", "platonov", "Платонов", "платонов", "Чу", "чу", "O'Shea",
            "plat onov", "Van Persie", "Дунин-Мицкевич", "fdulttpbrkvyjghcneawxioqwertyu", "Van-P-persie"})
    void applyPositive(String input) {

        when(content.getRequestParameters()).thenReturn(Map.of("lastname", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.LASTNAME)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Plat3tonov", "Plat#onov", "C", "chu.", "пла#тонов", "пла3тонов", "fdulttpbrkvyjghcneawxioqwertyuiop",
            "чу'", "'OSHEA", "'O'SHE'A", " platonov", "platonov ", "Плат-", "-Платонов", "", " ", "1234"})
    void applyNegative(String input) {
        when(content.getRequestParameters()).thenReturn(Map.of("lastname", new String[]{input}));
        when(content.getRequestParameter(RequestConstant.LASTNAME)).thenReturn(new String[]{input});

        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(MessageManager.getMessage("violation.lastname", "ru_RU")));

        assertEquals(expected, actual);
    }
}