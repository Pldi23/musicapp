package by.platonov.music.validator;

import by.platonov.music.message.MessageManager;
import by.platonov.music.command.RequestContent;
import by.platonov.music.constant.RequestConstant;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * music-app
 *
 * @author Dzmitry Platonov on 2019-07-02.
 * @version 0.0.1
 */
class SingerValidatorTest {

//    public static final String EXPECTED_VIOLATION_MESSAGE =
//            "You should enter at least 1 singer, and his name should contain at least one symbol";

    private SingerValidator validator = new SingerValidator(null);
    private RequestContent content = mock(RequestContent.class);

    @ParameterizedTest
    @ValueSource(strings = {"1", "name", "имя", "Clean Bandit"})
    void applyPositive(String input) {
        when(content.getRequestParameters()).thenReturn(Map.of(RequestConstant.SINGER, new String[]{input}));
        when(content.getRequestParameter(RequestConstant.SINGER)).thenReturn(new String[]{input});
        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "name1111111111111222222222222222222211111113333333331111333331111111111333333333111111333333331",})
    void applyNegative(String input) {
//        Locale.setDefault(new Locale("en_US"));
        when(content.getRequestParameters()).thenReturn(Map.of(RequestConstant.SINGER, new String[]{input}));
        when(content.getRequestParameter(RequestConstant.SINGER)).thenReturn(new String[]{input});
        Set<Violation> actual = validator.apply(content);
        Set<Violation> expected = Set.of(new Violation(MessageManager.getMessage("violation.singer", "en_US")));

        assertEquals(expected, actual);
    }
}